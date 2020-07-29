package com.goodlife.goodlife.Model;

import javax.persistence.*;

@Entity
@Table(name = "product_categories")
public class Category {




    //private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_category_id")
    private int id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_description")
    private String desc;



}
