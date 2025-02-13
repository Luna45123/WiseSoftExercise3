package com.wisesoft.seminar.controller;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.wisesoft.seminar.service.SeminarService;

@RestController
@RequestMapping("/api/reports")
public class SeminarController {
    private final SeminarService seminarService;
    
    public SeminarController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    @SuppressWarnings("null")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadTextFile(@RequestPart("file") MultipartFile file) throws IOException {
        if (file.getContentType() == null || !file.getContentType().equals("text/plain")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only text files are allowed");
            
        }
        return ResponseEntity.status(HttpStatus.OK).body(seminarService.scheduleSeminar(file));
    }
}
