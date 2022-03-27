package com.app.epothon.controller;

import com.app.epothon.dto.ApiMessageResponse;
import com.app.epothon.dto.ApiResponse;
import com.app.epothon.dto.request.EditProfileRequest;
import com.app.epothon.dto.response.UserProfileResponse;
import com.app.epothon.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/profle/")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile(@RequestHeader(name = "Authorization") String token){
        return profileService.getUserProfile(token);
    }

    @PostMapping
    public ResponseEntity<ApiMessageResponse> editProfile(@RequestHeader(name = "Authorization") String token, @RequestBody EditProfileRequest editProfileRequest){
        return profileService.editProfile(token,editProfileRequest);
    }

    @PostMapping("/profileImage")
    public ResponseEntity<ApiMessageResponse> uploadProfileImage(@RequestHeader(name = "Authorization") String token,
                                                                 @RequestParam(value = "image", required = true) MultipartFile aFile) {
        return profileService.uploadProfileImage(token,aFile);
    }
}
