package com.dog_feliz.user_service.service.mail.strategy;

import com.dog_feliz.user_service.controller.dto.MailRequestDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import com.dog_feliz.user_service.service.mail.MailSenderAvailable;
import com.dog_feliz.user_service.shared.exception.MailSenderException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service(value = MailSenderAvailable.GMAIL_SENDER)
public class GmailSenderStrategy implements MailSenderStrategy {
    @Autowired
    @Qualifier("gmailMailSender")
    private JavaMailSender sender;

    @Autowired
    private UserService userService;

    @Value("${mail.gmail.username}")
    private String defaultMailAddress;

    @Override
    public void sendSimpleMail(MailRequestDto mailRequest, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailAddressTo(to));
        message.setSubject(mailRequest.getSubject());
        message.setText(mailRequest.getContent());
        sender.send(message);
    }

    @Override
    public void sendMailWithAttachment(MailRequestDto mailRequest, String to) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mailAddressTo(to));
        helper.setSubject(mailRequest.getSubject());
        helper.setText(mailRequest.getContent());

        // Adjust logic to consider each type of file in extension, example: .png, .pdf, .docx, and others
        FileSystemResource file
                = new FileSystemResource(new File(mailRequest.getAttachment()));
        helper.addAttachment("Request File", file);

        sender.send(message);
    }

    @Override
    public void sendBulkMail(List<MailRequestDto> mailRequests) {
        List<UserEntity> usersForNotification = userService.getUsersForNotification();

        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String[] toAddresses = usersForNotification.stream()
                    .map(UserEntity::getMailAddress)
                    .map(this::mailAddressTo)
                    .toArray(String[]::new);
            helper.setTo(toAddresses);
            helper.setSubject("Notificações do Abrigo Dog Feliz");

            StringBuilder htmlBuilder = new StringBuilder();
            htmlBuilder.append("<html>");
            htmlBuilder.append("<head>");
            htmlBuilder.append("<link href='https://fonts.googleapis.com/css2?family=Baloo+2:wght@400;500;600;700&display=swap' rel='stylesheet'>");
            htmlBuilder.append("</head>");

            htmlBuilder.append("<body style='margin:0; padding:20px; background:#052759; font-family:\"Baloo 2\", Arial, sans-serif;'>");
            htmlBuilder.append("<h2 style='color:#FCAD0B; text-align:center; margin-bottom:30px; font-size:28px; font-weight:700;'>Notificações do Abrigo Dog Feliz</h2>");

            for (MailRequestDto mail : mailRequests) {
                htmlBuilder.append("<div style='background:#ffffff; border-radius:16px; padding:22px; margin:0 auto 22px auto; max-width:600px; box-shadow:0 4px 10px rgba(0,0,0,0.15); border:1px solid #e5e7eb; font-family:\"Baloo 2\", Arial, sans-serif;'>");

                htmlBuilder.append("<h3 style='color:#052759; font-size:20px; margin-top:0; margin-bottom:12px; font-weight:700;'>")
                        .append(mail.getSubject())
                        .append("</h3>");

                htmlBuilder.append("<p style='color:#333333; font-size:16px; line-height:1.6; margin:0; font-weight:500;'>")
                        .append(mail.getContent())
                        .append("</p>");

                htmlBuilder.append("</div>");
            }
            htmlBuilder.append(
                    """
                    <p style='color:#FCAD0B; text-align:center; margin-bottom:30px; font-size:28px; font-weight:700;'>Acesse o site <a style="color:white;" href="http://localhost:5173/">abrigo-dog-feliz.com</a></p>
                    """
            );
            htmlBuilder.append("</body></html>");

            helper.setText(htmlBuilder.toString(), true);
            sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new MailSenderException("Error in send bulk mail: " + e.getMessage());
        }
    }


    @Override
    public String mailAddressTo(String mailAddress) {
        return mailAddress.equalsIgnoreCase("default") ? defaultMailAddress : mailAddress;
    }
}
