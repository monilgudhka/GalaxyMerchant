/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxymerchant;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Monil Gudhka
 */
public class GalaxyMerchant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> inputContent = Files.read("GalaxyMerchantInput.txt");
        List<String> outputContent = new ArrayList<>();
        DataMap knowledge = new DataMap();
        Parser parser = new Parser();
        
        for(String sentence : inputContent){
            Statement statement = parser.parse(sentence);
            statement.process(knowledge);
            if(statement instanceof QuestionStatement)
                outputContent.add(statement.getResponse());
        }
        
        Files.write("GalaxyMerchantOutput.txt", outputContent);
    }
    
}
