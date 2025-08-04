package com.keiaa.soko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.keiaa.soko.model.Product;

/**
 * Repository interface for managing {@link Product} entities.
 * <p>
 * This interface extends {@link JpaRepository}, which provides a complete set
 * of CRUD operations and database access methods for the {@code Product} entity.
 * </p>
 *
 * <p>
 * The generic parameters specify:
 * <ul>
 *     <li>{@code Product} – the type of entity to manage</li>
 *     <li>{@code Long} – the type of the entity's primary key</li>
 * </ul>
 * </p>
 *
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see com.keiaa.soko.model.Product
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByItemNameContainingIgnoreCase(String itemName);

    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.itemCategory = :category) AND (:minPrice IS NULL OR p.itemPrice >= :minPrice) AND (:maxPrice IS NULL OR p.itemPrice <= :maxPrice)")
    List<Product> filterProducts(@Param("category") String category, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    List<Product> findAllByOrderByItemPriceAsc();
    List<Product> findAllByOrderByItemPriceDesc();

    List<Product> findAllByOrderByItemIdAsc();
    List<Product> findAllByOrderByItemIdDesc();
    List<Product> findAllByOrderByItemNameAsc();
    List<Product> findAllByOrderByItemNameDesc();

    @Query("SELECT DISTINCT p.itemCategory FROM Product p ORDER BY p.itemCategory")
    List<String> findDistinctItemCategories();
}
