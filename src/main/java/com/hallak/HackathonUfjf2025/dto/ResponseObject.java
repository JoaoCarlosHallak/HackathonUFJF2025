package com.hallak.HackathonUfjf2025.dto;

import java.time.LocalDateTime;

public record ResponseObject(LocalDateTime dateTime, String payload) {
}
