package com.wiseSoft.seminar.seminar.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class InputFileService {
    public List<String> readInputFile(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return lines.collect(Collectors.toList());
        }
    }
}
