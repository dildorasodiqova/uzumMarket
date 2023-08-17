package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> getCategoryById(UUID id);
    Optional<Category> getByName(String name);
    @Query(nativeQuery = true,value = "select * from category c where c.parent_id is null")
    List<Category> getCategoriesByParent_Id();

    List<Category> getCategoriesByParent_Id(UUID parentId);
}
