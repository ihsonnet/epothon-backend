package com.app.epothon.jwt.controller;

import com.app.epothon.dto.ApiMessageResponse;
import com.app.epothon.jwt.dto.request.LoginForm;
import com.app.epothon.jwt.dto.request.SignUpForm;
import com.app.epothon.jwt.services.ForgetPasswordService;
import com.app.epothon.jwt.services.SignUpAndSignInService;
import javassist.bytecode.DuplicateMemberException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private SignUpAndSignInService signUpAndSignInService;
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return ResponseEntity.ok(signUpAndSignInService.signIn(loginRequest));
    }

    @PostMapping("/signup")
    public Object userSignup(@RequestBody SignUpForm signUpRequest) throws DuplicateMemberException {
        return signUpAndSignInService.userSignup(signUpRequest);
    }

    @PutMapping("/generateOTP")
    public ResponseEntity<ApiMessageResponse> generateOTP(@RequestParam String email){
        return forgetPasswordService.generateOTP(email);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ApiMessageResponse> changePassword(@RequestParam Integer otp, @RequestParam String email,
                                                          @RequestParam String newPassword){
        return forgetPasswordService.changePassword(otp,email,newPassword);
    }

    @GetMapping("/serverCheck")
    public String getServerStatStatus() {
        return "The Server is Running";
    }

}
