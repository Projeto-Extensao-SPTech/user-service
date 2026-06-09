package com.dog.feliz.user.service.service.storage;

import com.dog.feliz.user.service.config.AwsS3Properties;
import com.dog.feliz.user.service.shared.exception.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    private final S3Presigner presigner;

    private final AwsS3Properties props;

    @Override
    public List<String> uploadAll(List<MultipartFile> files, String folder) {
        List<CompletableFuture<String>> futures = files.stream()
                .map(file -> CompletableFuture.supplyAsync(() -> upload(file, folder)))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }

    @Override
    public String upload(MultipartFile file, String folder) {
        String key = "%s/%s_%s".formatted(folder, UUID.randomUUID(), file.getOriginalFilename());
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(props.getBucketName())
                            .key(key)
                            .contentType(file.getContentType())
                            .contentLength(file.getSize())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (IOException e) {
            throw new StorageException("Falha ao fazer upload do arquivo: " + file.getOriginalFilename(), e);
        }
        return key;
    }

    @Override
    public String getPresignedUrl(String key, Duration expiration) {
        GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                .signatureDuration(expiration)
                .getObjectRequest(requestObject -> requestObject.bucket(props.getBucketName()).key(key))
                .build();
        return presigner.presignGetObject(request).url().toString();
    }

    @Override
    public void delete(String key) {
        s3Client.deleteObject(requestObject -> requestObject.bucket(props.getBucketName()).key(key));
    }
}

