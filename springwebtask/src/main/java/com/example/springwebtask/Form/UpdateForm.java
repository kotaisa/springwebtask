package com.example.springwebtask.Form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateForm {

    private int id;

    @NotBlank(message = "{jakarta.validation.constraints.NotBlank.message_product_id}")
    private String product_id;

    @NotBlank(message = "{jakarta.validation.constraints.NotBlank.message_name}")
    private String name;

    @NotNull(message = "{jakarta.validation.constraints.NotNull.message_price}")
    private Integer price;

    private int category_id;

    private String description;

}
