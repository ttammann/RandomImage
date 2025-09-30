package org.randomimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageEntryRepository extends JpaRepository<ImageEntry, Long> {
    @Query("SELECT e FROM ImageEntry e ORDER BY e.savedAt DESC LIMIT 1")
    ImageEntry findLatest();
}
