package com.hallak.HackathonUfjf2025.services;


import com.hallak.HackathonUfjf2025.dto.RequestObject;
import com.hallak.HackathonUfjf2025.dto.ResponseObject;

public interface MainService {
    ResponseObject newQuery(RequestObject requestObject);
}
