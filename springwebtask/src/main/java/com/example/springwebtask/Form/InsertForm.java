package com.example.springwebtask.Form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class InsertForm {

//    @NotEmpty
    private String productId;

//    @NotEmpty
    private String name;

//    @NotEmpty
    private int price;

//    @NotEmpty
    private int categoryId;

//    @NotEmpty
    private String description;
}
