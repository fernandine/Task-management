package com.challenge.task_management.service;

import com.challenge.task_management.dto.TaskDto;
import com.challenge.task_management.dto.TaskPage;
import com.challenge.task_management.entity.Task;
import com.challenge.task_management.repository.TaskRepository;
import com.challenge.task_management.service.exceptions.DatabaseException;
import com.challenge.task_management.service.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService service;

    private Task task;
    private TaskDto taskDto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setName("Test Task");
    }

    @Test
    void findAllReturnsTaskPage() {
        Page<Task> taskPage = new PageImpl<>(Collections.singletonList(task));
        when(repository.findAll(any(PageRequest.class))).thenReturn(taskPage);
        when(modelMapper.map(anyList(), eq(new TypeToken<List<TaskDto>>() {
        }.getType()))).thenReturn(Collections.singletonList(taskDto));

        TaskPage result = service.findAll(0, 10);

        assertEquals(1, result.totalPages());
        assertEquals(1, result.tasks().size());
    }

    @Test
    void findByIdReturnsTaskDto() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(task));
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        TaskDto result = service.findById(1L);

        assertEquals(taskDto, result);
    }

    @Test
    void findByIdThrowsResourceNotFoundException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    void createReturnsCreatedTaskDto() {
        when(repository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        TaskDto result = service.create(taskDto);

        assertEquals(taskDto, result);
    }

    @Test
    void updateReturnsUpdatedTaskDto() {
        when(repository.getReferenceById(anyLong())).thenReturn(task);
        when(repository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        TaskDto result = service.update(1L, taskDto);

        assertEquals(taskDto, result);
    }

    @Test
    void updateThrowsResourceNotFoundException() {
        when(repository.getReferenceById(anyLong())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, taskDto));
    }

    @Test
    void deleteRemovesTask() {
        doNothing().when(repository).deleteById(anyLong());

        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    void deleteThrowsResourceNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void deleteThrowsDatabaseException() {
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(anyLong());

        assertThrows(DatabaseException.class, () -> service.delete(1L));
    }

    @Test
    void filterByPriorityReturnsTaskDtos() {
        when(repository.findByPriority(anyInt())).thenReturn(Collections.singletonList(task));
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        List<TaskDto> result = service.filterByPriority(1);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    @Test
    void filterByCompletedReturnsTaskDtos() {
        when(repository.findByCompleted(anyBoolean())).thenReturn(Collections.singletonList(task));
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        List<TaskDto> result = service.filterByCompleted(true);

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }

    @Test
    void findAllSortedByHighlightedAndPriorityReturnsSortedTaskDtos() {
        when(repository.findAll()).thenReturn(Collections.singletonList(task));
        when(modelMapper.map(any(Task.class), any(Class.class))).thenReturn(taskDto);

        List<TaskDto> result = service.findAllSortedByHighlightedAndPriority();

        assertEquals(1, result.size());
        assertEquals(taskDto, result.get(0));
    }
}