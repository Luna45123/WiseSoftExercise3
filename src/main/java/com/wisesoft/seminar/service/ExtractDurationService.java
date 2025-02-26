package com.wisesoft.seminar.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ExtractDurationService {

    public int extractDuration(String title) {
        Pattern DURATION_PATTERN = Pattern.compile("\\s(\\d+)min$");
        Matcher matcher = DURATION_PATTERN.matcher(title);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Invalid format: " + title); // Handle cases where duration is missing
    }
}
