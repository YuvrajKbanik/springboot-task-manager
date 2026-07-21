package com.codewithraj.store.controller;

import com.codewithraj.store.entity.Category;
import com.codewithraj.store.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service){

        this.service=service;

    }

    @GetMapping
    public String categories(Model model){

        model.addAttribute("category",
                new Category());

        model.addAttribute("categories",
                service.getCategories());

        return "categories";

    }

    @PostMapping
    public String save(@ModelAttribute Category category){

        service.save(category);

        return "redirect:/categories";

    }

}