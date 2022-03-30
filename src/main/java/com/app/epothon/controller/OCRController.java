package com.app.epothon.controller;

import com.app.epothon.dto.ApiResponse;
import com.app.epothon.model.ScannerModel;
import com.app.epothon.services.OCRService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ocr/")
public class OCRController {

    private OCRService ocrService;

    @PostMapping("/api/ocr")
    public ResponseEntity<ApiResponse<ScannerModel>> generateText(@RequestParam("DestinationLanguage") String destinationLanguage,
                                                                  @RequestParam("Image") MultipartFile image) throws IOException {

        return ocrService.generateText(destinationLanguage, image);
    }
}