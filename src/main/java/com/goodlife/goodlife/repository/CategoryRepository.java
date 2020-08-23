package com.goodlife.goodlife.repository;

import com.goodlife.goodlife.Model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findAllById(Long id);

    Category findByname(String name);

}

