package uz.pdp.uzummarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.uzummarket.entity.Category;
import uz.pdp.uzummarket.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByUserIdAndCategoryId(UUID sellerId, UUID categoryId,PageRequest pageRequest);
    List<Product> getAllByUserIdAndCategory_id(UUID sellerId, UUID categoryId);
    void deleteById(UUID productId);
//    @Query(nativeQuery = true ,value = """
//                SELECT p FROM product p
//                                  INNER JOIN category c ON c.id = p.category_id
//                WHERE c.name LIKE '%' || :keyword || '%'
//                   OR p.name LIKE '%' || :keyword || '%'
//                   """
//            )
    @Query(value = """
                SELECT p FROM product p where p.category.name ilike '%' || :keyword || '%' or p.name ilike '%' || :keyword || '%'
                """
            )
    List<Product> searchProductByNameOrCategoryName(@Param("keyword") String keyword);
    Optional<Object> findByName(String name);

List<Product> searchAllByCategoryOrNameContainingIgnoreCase(Category category, String name);


    List<Product> getProductsByCategory_Id(UUID categoryId);
}
