package org.jwttest.web.rest;

import java.util.List;

import org.jwttest.domain.Task;
import org.jwttest.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskRepository.class);

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> listTask() {
        log.debug("REST request to get all Tasks");
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task save(@RequestBody Task t){
        log.debug("REST request to save a Task : {}", t);
        return taskRepository.save(t);
    }

}

