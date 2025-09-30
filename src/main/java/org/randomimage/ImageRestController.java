package org.randomimage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ImageRestController {
    private final ImageService service;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/fetch-random")
    public ResponseEntity<?> fetchRandom() {
        try {
            ImageEntry entry = service.fetchAndSaveRandomImage();
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("id", entry.getId());
            metadata.put("savedAt", entry.getSavedAt().format(FMT));
            metadata.put("sourceUrl", entry.getSourceUrl());
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch image.");
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        ImageEntry entry = service.getLatestImage();
        if (entry == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("id", entry.getId());
        response.put("savedAt", entry.getSavedAt().format(FMT));
        response.put("sourceUrl", entry.getSourceUrl());
        response.put("image", "/image/" + entry.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageBinary(@PathVariable Long id) {
        ImageEntry entry = service.getLatestImage();
        if (entry == null || !entry.getId().equals(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(entry.getData());
    }
}

