package com.challenge.task_management.dto;

import java.util.List;

public record TaskPage(List<TaskDto> tasks, long totalElements, int totalPages) {
}
