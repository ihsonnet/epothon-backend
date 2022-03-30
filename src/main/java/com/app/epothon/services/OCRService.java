package com.app.epothon.services;

import com.app.epothon.dto.ApiResponse;
import com.app.epothon.model.ScannerModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface OCRService {
    ResponseEntity<ApiResponse<ScannerModel>> generateText(String destinationLanguage, MultipartFile image);
}
