package com.imageUpload.image.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface FileService {

    String uploadImage(String path, MultipartFile file) throws IOException;

    List<String> getAllImages(String path);
}
