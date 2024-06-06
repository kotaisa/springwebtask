package com.example.springwebtask.Dao;

import com.example.springwebtask.Entity.UpdateRecord;
import com.example.springwebtask.Entity.categoriesRecord;
import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.categoryForm;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    usersRecord findByid(String login_id);

    List<productsRecord> findAll();

    List<productsRecord> searchProducts(String name);

    int insert(String productId, String name, int price, int categoryId, String description);

    List<categoryForm> categoriesName();


    productsRecord findByproductId(String product_id);

    int delete(String product_id);

    UpdateRecord findById(int id);

    UpdateRecord findByproductid(String product_id);

    categoriesRecord getCategoryName(int category_id);

    int update(UpdateRecord updateRecord);

}
