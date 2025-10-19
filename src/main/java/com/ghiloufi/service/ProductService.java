package com.ghiloufi.service;

import com.ghiloufi.domain.Product;
import com.ghiloufi.repository.InMemoryProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductService {

  private final InMemoryProductRepository repository;

  public ProductService(InMemoryProductRepository repository) {
    this.repository = repository;
  }

  public Long createProduct(String name, BigDecimal price, int stockQuantity) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Product name cannot be null or blank");
    }
    if (price == null) {
      throw new IllegalArgumentException("Product price cannot be null");
    }
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Product price cannot be negative");
    }
    if (stockQuantity < 0) {
      throw new IllegalArgumentException("Stock quantity cannot be negative");
    }

    Product product = new Product(name, price, stockQuantity);
    return repository.save(product);
  }

  public Optional<Product> getProductById(Long id) {
    return repository.findById(id);
  }

  public List<Product> getAllProducts() {
    return repository.findAll();
  }

  public boolean updateProduct(Long id, Product product) {
    if (product == null) {
      throw new IllegalArgumentException("Product cannot be null");
    }
    return repository.update(id, product);
  }

  public boolean increaseStock(Long id, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity to add must be positive");
    }
    Optional<Product> productOpt = repository.findById(id);
    if (productOpt.isEmpty()) {
      return false;
    }
    Product product = productOpt.get();
    Product updatedProduct = product.withStockQuantity(product.stockQuantity() + quantity);
    return repository.update(id, updatedProduct);
  }

  public boolean decreaseStock(Long id, int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity to remove must be positive");
    }
    Optional<Product> productOpt = repository.findById(id);
    if (productOpt.isEmpty()) {
      return false;
    }
    Product product = productOpt.get();
    int newQuantity = product.stockQuantity() - quantity;
    if (newQuantity < 0) {
      throw new IllegalArgumentException("Cannot decrease stock below zero");
    }
    Product updatedProduct = product.withStockQuantity(newQuantity);
    return repository.update(id, updatedProduct);
  }

  public boolean deleteProduct(Long id) {
    return repository.deleteById(id);
  }
}
