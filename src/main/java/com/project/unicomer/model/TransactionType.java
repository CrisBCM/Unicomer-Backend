package com.project.unicomer.model;

public enum TransactionType {
    DEPOSITO("Depósito"),
    TRANSACCION("Transacción"),
    EXTRACCION("Extracción");

    private String name;

    TransactionType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
