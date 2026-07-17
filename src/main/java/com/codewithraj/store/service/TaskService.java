package com.codewithraj.store.service;

import com.codewithraj.store.entity.Task;
import com.codewithraj.store.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> searchTasks(String keyword) {
        return taskRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
    public long getTotalTasks() {
            return taskRepository.count();
        }
     public long getPendingTasks() {
            return taskRepository.countByStatus("PENDING");
        }

     public long getCompletedTasks() {
            return taskRepository.countByStatus("DONE");
        }
    public List<Task> getSortedTasks(String sortBy) {

        return taskRepository.findAll(Sort.by(sortBy));

    }
    public Page<Task> getTasksByPage(int page) {

        Pageable pageable = PageRequest.of(page, 4);

        return taskRepository.findAll(pageable);

    }
    }
