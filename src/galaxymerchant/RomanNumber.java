/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxymerchant;

/**
 * RomanNumber represents the Roman Number.
 * It calculates the Decimal value of provided Roman number as well as the Roman value of provided decimal number
 * 
 * @author Monil Gudhka
 */
public class RomanNumber {
    
    public static int toDecimal(String romanNumber){
        int decimalNumber = 0;
        char[] symbolList = romanNumber.toCharArray();
        Symbol previous = null;
        for(int position=symbolList.length-1; position>=0; position--){
            Symbol current = Symbol.valueOf(symbolList[position]+"");
            int tempNumber = current.getDecimal();
            if(previous != null && previous.getDecimal()>tempNumber){
                tempNumber = (previous.getDecimal() - tempNumber)/tempNumber;
                tempNumber = (tempNumber==9 || tempNumber==4)? (-1)*current.getDecimal() : current.getDecimal();
            }
            decimalNumber += tempNumber;
            previous = current;
        }
        return decimalNumber;
    }
    
    
    public static String toRoman(int decimalNumber){
        String romanNumber = "";
        for(int temp=decimalNumber,position=0; temp>0; position=position+1,temp=temp/10){
            romanNumber = calcRoman((temp%10), position) + romanNumber;
        }
        return romanNumber;
    }

    private static String calcRoman(int digit, int position) {
        Symbol[] symbolList = Symbol.values();
        StringBuilder tempRoman = new StringBuilder();
        boolean mod9 = (digit==9);
        boolean substract = (mod9) || (digit==4);
        
        digit = (substract)?digit+1 : digit;
        digit = (int)(digit*Math.pow(10, position));
        position = (mod9)?(position*2)+2 : (position*2)+1;
        position = Math.min(position, symbolList.length-1);
        
        while(digit>0){
            digit = digit - symbolList[position].getDecimal();
            if(digit<0){
                digit += symbolList[position].getDecimal();
                position--;
            }else{
                tempRoman.append(symbolList[position]);
            }
        }
        if(substract){
            position = (mod9)? position-2 : position-1;
            tempRoman.insert(0, symbolList[position]);
        }
        return tempRoman.toString();
    }
}
