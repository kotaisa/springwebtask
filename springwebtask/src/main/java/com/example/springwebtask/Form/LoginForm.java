package com.example.springwebtask.Form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty(message = "{jakarta.validation.constraints.NotEmpty.message_ID}")
    private String login_id;

    @NotEmpty(message = "{jakarta.validation.constraints.NotEmpty.message_PASS}")
    private String password;

}
