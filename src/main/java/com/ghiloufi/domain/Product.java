package com.ghiloufi.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(String name, BigDecimal price, int stockQuantity) {

  public Product {
    Objects.requireNonNull(name, "Product name cannot be null");
    Objects.requireNonNull(price, "Product price cannot be null");
    if (stockQuantity < 0) {
      throw new IllegalArgumentException("Stock quantity cannot be negative");
    }
  }

  public Product withName(String name) {
    return new Product(name, this.price, this.stockQuantity);
  }

  public Product withPrice(BigDecimal price) {
    return new Product(this.name, price, this.stockQuantity);
  }

  public Product withStockQuantity(int stockQuantity) {
    return new Product(this.name, this.price, stockQuantity);
  }
}
