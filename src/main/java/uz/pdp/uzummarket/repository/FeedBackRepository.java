package uz.pdp.uzummarket.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import uz.pdp.uzummarket.entity.Feedback;

import java.util.List;
import java.util.UUID;

public interface FeedBackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findAllByProductId(UUID product_id);
}
