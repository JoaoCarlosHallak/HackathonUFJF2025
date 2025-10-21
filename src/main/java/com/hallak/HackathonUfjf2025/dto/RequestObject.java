package com.hallak.HackathonUfjf2025.dto;


import java.time.LocalDateTime;

public record RequestObject(LocalDateTime dateTime, String payload) {
}
