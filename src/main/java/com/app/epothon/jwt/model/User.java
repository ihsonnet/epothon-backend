package com.app.epothon.jwt.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotBlank
    @Size(min = 11, max = 11)
    String phoneNo;

    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;

    @Column(unique = true)
    @NotBlank
    @NaturalId
    @Size(min = 3, max = 100)
    private String username;

    @Column(unique = true)
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @JsonIgnore
    int generatedOTP;

    @JsonIgnore
    boolean passwordReset;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private String referralUsername;

    private String createdBy;
    private String createdOn;
    private String joiningDate;
    private String ExpiryDate;

    private String profileImage;

}