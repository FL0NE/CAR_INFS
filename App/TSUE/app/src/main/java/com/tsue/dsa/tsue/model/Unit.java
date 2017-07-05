package com.tsue.dsa.tsue.model;

/**
 * Created by abr on 08.06.2017.
 */

public enum Unit {
    SPEED("km/h");

    private String symbol;
    private Unit(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol(){
        return symbol;
    }
}