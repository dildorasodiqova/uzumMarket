package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.Attachment;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
Optional<Attachment> findByName(String fileName);

}
