package com.example.springwebtask.Service;

import com.example.springwebtask.Dao.PmProductDao;
import com.example.springwebtask.Entity.UpdateRecord;
import com.example.springwebtask.Entity.categoriesRecord;
import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Entity.usersRecord;
import com.example.springwebtask.Form.categoryForm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PmProductService implements ProductService {

    @Autowired
    private PmProductDao pmProductDao;

    @Override
    public usersRecord findByid(String login_id) {
        return pmProductDao.findByid(login_id);
    }

    @Override
    public List<productsRecord> findAll() {
        return pmProductDao.findAll();
    }

    @Override
    public List<productsRecord> searchProducts(String name) {
        return pmProductDao.searchProducts(name);
    }

    @Override
    public int insert(String productId, String name, int price, int categoryId, String description) {
        return pmProductDao.insert(productId, name, price, categoryId, description);
    }

    @Override
    public List<categoryForm> categoriesName() {
        return pmProductDao.categoriesName();
    }

    @Override
    public productsRecord findByproductId(String product_id) {
        return pmProductDao.findByproductId(product_id);
    }

    @Override
    public int delete(String product_id) {
        return pmProductDao.delete(product_id);
    }

    @Override
    public UpdateRecord findById(int id) {
        return pmProductDao.findById(id);
    }

    @Override
    public UpdateRecord findByproductid(String product_id){
        return pmProductDao.findByproductid(product_id);
    }

    @Override
    public categoriesRecord getCategoryName(int category_id) {
        return pmProductDao.getCategoryName(category_id);
    }

    @Override
    public int update(UpdateRecord updateRecord) {
        return pmProductDao.update(updateRecord);
    }
}
