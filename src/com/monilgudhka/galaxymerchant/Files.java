/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monilgudhka.galaxymerchant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Files reads and writes sentences into the File.
 * @author Monil Gudhka
 */
public class Files {
    
    public static List<String> read(String fileName){
        BufferedReader reader = null;
        List<String> content = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String sentence;
            while((sentence = reader.readLine()) != null){
                content.add(sentence);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException ex) {}
            }
        }
        return content;
    }
    
    public static void write(String fileName, List<String> content){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for(String sentence : content){
                writer.write(sentence);
                writer.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Files.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            if(writer != null){
                try {
                    writer.flush();
                } catch (IOException ex) {}
                try {
                    writer.close();
                } catch (IOException ex) {}
            }  
        }
    }
}