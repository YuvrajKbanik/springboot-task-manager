package com.codewithraj.store.repository;

import com.codewithraj.store.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}