package com.imageUpload.image.controller;

import com.imageUpload.image.playload.FileResponse;
import com.imageUpload.image.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private  String path;

    @RequestMapping(value="/upload",method= RequestMethod.POST,headers="accept=Application/json")
    public ResponseEntity<FileResponse> fileUpload(
            @RequestParam("image")MultipartFile image
            ){


        String fileName = null;
        try {
            fileName = this.fileService.uploadImage(path,image );
        } catch (IOException e) {
            e.printStackTrace();
            return  new ResponseEntity<>(new FileResponse(null,"ERREURR fichier non envoyer"), HttpStatus.OK);

        }

        return  new ResponseEntity<>(new FileResponse(fileName,"fichier uploader avec success"), HttpStatus.OK);
    }


    @RequestMapping(value="/images",method= RequestMethod.GET,headers="accept=Application/json")
    /*public ResponseEntity<List<String>> getAllImages() {
        List<String> imageNames = fileService.getAllImages(path);
        return ResponseEntity.ok(imageNames);
    }

    */

    public ResponseEntity<List<String>> getAllImageURLs() {
        List<String> imageNames = fileService.getAllImages(path);
        List<String> imageURLs = new ArrayList<>();

        for (String imageName : imageNames) {
            String imageURL = "http://localhost:8080/images/" + imageName;
            imageURLs.add(imageURL);
        }

        return ResponseEntity.ok(imageURLs);
    }

}
