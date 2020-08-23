package com.goodlife.goodlife.service;

import com.goodlife.goodlife.Model.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    void saveProduct(Product product);
    List<Product> getAllActiveProducts();
    Optional<Product> getProductById(Long id);
}