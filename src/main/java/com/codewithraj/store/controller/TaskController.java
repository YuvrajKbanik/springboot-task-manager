package com.codewithraj.store.controller;

import com.codewithraj.store.entity.Task;
import com.codewithraj.store.repository.TaskRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repo;

    public TaskController(TaskRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", repo.findAll());
        model.addAttribute("task", new Task());
        return "tasks";   // Looks for src/main/resources/templates/tasks.html
    }
    @GetMapping("/search")
    public String searchTasks(@RequestParam String keyword,
                              Model model) {

        model.addAttribute(
                "tasks",
                repo.findByTitleContainingIgnoreCase(keyword)
        );

        model.addAttribute("task", new Task());

        model.addAttribute("keyword", keyword);

        return "tasks";
    }

    @PostMapping("/add")
    public String addTask(Task task) {
        repo.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", repo.findById(id).orElseThrow());
        model.addAttribute("tasks", repo.findAll());
        return "tasks";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, Task task) {
        task.setId(id);
        repo.save(task);
        return "redirect:/tasks";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }
}
