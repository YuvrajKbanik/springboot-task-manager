package com.codewithraj.store.repository;

import com.codewithraj.store.entity.Category;
import com.codewithraj.store.entity.Task;
import com.codewithraj.store.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByUser(User user, Pageable pageable);

    List<Task> findByUserAndTitleContainingIgnoreCase(User user,String keyword);

    List<Task> findByUser(User user);

    long countByUser(User user);

    long countByUserAndStatus(User user,String status);
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    long countByStatus(String status);
    List<Task> findByUserAndCategory(User user, Category category);
}