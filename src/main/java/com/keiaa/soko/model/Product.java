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

    private String itemId;
    private String itemName;
    private int itemQty;
    private double itemPrice;
    private String itemCategory;

    public Product() {}

    private static class InvalidItemException extends RuntimeException {
        public InvalidItemException(String message) {
            super(message);
        }
    }

    public Product(
        String itemId,
        String itemName,
        int itemQty,
        double itemPrice,
        String itemCategory
        ) {
        
        if (itemQty < 0) {
            throw new InvalidItemException("The item quantity should not be negative.");
        }

        if (itemPrice < 0) {
            throw new InvalidItemException("The item price should not be negative.");
        }
        
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
    }

    public Long getId() { return id; }
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public int getItemQty() { return itemQty; }
    public double getItemPrice() { return itemPrice; }
    public String getItemCategory() { return itemCategory; }

    public void setId(Long newId) { this.id = newId; }
    public void setItemId(String newItemId) { this.itemId = newItemId; }
    public void setName(String newName) { this.itemName = newName; }
    public void setQty(int newQty) { this.itemQty = newQty; }
    public void setPrice(double newPrice) { this.itemPrice = newPrice; }
    public void setCategory(String newCategory) { this.itemCategory = newCategory; }
}
