package com.mita.dto.login;

import com.mita.validation.Compare;
import com.mita.validation.UniqueUsername;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
@Compare(message = "Password is not matched", firstField = "password",
secondField = "passwordConfirm")
public class RegisterDTO {

    @UniqueUsername(message = "This username already exist")
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Password is required")
    private String passwordConfirm;
    @NotBlank(message = "Role is required")
    private String role;
}
