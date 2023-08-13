package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.uzummarket.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
