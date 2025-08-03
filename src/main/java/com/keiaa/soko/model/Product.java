package com.keiaa.soko.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a product entity in the inventory system.
 * <p>
 * This class is a JPA-managed entity and is mapped to a corresponding table
 * in the database (typically named {@code product} by default).
 * </p>
 *
 * <p>
 * The {@code id} field is marked as the primary key and is automatically generated
 * by the database.
 * This means the database will assign an auto-incremented value when a new product
 * is inserted.
 * </p>
 *
 * <p>
 * This entity includes basic information about a product: its name, available quantity,
 * and unit price. It includes constructors, getters, and setters for use within
 * Spring Data JPA repositories and service layers.
 * </p>
 *
 * @see jakarta.persistence.Entity
 * @see jakarta.persistence.Id
 * @see jakarta.persistence.GeneratedValue
 * @see org.springframework.data.jpa.repository.JpaRepository
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private double price;

    public Product() {}

    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
}
