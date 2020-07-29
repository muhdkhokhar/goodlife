package com.goodlife.goodlife.repository;

import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findAllById(Long id);

}

