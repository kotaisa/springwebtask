package com.example.springwebtask;

import com.example.springwebtask.Entity.categoriesRecord;
import com.example.springwebtask.Entity.productsRecord;
import com.example.springwebtask.Service.PmProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringwebtaskApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringwebtaskApplication.class, args);

        var context = SpringApplication.run(SpringwebtaskApplication.class, args);

//        PmProductService pmProductService = context.getBean(PmProductService.class);
//        List<productsRecord> list = pmProductService.searchProducts("キ");
//        list.forEach(System.out::println);
//
//        PmProductService pmProductService = context.getBean(PmProductService.class);
//        List<categoriesRecord> list = pmProductService.categories();
//        list.forEach(System.out::println);


    }

}
