package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        var result = timeEntryRepository.create(timeEntryToCreate);

        return ResponseEntity.created(URI.create("/time-entries/" + result.getId())).body(result);
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {
        var result = timeEntryRepository.find(timeEntryId);

        if (result == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        var result = timeEntryRepository.list();

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> update(@PathVariable long timeEntryId, @RequestBody TimeEntry timeEntryToUpdate) {
        var result = timeEntryRepository.update(timeEntryId, timeEntryToUpdate);

        if (result == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity<Void> delete(@PathVariable long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);

        return ResponseEntity.noContent().build();
    }
}
