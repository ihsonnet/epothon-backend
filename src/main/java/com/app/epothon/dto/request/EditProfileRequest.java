package com.app.epothon.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditProfileRequest {

    String fullName;

    String email;

    String phoneNo;

    String deliveryAddress;
}
