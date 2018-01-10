package com.example.priyanka.shoppingapplication.models;

public class OrderedItem {
    private boolean isOrdered;
    private String productId;

    public OrderedItem() {
    }

    public OrderedItem(boolean isOrdered, String productId) {
        this.isOrdered = isOrdered;
        this.productId = productId;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
