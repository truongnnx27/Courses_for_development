package com.example.coursefordevelopment.service;

import com.example.coursefordevelopment.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface S3Service {
    List<Video> uploadFiles(List<MultipartFile> files) throws IOException;
}
