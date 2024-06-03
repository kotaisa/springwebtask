package com.example.springwebtask.Service;

import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.categoryForm;

import java.util.List;

public interface ProductService {

    usersRecord findByid(String login_id);

    List<productsRecord> findAll();

    List<productsRecord> searchProducts(String name);

    int categories(String productId, String name, int price, int categoryId, String description);

    List<categoryForm> categoriesName();
}
