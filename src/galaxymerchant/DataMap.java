/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxymerchant;

import java.util.ArrayList;
import java.util.List;

/**
 * DataMap implements Data Structure which is used as the knowledge of Parser.
 * Parser can store and search for data word by word.
 * 
 * @author Monil Gudhka
 */
public class DataMap {
    
    private final Node root;
    public DataMap(){
        root = new Node(' ');
    }
    
    /**
     * Stores the word along with data represented by that word
     * @param key
     * @param value
     */
    public void add(String key, Object value){
        Node currentNode = root;
        for(char ch: key.toCharArray()){
            boolean matched = false;
            for(Node nextNode: currentNode.getNextNodeList()){
                if(nextNode.matches(ch, true)){
                    currentNode = nextNode;
                    matched = true;
                    break;
                }
            }
            if(!matched){
                Node newNode = new Node(ch);
                currentNode.addNode(newNode);
                currentNode = newNode;
            }
        }
        currentNode.setValue(value);
    }
    
    /**
     * Search for the word and returns data represented by that word
     * @param sentence
     * @param caseSensitive
     * @return
     */
    public Object search(String sentence, boolean caseSensitive){
        return search(sentence, root, caseSensitive);
    }
    private Object search(String sentence, Node currentNode, boolean caseSensitive){
        if(sentence.isEmpty()){
            return currentNode.getValue();
        }
        
        char key = sentence.charAt(0);
        String subSentence = sentence.substring(1);
        for(Node nextNode : currentNode.getNextNodeList()){
            if(nextNode.matches(key, caseSensitive)){
                Object value = search(subSentence, nextNode, caseSensitive);
                if(value != null){
                    return value;
                }
            }
        }
        return null;
    }

    
    
    
    
    
    @Override
    public String toString() {
        return root.getStringForDisplay("\t");
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private static class Node{
        private final char key;
        private final List<Node> nextNodeList;
        private Object value;
        
        Node(char key){
            this(key, null);
        }
        Node(char key, Object value){
            this.key = key;
            this.value = value;
            this.nextNodeList = new ArrayList<>();
        }
        
        void setValue(Object value){
            this.value = value;
        }
        Object getValue(){
            return this.value;
        }
        boolean matches(char key, boolean caseSensitive){
            boolean matched = (this.key==key);
            if(!matched && !caseSensitive){
                return Character.toUpperCase(this.key)==key;
            }
            return matched;
        }
        List<Node> getNextNodeList(){
            return this.nextNodeList;
        }
        void addNode(Node node){
            this.nextNodeList.add(node);
        }

        String getStringForDisplay(String intent){
            StringBuilder display = new StringBuilder();
            display.append(intent).append(this.key).append("=").append(this.value);
            intent = intent+"\t";
            for(Node nextNode: this.getNextNodeList()){
                display.append("\n").append(nextNode.getStringForDisplay(intent));
            }
            return display.toString();
        }
    }
}
