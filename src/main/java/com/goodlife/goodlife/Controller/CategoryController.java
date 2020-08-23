package com.goodlife.goodlife.Controller;

import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Product;
import com.goodlife.goodlife.Model.User;
import com.goodlife.goodlife.repository.CategoryRepository;
import com.goodlife.goodlife.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.io.Serializable;

@Slf4j
@Controller
//@SessionAttributes("category")
//@SessionAttributes("team")
public class CategoryController implements Serializable {
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    CategoryRepository service;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/category")
    public String displayCategory(Model model) {

        model.addAttribute("category", service.findAll());

        return "/category";
    }

    @GetMapping("/categoryUserView")
    public String displayCategoryUser(Model model) {

        model.addAttribute("category", service.findAll());

        return "/categoryUserView";
    }

    @GetMapping("/addCategory")
    public String displayAddCategory(Model model) {

        model.addAttribute("category", service.findAll());

        return "/addCategory";
    }





    @PostMapping("/addCategory")
    public String processOrder(@Valid Category category, @AuthenticationPrincipal User user, BindingResult result, SessionStatus sessionStatus, Model model) {
        if (result.hasErrors()) {
            return "addCategory";
        }

        service.save(category);
        model.addAttribute("category", service.findAll());

        return "category";
    }




    @GetMapping("/editCategory/{id}")
    public String showUpdateCategoryForm (@PathVariable("id") long id, Model model){
        Category category = service.findAllById(id);
        model.addAttribute("category", category);
        return "editCategory";
    }

    @PostMapping("/updateCategory/{id}")
    public String updateFixtures ( @PathVariable("id") long id, @Valid Category category,
                                   BindingResult result, Model model){
        if (result.hasErrors()) {
            category.setId((int) id);
            return "editFixtures";
        }


        service.save(category);
        model.addAttribute("category", service.findAll());
        return "redirect:/category";
    }


    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        Category category  = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        categoryRepository.delete(category);
        model.addAttribute("category", categoryRepository.findAll());
        return "redirect:/category";
    }
}
