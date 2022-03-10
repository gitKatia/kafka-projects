package com.kat.ordersmodel;

import lombok.Getter;

@Getter
public enum InventoryOperation {
    ADD("ADD"), REMOVE("REMOVE");

    private String operation;

    InventoryOperation(String operation) {
        this.operation = operation;
    }
}
