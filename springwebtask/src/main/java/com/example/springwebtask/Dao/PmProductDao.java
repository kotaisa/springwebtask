package com.example.springwebtask.Dao;

import com.example.springwebtask.Entity.UpdateRecord;
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
        return jdbcTemplate.query("SELECT product_id, products.name, price, categories.name AS categories_name, description FROM products INNER JOIN categories ON products.category_id = categories.id ORDER BY product_id;",
                new DataClassRowMapper<>(productsRecord.class));
    }

    @Override
    public List<productsRecord> searchProducts(String name) {
        var param = new MapSqlParameterSource();
        param.addValue("name", name);
        var list = jdbcTemplate.query("SELECT product_id, products.name, price, categories.name AS categories_name, description FROM products INNER JOIN categories ON products.category_id = categories.id WHERE products.name LIKE '%'|| :name || '%' ORDER BY product_id", param, new DataClassRowMapper<>(productsRecord.class));
        return list == null ? null : list;
    }

    @Override
    public int insert(String productId, String name, int price, int categoryId, String description) {
        var param = new MapSqlParameterSource();
        param.addValue("product_id", productId);
        param.addValue("name", name);
        param.addValue("price", price);
        param.addValue("category_id", categoryId);
        param.addValue("description", description);
        return jdbcTemplate.update("INSERT INTO products (product_id, category_id, name, price, description) VALUES" +
                " (:product_id, :category_id, :name, :price, :description)", param);
    }

    //findCategory
    @Override
    public List<categoryForm> categoriesName(){;
        return jdbcTemplate.query("SELECT id, name FROM categories",new DataClassRowMapper<>(categoryForm.class));
    }

    @Override
    public productsRecord findByproductId(String product_id) {
        var param = new MapSqlParameterSource();
        param.addValue("product_id", product_id);
        var list = jdbcTemplate.query("SELECT product_id, products.name, price, categories.name AS categories_name, description FROM products INNER JOIN categories ON products.category_id = categories.id WHERE product_id = :product_id ORDER BY product_id", param,
                new DataClassRowMapper<>(productsRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int delete(String product_id) {
        var param = new MapSqlParameterSource();
        param.addValue("product_id", product_id);
        return jdbcTemplate.update("DELETE FROM products WHERE product_id = :product_id", param);
    }


    @Override
    public UpdateRecord findById(int id) {
        var param = new MapSqlParameterSource();
        param.addValue("id", id);
        var list = jdbcTemplate.query("SELECT * FROM products WHERE id = :id ORDER BY id", param,
                new DataClassRowMapper<>(UpdateRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public UpdateRecord findByproductid(String product_id) {
        var param = new MapSqlParameterSource();
        param.addValue("product_id", product_id);
        var list = jdbcTemplate.query("SELECT * FROM products WHERE product_id = :product_id ORDER BY id", param,
                new DataClassRowMapper<>(UpdateRecord.class));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int update(UpdateRecord updateRecord) {
        var param = new MapSqlParameterSource();
        param.addValue("id", updateRecord.id());
        param.addValue("product_id", updateRecord.product_id());
        param.addValue("name", updateRecord.name());
        param.addValue("price", updateRecord.price());
        param.addValue("category_id", updateRecord.category_id());
        param.addValue("description", updateRecord.description());
        return jdbcTemplate.update("UPDATE products SET product_id = :product_id, name = :name, price = :price, category_id = :category_id, description = :description WHERE id = :id", param);
    }
}
