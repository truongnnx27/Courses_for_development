package coursefordevelopment.controller;

import com.example.coursefordevelopment.entity.Video;
import com.example.coursefordevelopment.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/s3/upload")
public class UploadS3 {
    private final S3Service s3Service;

    public UploadS3(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadImages(@RequestParam("img") MultipartFile image) throws IOException {
        return new ResponseEntity<>(Map.of("urlImg", s3Service.uploadImage(image)), HttpStatus.OK);
    }

    @PostMapping("/video")
    public ResponseEntity<List<Video>> uploadFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        List<Video> videos = s3Service.uploadFiles(files);
        return ResponseEntity.ok(videos);
    }


}
