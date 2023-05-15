package com.imageUpload.image.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements  FileService{

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // File name
        String name= file.getOriginalFilename();


        // random name generate file
        String randomID = UUID.randomUUID().toString();
        String fileName1= randomID.concat(name.substring(name.lastIndexOf(".")));


        // fullPath
        String  filePath = path + File.separator+fileName1;


        // create folder if not created
        File f = new File(path);

        if (!f.exists()){
            f.mkdir();
        }


        // file copy

        Files.copy(file.getInputStream(), Paths.get(filePath));



        return name;

    }
}


