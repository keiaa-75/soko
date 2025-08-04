package com.keiaa.soko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

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
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT DISTINCT p.itemCategory FROM Product p ORDER BY p.itemCategory")
    List<String> findDistinctItemCategories();
}
