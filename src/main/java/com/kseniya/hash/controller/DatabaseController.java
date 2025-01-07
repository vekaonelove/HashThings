package com.kseniya.hash.controller;

import com.kseniya.hash.model.FileRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class DatabaseController {

    private final FileRecordRepository fileRecordRepository;

    @GetMapping
    public String getAllFileRecords(Model model) {
        model.addAttribute("fileRecords", fileRecordRepository.findAll());
        return "database.html";
    }
}
