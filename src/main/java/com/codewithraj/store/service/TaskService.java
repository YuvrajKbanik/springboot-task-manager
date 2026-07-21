package com.codewithraj.store.service;

import com.codewithraj.store.entity.Category;
import com.codewithraj.store.entity.Task;
import com.codewithraj.store.entity.User;
import com.codewithraj.store.repository.TaskRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findByUser(currentUser());
    }

    public List<Task> searchTasks(String keyword) {

        return taskRepository.findByUserAndTitleContainingIgnoreCase(
                currentUser(),
                keyword
        );
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public void saveTask(Task task) {
        task.setUser(currentUser());
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();

        if(task.getUser().getId().equals(currentUser().getId())){
            taskRepository.delete(task);
        }
    }
    public long getTotalTasks() {
        return taskRepository.countByUser(currentUser());
        }
     public long getPendingTasks() {
            return taskRepository.countByUserAndStatus(currentUser(),"PENDING");
        }

     public long getCompletedTasks() {
            return taskRepository.countByUserAndStatus(currentUser(),"DONE");
        }
    public List<Task> getSortedTasks(String sortBy) {

        List<Task> tasks = taskRepository.findByUser(currentUser());

        tasks.sort(
                Comparator.comparing(task -> {

                    switch(sortBy){

                        case "title":
                            return task.getTitle();

                        case "status":
                            return task.getStatus();

                        case "priority":
                            return task.getPriority();

                        default:
                            return task.getDueDate().toString();

                    }

                })
        );

        return tasks;

    }
    public Page<Task> getTasksByPage(int page) {

        Pageable pageable = PageRequest.of(page, 4);

        return taskRepository.findByUser(currentUser(),pageable);

    }
    private User currentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return userService.findByUsername(
                authentication.getName()
        );
    }
    public List<Task> getTasksByCategory(Category category) {
        return taskRepository.findByUserAndCategory(currentUser(), category);
    }


}
