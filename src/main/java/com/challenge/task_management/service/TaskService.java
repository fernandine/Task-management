package com.challenge.task_management.service;

import com.challenge.task_management.dto.TaskDto;
import com.challenge.task_management.dto.TaskPage;
import com.challenge.task_management.entity.Task;
import com.challenge.task_management.repository.TaskRepository;
import com.challenge.task_management.service.exceptions.DatabaseException;
import com.challenge.task_management.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public TaskPage findAll(int page, int pageSize) {
        Page<Task> taskpage = repository.findAll(PageRequest.of(page, pageSize));
        List<TaskDto> dtos = modelMapper.map(
                taskpage.getContent(),
                new TypeToken<List<TaskDto>>() {
                }.getType()
        );
        return new TaskPage(dtos, taskpage.getTotalElements(), taskpage.getTotalPages());
    }

    @Transactional(readOnly = true)
    public TaskDto findById(Long id) {
        Optional<Task> obj = repository.findById(id);
        Task entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return modelMapper.map(entity, TaskDto.class);
    }

    @Transactional
    public TaskDto create(TaskDto dto) {
        Task entity = new Task();

        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return modelMapper.map(entity, TaskDto.class);
    }

    @Transactional
    public TaskDto update(Long id, TaskDto dto) {
        try {
            Task entity = repository.getReferenceById(id);

            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return modelMapper.map(entity, TaskDto.class);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional()
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional(readOnly = true)
    public List<TaskDto> filterByPriority(Integer priority) {
        List<Task> list = repository.findByPriority(priority);
        return list.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> filterByCompleted(Boolean completed) {
        List<Task> list = repository.findByCompleted(completed);
        return list.stream()
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAllSortedByHighlightedAndPriority() {
        List<Task> list = repository.findAll();
        return list.stream()
                .sorted(Comparator.comparing(Task::getHighlighted, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Task::getPriority, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(task -> modelMapper.map(task, TaskDto.class))
                .collect(Collectors.toList());
    }

    private void copyDtoToEntity(TaskDto dto, Task entity) {

        entity.setName(dto.getName());
        entity.setCompleted(dto.getCompleted());
        entity.setPriority(dto.getPriority());
    }
}
