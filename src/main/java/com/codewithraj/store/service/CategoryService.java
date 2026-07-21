package com.codewithraj.store.service;

import com.codewithraj.store.entity.Category;
import com.codewithraj.store.entity.User;
import com.codewithraj.store.repository.CategoryRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final UserService userService;

    public CategoryService(CategoryRepository repository,
                           UserService userService){

        this.repository=repository;
        this.userService=userService;

    }
    public Category getCategory(Long id) {
        return repository.findById(id).orElseThrow();
    }
    private User currentUser(){

        Authentication auth=
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return userService.findByUsername(auth.getName());

    }

    public List<Category> getCategories(){

        return repository.findByUser(currentUser());

    }

    public void save(Category category){

        category.setUser(currentUser());

        repository.save(category);

    }

}