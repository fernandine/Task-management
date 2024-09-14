package com.challenge.task_management.repository;

import com.challenge.task_management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(Boolean completed);
    List<Task> findByPriority(Integer priority);
}
