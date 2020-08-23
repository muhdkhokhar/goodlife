package com.goodlife.goodlife.repository;

import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findAllById(Long id);



}
