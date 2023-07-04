package com.imageUpload.image.services;

import com.imageUpload.image.model.FileEntity;
import com.imageUpload.image.repo.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements  FileService{

    @Autowired
    FileRepository fileRepository;
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String name = file.getOriginalFilename();

        // Générer un nom de fichier aléatoire
        String randomID = UUID.randomUUID().toString();
        String fileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        // Chemin complet
        String filePath = path + File.separator + fileName;

        // Créer le dossier s'il n'existe pas
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // Copier le fichier
        Files.copy(file.getInputStream(), Paths.get(filePath));

        // Enregistrer le nom du fichier dans la base de données
        FileEntity uploadedFile = new FileEntity();
        uploadedFile.setFileName(fileName);
        fileRepository.save(uploadedFile);

        return fileName;
    }
    @Override
    public List<String> getAllImages(String path) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        List<String> imageNames = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    imageNames.add(file.getName());
                }
            }
        }

        return imageNames;
    }
}


