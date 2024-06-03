package com.example.springwebtask.Dao;

import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.categoryForm;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PmProductDao implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public usersRecord findByid(String login_id) {
        var param = new MapSqlParameterSource();
        param.addValue("login_id", login_id);
        var list = jdbcTemplate.query("SELECT * FROM users WHERE login_id = :login_id ORDER BY id", param,
                new DataClassRowMapper<>(usersRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<productsRecord> findAll() {
        return jdbcTemplate.query("SELECT product_id, products.name, price, categories.name AS categories_name FROM products INNER JOIN categories ON products.category_id = categories.id ORDER BY product_id;",
                new DataClassRowMapper<>(productsRecord.class));
    }

    @Override
    public List<productsRecord> searchProducts(String name) {
        var param = new MapSqlParameterSource();
        param.addValue("name", name);
        var list = jdbcTemplate.query("SELECT product_id, products.name, price, categories.name AS categories_name FROM products INNER JOIN categories ON products.category_id = categories.id WHERE products.name LIKE '%'|| :name || '%' ORDER BY product_id", param, new DataClassRowMapper<>(productsRecord.class));
        return list == null ? null : list;
    }

    @Override
    public int categories(String productId, String name, int price, int categoryId, String description) {
        var param = new MapSqlParameterSource();
        param.addValue("product_id", productId);
        param.addValue("name", name);
        param.addValue("price", price);
        param.addValue("category_id", categoryId);
        param.addValue("description", description);
        return jdbcTemplate.update("INSERT INTO products (product_id, category_id, name, price, description) VALUES" +
                " (:product_id, :category_id, :name, :price, :description)", param);
    }

    @Override
    public List<categoryForm> categoriesName(){;
        return jdbcTemplate.query("SELECT name FROM categories",new DataClassRowMapper<>(categoryForm.class));
    }
//    var param = new MapSqlParameterSource();
//        param.addValue("name", name);
//        param.addValue("price", price);
//        return jdbcTemplate.update("INSERT INTO products (name, price) VALUES(:name, :price)", param);
}
