/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monilgudhka.galaxymerchant;

/**
 * Symbol defines Roman Numeral and its decimal value
 * 
 * @author Monil Gudhka
 */
public enum Symbol {
    I(1),
    V(5),
    X(10),
    L(50),
    C(100),
    D(500),
    M(1000);
    
    
    private final int decimal;
    Symbol(int decimal){
        this.decimal = decimal;
    }
    public int getDecimal(){
        return this.decimal;
    }
    
    /**
     * It takes the Roman Numeral and return Symbol representation of the same
     * @param symbol
     * @return
     */
    public static Symbol substitude(String symbol){
        try{
            return Symbol.valueOf(symbol.toUpperCase());
        }catch(NullPointerException | IllegalArgumentException ex){
            return null;
        }
    }
}
