package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Bucket;

import java.util.UUID;

public interface BucketRepository extends JpaRepository<Bucket, UUID> {
}
