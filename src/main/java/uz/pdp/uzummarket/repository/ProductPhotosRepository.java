package uz.pdp.uzummarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.ProductPhotos;

import java.util.List;
import java.util.UUID;
@Repository
public interface ProductPhotosRepository  extends JpaRepository<ProductPhotos, UUID> {
    List<ProductPhotos> getByProduct_Id (UUID productId);
}
