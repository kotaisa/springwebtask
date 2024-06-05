package com.example.springwebtask.Form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InsertForm {

    @NotEmpty(message = "{jakarta.validation.constraints.NotEmpty.message_productId}")
    private String productId;

    @NotEmpty(message = "{jakarta.validation.constraints.NotEmpty.message_name}")
    private String name;

    @NotNull(message = "{jakarta.validation.constraints.NotNull.message_price}")
    private Integer price;

    private Integer categoryId;

    private String description;
}
