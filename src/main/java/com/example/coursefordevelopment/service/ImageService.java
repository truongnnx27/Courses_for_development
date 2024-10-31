package com.example.coursefordevelopment.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageService {

    public String base64Image(String nameImage, String pathImage) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(pathImage + nameImage));
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return base64Image;
    }

}
