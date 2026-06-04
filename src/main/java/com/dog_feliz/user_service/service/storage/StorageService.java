package com.dog_feliz.user_service.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

public interface StorageService {
    List<String> uploadAll(List<MultipartFile> files, String folder);

    String upload(MultipartFile file, String folder);

    String getPresignedUrl(String key, Duration expiration);

    void delete(String key);
}
