package com.koral.vister.model;

public class User {
    private PreOrder preOrder;

    public User(PreOrder preOrder) {
        this.preOrder = preOrder;
    }

    public PreOrder getPreOrder() {
        return preOrder;
    }

    public void setPreOrder(PreOrder preOrder) {
        this.preOrder = preOrder;
    }
}
