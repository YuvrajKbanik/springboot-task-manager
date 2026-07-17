package com.codewithraj.store.controller;

import com.codewithraj.store.entity.Task;
import com.codewithraj.store.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", service.getAllTasks());
        model.addAttribute("task", new Task());
        model.addAttribute("totalTasks", service.getTotalTasks());

        model.addAttribute("pendingTasks", service.getPendingTasks());

        model.addAttribute("completedTasks", service.getCompletedTasks());
        return "tasks";

    }
    @GetMapping("/search")
    public String searchTasks(@RequestParam String keyword,
                              Model model) {

        model.addAttribute(
                "tasks",
                service.searchTasks(keyword)
        );
        model.addAttribute("totalTasks", service.getTotalTasks());

        model.addAttribute("pendingTasks", service.getPendingTasks());

        model.addAttribute("completedTasks", service.getCompletedTasks());

        model.addAttribute("task", new Task());

        model.addAttribute("keyword", keyword);

        return "tasks";
    }

    @PostMapping("/add")
    public String addTask(Task task) {
        service.saveTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", service.getTask(id));
        model.addAttribute("tasks", service.getAllTasks());
        return "tasks";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, Task task) {
        task.setId(id);
        service.saveTask(task);;
        return "redirect:/tasks";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }
    @GetMapping("/sort")
    public String sortTasks(@RequestParam String sortBy,
                            Model model) {

        model.addAttribute(
                "tasks",
                service.getSortedTasks(sortBy)
        );

        model.addAttribute("task", new Task());

        model.addAttribute("keyword", "");

        model.addAttribute("totalTasks", service.getTotalTasks());
        model.addAttribute("pendingTasks", service.getPendingTasks());
        model.addAttribute("completedTasks", service.getCompletedTasks());

        return "tasks";
    }
}
