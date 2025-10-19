package com.ghiloufi.domain;

import java.math.BigDecimal;
import java.util.Objects;

public record CartItem(Product product, int quantity) {

  public CartItem {
    Objects.requireNonNull(product, "Product cannot be null");
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
  }

  public BigDecimal subtotal() {
    return product.price().multiply(BigDecimal.valueOf(quantity));
  }

  public CartItem withQuantity(int quantity) {
    return new CartItem(this.product, quantity);
  }
}
