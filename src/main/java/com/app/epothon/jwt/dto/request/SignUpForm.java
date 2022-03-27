package com.app.epothon.jwt.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    @NotBlank
    @Size(min = 11, max = 11)
    String phoneNo;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

}