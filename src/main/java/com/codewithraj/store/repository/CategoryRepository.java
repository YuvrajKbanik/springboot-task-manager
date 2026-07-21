package com.codewithraj.store.repository;

import com.codewithraj.store.entity.Category;
import com.codewithraj.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository
        extends JpaRepository<Category,Long>{

    List<Category> findByUser(User user);

}