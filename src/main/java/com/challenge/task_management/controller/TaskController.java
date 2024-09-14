package com.challenge.task_management.controller;

import com.challenge.task_management.dto.TaskDto;
import com.challenge.task_management.dto.TaskPage;
import com.challenge.task_management.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public ResponseEntity<TaskPage> findAll(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "200") int pageSize) {
        return ResponseEntity.ok().body(service.findAll(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto dto) {
        var newDto = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(@PathVariable Long id, @RequestBody @Valid TaskDto dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter/priority")
    public ResponseEntity<List<TaskDto>> filterByPriority(@RequestParam int priority) {
        return ResponseEntity.ok().body(service.filterByPriority(priority));
    }

    @GetMapping("/filter/completed")
    public ResponseEntity<List<TaskDto>> filterByCompleted(@RequestParam(required = false) Boolean completed) {
        return ResponseEntity.ok().body(service.filterByCompleted(completed));
    }

    @GetMapping("/sorted/highlighted-priority")
    public ResponseEntity<List<TaskDto>> findAllSortedByHighlightedAndPriority() {
        return ResponseEntity.ok().body(service.findAllSortedByHighlightedAndPriority());
    }
}
