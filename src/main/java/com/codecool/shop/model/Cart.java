package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    public List<Product> shoppingCart = new ArrayList<>();
    private static Cart instance = null;

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void add(Product product) {
       if (shoppingCart.contains(product)) product.increaseQuantity();
       else shoppingCart.add(product);
    }

    public void remove(Product product) {
        if (product.getQuantity() > 1) product.decreaseQuantity();
        else shoppingCart.remove(product);
    }
}
