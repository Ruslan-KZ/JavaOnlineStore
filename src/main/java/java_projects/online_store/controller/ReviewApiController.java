package java_projects.online_store.controller;

import java_projects.online_store.entity.Review;
import java_projects.online_store.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody Review review) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}