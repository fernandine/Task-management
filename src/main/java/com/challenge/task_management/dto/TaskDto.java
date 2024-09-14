package com.challenge.task_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto implements Serializable {

    private Long id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, message = "Task name must be at least 3 characters")
    private String name;
    private Boolean completed;
    private Integer priority;
    private Boolean highlighted;

}
