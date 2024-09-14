package com.challenge.task_management.controller;

import com.challenge.task_management.dto.TaskDto;
import com.challenge.task_management.dto.TaskPage;
import com.challenge.task_management.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskController controller;

    private TaskDto taskDto;
    private TaskPage taskPage;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setName("Task1");

        taskPage = new TaskPage(List.of(taskDto), 1L, 1);
    }

    @Test
    void findAllReturnsTaskPage() {
        when(service.findAll(any(Integer.class), any(Integer.class))).thenReturn(taskPage);

        ResponseEntity<TaskPage> response = controller.findAll(0, 200);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(taskPage, response.getBody());
    }

    @Test
    void findByIdReturnsTaskDto() {
        when(service.findById(1L)).thenReturn(taskDto);

        ResponseEntity<TaskDto> response = controller.findById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(taskDto, response.getBody());
    }

    @Test
    void createReturnsCreatedTaskDto() {
        when(service.create(any(TaskDto.class))).thenReturn(taskDto);

        ResponseEntity<TaskDto> response = controller.create(taskDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(taskDto, response.getBody());
    }

    @Test
    void updateReturnsUpdatedTaskDto() {
        when(service.update(any(Long.class), any(TaskDto.class))).thenReturn(taskDto);

        ResponseEntity<TaskDto> response = controller.update(1L, taskDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(taskDto, response.getBody());
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void filterByPriorityReturnsTaskDtoList() {
        when(service.filterByPriority(1)).thenReturn(List.of(taskDto));

        ResponseEntity<List<TaskDto>> response = controller.filterByPriority(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(taskDto), response.getBody());
    }

    @Test
    void filterByCompletedReturnsTaskDtoList() {
        when(service.filterByCompleted(true)).thenReturn(List.of(taskDto));

        ResponseEntity<List<TaskDto>> response = controller.filterByCompleted(true);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(taskDto), response.getBody());
    }

    @Test
    void findAllSortedByHighlightedAndPriorityReturnsTaskDtoList() {
        when(service.findAllSortedByHighlightedAndPriority()).thenReturn(List.of(taskDto));

        ResponseEntity<List<TaskDto>> response = controller.findAllSortedByHighlightedAndPriority();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(taskDto), response.getBody());
    }
}