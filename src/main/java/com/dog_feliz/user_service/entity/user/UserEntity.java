package com.dog_feliz.user_service.entity.user;

import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.shared.crypto.StringCryptoConverter;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_tb")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType type;

//    @Convert(converter = StringCryptoConverter.class)
    private String name;

//    @Convert(converter = StringCryptoConverter.class)
    private String document;

//    @Convert(converter = StringCryptoConverter.class)
    private String phone;

    @Column(name = "mail_address")
//    @Convert(converter = StringCryptoConverter.class)
    private String mailAddress;

//    @Convert(converter = StringCryptoConverter.class)
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "receive_notifications")
    private Boolean receiveNotifications = true;

    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    public UserEntity() {
    }

    public UserEntity(UserRequestDto userRequestDto, AddressEntity addressEntity, String passwordEncoded) {
        this.type = userRequestDto.getType();
        this.name = userRequestDto.getName();
        this.document = userRequestDto.getDocument();
        this.phone = userRequestDto.getPhone();
        this.mailAddress = userRequestDto.getMailAddress();
        this.password = passwordEncoded;
        this.address = addressEntity;
        this.isAdmin = userRequestDto.getIsAdmin() != null ? userRequestDto.getIsAdmin() : false;
    }

    public UserEntity(Long id, UserRequestDto userRequestDto, AddressEntity addressEntity) {
        this.id = id;
        this.type = userRequestDto.getType();
        this.name = userRequestDto.getName();
        this.document = userRequestDto.getDocument();
        this.phone = userRequestDto.getPhone();
        this.mailAddress = userRequestDto.getMailAddress();
        this.password = userRequestDto.getPassword();
        this.address = addressEntity;
    }

    public UserEntity(UserEntity userEntity, String newPassword) {
        this.id = userEntity.id;
        this.type = userEntity.type;
        this.name = userEntity.name;
        this.document = userEntity.document;
        this.phone = userEntity.phone;
        this.mailAddress = userEntity.mailAddress;
        this.address = userEntity.address;
        this.receiveNotifications = userEntity.receiveNotifications;
        this.createdAt = userEntity.createdAt;
        this.isAdmin = userEntity.isAdmin;
        this.password = newPassword;
    }


    @Override
    public String getUsername() {
        return mailAddress;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Long getId() {
        return id;
    }

    public UserType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getPhone() {
        return phone;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", document='" + document + '\'' +
                ", phone='" + phone + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", receiveNotifications=" + receiveNotifications +
                ", createdAt=" + createdAt +
                '}';
    }
  
    public Boolean getReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(Boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
