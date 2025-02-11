package com.wiseSoft.seminar.seminar.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wiseSoft.seminar.seminar.model.ScheduleModel;
import com.wiseSoft.seminar.seminar.model.SeminarDay;
import com.wiseSoft.seminar.seminar.model.SeminarTopic;
import com.wiseSoft.seminar.seminar.service.ParseSeminarTopicsService;
import com.wiseSoft.seminar.seminar.service.PrintScheduleService;
import com.wiseSoft.seminar.seminar.service.SeminarSchedulerService;

@RestController
public class SeminarController {

    private final SeminarSchedulerService seminarScheduler;

    private final PrintScheduleService printSchedule;

    private final ParseSeminarTopicsService parseSeminarTopics;

    public SeminarController(SeminarSchedulerService seminarScheduler, PrintScheduleService printSchedule,
            ParseSeminarTopicsService parseSeminarTopics) {
        this.seminarScheduler = seminarScheduler;
        this.printSchedule = printSchedule;
        this.parseSeminarTopics = parseSeminarTopics;
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello World";
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadTextFile(@RequestPart("file") MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.toLowerCase().endsWith(".txt")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Only .txt files are allowed.");
            }
            List<String> input = reader.lines().collect(Collectors.toList());
            if (input.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input file is empty or invalid.");
            }
            String startDate = input.getFirst();
            List<SeminarTopic> topics = parseSeminarTopics.parseSeminarTopics(input);

            List<SeminarDay> schedule = seminarScheduler.createScheduleSeminars(startDate, topics);
            List<ScheduleModel> scheduleModels = printSchedule.print(schedule);
            return ResponseEntity.ok(scheduleModels);
        }
    }
}
