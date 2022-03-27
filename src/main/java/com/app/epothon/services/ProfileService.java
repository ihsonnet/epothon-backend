package com.app.epothon.services;

import com.app.epothon.dto.ApiMessageResponse;
import com.app.epothon.dto.ApiResponse;
import com.app.epothon.dto.request.EditProfileRequest;
import com.app.epothon.dto.response.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(String token);

    ResponseEntity<ApiMessageResponse> editProfile(String token, EditProfileRequest editProfileRequest);

    ResponseEntity<ApiMessageResponse> uploadProfileImage(String token, MultipartFile aFile);
}
