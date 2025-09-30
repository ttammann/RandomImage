package org.randomimage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageEntryRepository repository;

    private final List<String> urlList = List.of(

            "https://random-d.uk/api/478.jpg",
            "https://random-d.uk/api/436.jpg",
            "https://random-d.uk/api/88.jpg",
            "https://random-d.uk/api/179.jpg",
            "https://random-d.uk/api/51.jpg",
            "https://random-d.uk/api/463.jpg",
            "https://random-d.uk/api/324.jpg",
            "https://random-d.uk/api/551.jpg",
            "https://random-d.uk/api/362.jpg",
            "https://random-d.uk/api/348.jpg",
            "https://random-d.uk/api/519.jpg",
            "https://random-d.uk/api/129.jpg",
            "https://random-d.uk/api/133.jpg",
            "https://random-d.uk/api/28.jpg",
            "https://random-d.uk/api/72.jpg"


    );

    @Transactional
    public ImageEntry fetchAndSaveRandomImage() throws Exception {
        String chosenUrl = urlList.get(new Random().nextInt(urlList.size()));
        try (InputStream in = new URL(chosenUrl).openStream()) {
            byte[] imageData = in.readAllBytes();
            ImageEntry entry = ImageEntry.builder()
                    .data(imageData)
                    .savedAt(LocalDateTime.now())
                    .sourceUrl(chosenUrl)
                    .build();
            return repository.save(entry);
        }
    }

    public ImageEntry getLatestImage() {
        return repository.findLatest();
    }
}

