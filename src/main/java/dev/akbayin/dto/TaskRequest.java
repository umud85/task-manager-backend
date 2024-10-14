package dev.akbayin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequest(@NotNull @Size(min = 1, max = 255) String description) {
}
