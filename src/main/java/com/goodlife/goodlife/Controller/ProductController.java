package com.goodlife.goodlife.Controller;


import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Product;
import com.goodlife.goodlife.repository.CategoryRepository;
import com.goodlife.goodlife.repository.ProductRepository;
import com.goodlife.goodlife.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Slf4j
@Controller
//@SessionAttributes("category")
//@SessionAttributes("team")
public class ProductController implements Serializable {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    ;


    @Value("${upload.dir:/Users/eamonmckelvey/Desktop}")
    private String uploadFolder;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductRepository service;

    @Autowired
    CategoryRepository serviceCat;

    @Autowired
    private ProductService productService;


    @GetMapping("/product")
    public String displayCategory(Model model) {

        model.addAttribute("product", service.findAll());

        return "/product";
    }


    @GetMapping("/addProduct")
    public String showSignUpForm(SessionStatus sessionStatus, Category category, Model model) {


        List<Category> list = (List<Category>) serviceCat.findAll();

        model.addAttribute("list", serviceCat.findAll());


        return "addProduct";
    }


    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/addProduct")
    public  String createProduct(@RequestParam("name") String name,
                                    @RequestParam("price") double price, @RequestParam("desc") String description, @RequestParam("quantity") int quantity, @RequestParam int categoryId, Model model, HttpServletRequest request
            , final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;

            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("description: " + descriptions[0]);
            log.info("price: " + price);


            String fname = file.getOriginalFilename();
            log.info("File name {}", fname);

            File f = new File(uploadFolder + File.separator + fname);
            file.transferTo(f);

            Product product = new Product();
            product.setName(names[0]);
            product.setImage(f.getName());
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(descriptions[0]);
            product.setCreateDate(createDate);

            // This doesnt work
            Category category = categoryRepository.findById((long) categoryId).get();
            product.setCategory(category);


            productService.saveProduct(product);
            model.addAttribute("product", service.findAll());

            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));

            return ("product");
            //return new ResponseEntity<>("Product Saved With File - " + f.getAbsolutePath(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
           //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ("addProduct");
    }

    @GetMapping("/editProduct/{id}")
    public String showUpdateCategoryForm (@PathVariable("id") long id, Model model){
        List<Category> list = (List<Category>) serviceCat.findAll();

        model.addAttribute("list", serviceCat.findAll());

        Product product = productRepository.findById(id).get();

        model.addAttribute("product",product);
        return "editProduct";
    }

    @PostMapping("/updateProduct/{id}")
    public String updateFixtures ( @PathVariable("id") long id, @Valid Product product, Category category,
                                   BindingResult result, Model model,final @RequestParam("uploadedImage") MultipartFile file) throws IOException {
//        if (result.hasErrors()) {
//            category.setId((int) id);
//            return "category";
//        }

        Product x = service.findAllById(id);
        Category cat = x.getCategory();

        String fname = file.getOriginalFilename();
        File f = new File(uploadFolder + File.separator + fname);
        file.transferTo(f);
        product.setCategory(cat);
        product.setCreateDate(new Date());
        product.setImage(f.getName());


        productService.saveProduct(product);
        model.addAttribute("product", service.findAll());
        return "redirect:/product";
    }


}