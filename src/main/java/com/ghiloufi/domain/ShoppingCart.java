package com.ghiloufi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record ShoppingCart(List<CartItem> items) {

  public ShoppingCart {
    Objects.requireNonNull(items, "Items list cannot be null");
    items = Collections.unmodifiableList(new ArrayList<>(items));
  }

  public ShoppingCart() {
    this(new ArrayList<>());
  }

  public ShoppingCart addProduct(Product product, int quantity) {
    Objects.requireNonNull(product, "Product cannot be null");
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }

    List<CartItem> newItems = new ArrayList<>(items);

    boolean found = false;
    for (int i = 0; i < newItems.size(); i++) {
      CartItem item = newItems.get(i);
      if (item.product().equals(product)) {
        newItems.set(i, item.withQuantity(item.quantity() + quantity));
        found = true;
        break;
      }
    }

    if (!found) {
      newItems.add(new CartItem(product, quantity));
    }

    return new ShoppingCart(newItems);
  }

  public ShoppingCart removeProduct(Product product) {
    Objects.requireNonNull(product, "Product cannot be null");

    List<CartItem> newItems = new ArrayList<>();
    for (CartItem item : items) {
      if (!item.product().equals(product)) {
        newItems.add(item);
      }
    }

    return new ShoppingCart(newItems);
  }

  public ShoppingCart updateQuantity(Product product, int newQuantity) {
    Objects.requireNonNull(product, "Product cannot be null");
    if (newQuantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }

    List<CartItem> newItems = new ArrayList<>();
    boolean found = false;

    for (CartItem item : items) {
      if (item.product().equals(product)) {
        newItems.add(item.withQuantity(newQuantity));
        found = true;
      } else {
        newItems.add(item);
      }
    }

    if (!found) {
      throw new IllegalArgumentException("Product not found in cart");
    }

    return new ShoppingCart(newItems);
  }

  public BigDecimal total() {
    return items.stream().map(CartItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public int itemCount() {
    return items.size();
  }
}
