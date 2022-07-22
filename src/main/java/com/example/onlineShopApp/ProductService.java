package com.example.onlineShopApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    public Iterator<Product> getAllProducts(){
        return productDao.findAll().iterator();
    }

    public Product findProductById(int id){
        return productDao.findById(id).get();
    }
}
