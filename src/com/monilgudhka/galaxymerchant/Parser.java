/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monilgudhka.galaxymerchant;

/**
 *
 * @author Monil Gudhka
 */
public class Parser {
    
    public Statement parse(String sentence){
        if(sentence.matches("^[A-Za-z]+ is [IVXLCDMivxlcdm]{1}$")){
           return new DataStatement(sentence);
        }else if(sentence.matches("[A-Za-z ]+ is [0-9]+ [cC]redits$")){
           return new EquationDataStatement(sentence);
        }else if(sentence.matches("how (much|many Credits) .* \\?")){
            return new QuestionStatement(sentence);
        }else if(sentence.matches("how many .+ is .+ \\?")){
            return new CompareQuestionStatement(sentence);
        }else{
            throw new RuntimeException("New Type of Sentence found");
        }
    }
}
