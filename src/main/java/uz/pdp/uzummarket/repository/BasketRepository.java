package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Bucket;

import java.util.UUID;

@Repository
public interface BucketRepository extends JpaRepository<Bucket, UUID> {
}
