package com.goodlife.goodlife.Controller;


import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Product;
import com.goodlife.goodlife.repository.CategoryRepository;
import com.goodlife.goodlife.repository.ProductRepository;
import com.goodlife.goodlife.repository.UserRepository;
import com.goodlife.goodlife.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Blob;
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
    };


    @Value("goodlife/src/main/java/com/goodlife/goodlife/Uploads")
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


        List<Category>list = (List<Category>) serviceCat.findAll();

            model.addAttribute("list",serviceCat.findAll());


        return "addProduct";
    }


    @PostMapping("/addProduct")
    public @ResponseBody
    ResponseEntity<?> createProduct(@RequestParam("name") String name,
                                     @RequestParam("price") double price, @RequestParam("desc") String description, @RequestParam("quantity") int quantity, @RequestParam Category category,Model model, HttpServletRequest request
            , final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            log.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            log.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            log.info("Name: " + names[0]+" "+filePath);
            log.info("description: " + descriptions[0]);
            log.info("price: " + price);
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    log.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                log.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Product product = new Product();
            product.setName(names[0]);
            product.setImage(imageData);
            product.setPrice(price);
            product.setDesc(descriptions[0]);
            product.setCreateDate(createDate);

            // This doesnt work
            product.setCategory(category);


            productService.saveProduct(product);
            model.addAttribute("product", service.findAll());

            log.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }





}