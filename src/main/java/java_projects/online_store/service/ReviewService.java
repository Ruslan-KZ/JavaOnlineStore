package java_projects.online_store.service;

import java_projects.online_store.entity.Review;
import java_projects.online_store.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public Review getById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Отзыв не найден: " + id));
    }

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}