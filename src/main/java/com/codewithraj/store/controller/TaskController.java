package com.codewithraj.store.controller;

import com.codewithraj.store.entity.Task;
import com.codewithraj.store.service.TaskService;
import org.springframework.data.domain.Page;
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
    public String listTasks(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<Task> taskPage = service.getTasksByPage(page);

        model.addAttribute("tasks", taskPage.getContent());

        model.addAttribute("currentPage", page);

        model.addAttribute("totalPages", taskPage.getTotalPages());

        addCommonAttributes(model);


        return "tasks";
    }
    @GetMapping("/search")
    public String searchTasks(@RequestParam String keyword,
                              Model model) {

        model.addAttribute(
                "tasks",
                service.searchTasks(keyword)
        );
        addCommonAttributes(model);

        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);
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
    public String editTask(@PathVariable Long id,
                           @RequestParam(defaultValue = "0") int page,
                           Model model) {

        Page<Task> taskPage = service.getTasksByPage(page);

        model.addAttribute("tasks", taskPage.getContent());

        model.addAttribute("task", service.getTask(id));

        model.addAttribute("currentPage", page);

        model.addAttribute("totalPages", taskPage.getTotalPages());

        model.addAttribute("totalTasks", service.getTotalTasks());

        model.addAttribute("pendingTasks", service.getPendingTasks());

        model.addAttribute("completedTasks", service.getCompletedTasks());

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



        model.addAttribute("keyword", "");
        addCommonAttributes(model);

        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);

        return "tasks";
    }
    private void addCommonAttributes(Model model) {

        model.addAttribute("task", new Task());

        model.addAttribute("totalTasks", service.getTotalTasks());

        model.addAttribute("pendingTasks", service.getPendingTasks());

        model.addAttribute("completedTasks", service.getCompletedTasks());

    }

}
