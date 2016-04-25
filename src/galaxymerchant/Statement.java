/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxymerchant;

import java.text.DecimalFormat;

/**
 *
 * @author monil
 */
abstract class Statement {
    private final String sentence;
    private String response;
    
    public Statement(String sentence){
        this.sentence = sentence;
    }
    
    
    public String getSentence(){
        return sentence;
    }
    
    public String getResponse(){
        return response;
    }
    public void setResponse(String response){
        this.response = response;
    }
   
    public abstract void process(DataMap dataMap) throws RuntimeException;
}





class DataStatement extends Statement {
    public DataStatement(String sentence) {
        super(sentence);
    }
    @Override
    public void process(DataMap dataMap) {
        String[] data = super.getSentence().split(" is ");
        dataMap.add(data[0], data[1]);
    }
}


class EquationDataStatement extends DataStatement{
    public EquationDataStatement(String sentence) {
        super(sentence);
    }
    @Override
    public void process(DataMap dataMap) {
        String data[] = super.getSentence().split(" is ");
        data[1] = data[1].replaceAll("[cC]redits", "").trim();
        String unknownVariable = null;
        StringBuilder knownVariable = new StringBuilder();
        for(String word : data[0].split(" ")){
            Object value = dataMap.search(word, false);
            if(value != null){
                knownVariable.append(value.toString());
            }else{
                if(unknownVariable != null){
                    throw new RuntimeException("Multiple Unknown Variables not handled");
                }
                unknownVariable = word;
            }
        }
        
        double unknownValue = Double.parseDouble(data[1]);
        int knownValue = RomanNumber.toDecimal(knownVariable.toString());
        dataMap.add(unknownVariable, (unknownValue/knownValue));
    }
}





class QuestionStatement extends Statement{
    public QuestionStatement(String sentence) {
        super(sentence);
    }
    @Override
    public void process(DataMap dataMap) {
        double number = -1;
        boolean sequenceStarted = false;
        StringBuilder symbol = new StringBuilder();
        StringBuilder response = new StringBuilder();
        for(String word : super.getSentence().split(" ")){
            Object value = dataMap.search(word, false);
            if(sequenceStarted && value == null){
                break;
            }
            if(value != null){
                sequenceStarted = true;
                response.append(word).append(" ");
                if(Symbol.substitude(value.toString()) != null){
                    symbol.append(value);
                }else{
                    number = (Double)value;
                }
            }
        }
        if(!sequenceStarted){
            this.setResponse("I have no idea what you are talking about");
            return;
        }
        
        int decimalNumber = RomanNumber.toDecimal(symbol.toString());
        response.append("is ");
        if(number < 0){
            response.append(decimalNumber);
        }else{
            String credit = (new DecimalFormat("#.#")).format(number*decimalNumber);
            response.append(credit).append(" Credits");
        }
        this.setResponse(response.toString());
    }
}


class CompareQuestionStatement extends QuestionStatement {

    public CompareQuestionStatement(String sentence) {
        super(sentence);
    }
    
    @Override
    public void process(DataMap dataMap) {
        String data[] = this.getSentence().split(" is ");
        double values[] = new double[data.length];
        for(int i=0; i<data.length; i++){
            double number = -1;
            boolean sequenceStarted = false;
            StringBuilder symbol = new StringBuilder();
            StringBuilder response = new StringBuilder();
            for(String word : data[i].split(" ")){
                Object value = dataMap.search(word, false);
                if(sequenceStarted && value == null){
                    break;
                }
                if(value != null){
                    sequenceStarted = true;
                    response.append(word).append(" ");
                    if(Symbol.substitude(value.toString()) != null){
                        symbol.append(value);
                    }else{
                        number = (Double)value;
                    }
                }
            }
            if(!sequenceStarted){
                this.setResponse("I have no idea what you are talking about");
                return;
            }
            int decimalNumber = RomanNumber.toDecimal(symbol.toString());
            values[i] = (number<0)? decimalNumber : ((decimalNumber>0)? number*decimalNumber : number);
            data[i] = response.toString();
        }
        
        if(values.length > 2){
            throw new RuntimeException("More than 2 variables to compare");
        }
        
        int minIndex = (values[0]>values[1])? 1:0;
        int maxIndex = (minIndex+1)%2;
        String compare = (new DecimalFormat("#.#")).format(values[maxIndex]/values[minIndex]);
        this.setResponse(compare + " times " + data[minIndex] + "is " + data[maxIndex]);
    }
}