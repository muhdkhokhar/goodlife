package com.goodlife.goodlife.Controller;

import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.Category;
import com.goodlife.goodlife.Model.User;
import com.goodlife.goodlife.repository.CategoryRepository;
import com.goodlife.goodlife.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.swing.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Controller
@SessionAttributes("category")
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

    @GetMapping("/addCategory")
    public String showSignUpForm(SessionStatus sessionStatus,
                                 @AuthenticationPrincipal User user, Model model) {
        //if the user has already the team we should not let them add another one
        //this is due to having one to one rleationship
        //long userHasTeamCount = service.countAllByUser(user);
        int returnMsg;
//        if (userHasTeamCount > 0) {
//
//
//            //JFrame parent = new JFrame();
//
//            JOptionPane.showMessageDialog(null,
//                    "YOU ALREADY HAVE A TEAM, ONE PER USER.");
//            return "redirect:team";
//
//        }

        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String processOrder(@Valid Category category, BindingResult result, SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user, Model model) {
        if (result.hasErrors()) {
            return "addCategory";
        }

        //category.setUser(user);
        service.save(category);
        model.addAttribute("category", service.findAll());
        return "category";
    }
}
