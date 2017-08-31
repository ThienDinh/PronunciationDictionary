/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pronunciationdictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tdinh
 */
public class PhonemeDictionary {
    
    private Map<String, PhonemeVocabulary> phonemeVocabMap;
    
    private static PhonemeDictionary dictionary;
    
    private PhonemeDictionary() throws FileNotFoundException, IOException{
        File phones = new File(this.getClass()
                .getResource("./cmudict_phones.txt").getFile());
        phonemeVocabMap = new TreeMap<>();
        phonemeVocabMap.put("IPA", new PhonemeVocabulary());
        phonemeVocabMap.put("Arpabet", new PhonemeVocabulary());
        
        BufferedReader fileScanner;
        String line;
        //Read in phones List.
        fileScanner = new BufferedReader(new FileReader(phones));
        //Skip the first line, header line.
        boolean firstLine = true;
        while((line = fileScanner.readLine()) != null) {
            if(firstLine) {
                firstLine = false;
                continue;
            }
            line = line.toLowerCase();
            String[] parts = line.split(",");
            phonemeVocabMap.get("Arpabet").addPhoneme(new Phoneme(parts[0], parts[2]));
            phonemeVocabMap.get("IPA").addPhoneme(new Phoneme(parts[1], parts[2]));
        }
    }
    
    /**
     * Look up the give phoneme in vocabulary list 1 and return its equivalent in
     * vocabulary list 2.
     * @param phoneme
     * @param vocab1
     * @param vocab2
     * @return 
     */
    public Phoneme convertPhoneme(String phoneme, String vocab1, String vocab2){
        int index = phonemeVocabMap.get(vocab1).indexOf(phoneme);
        if(index == -1){
            return null;
        }
        return phonemeVocabMap.get(vocab2).get(index);
    }
    
    public PhonemeVocabulary getPhonemeVocabulary(String type){
        return phonemeVocabMap.get(type);
    }
    
    public static PhonemeDictionary getPhonemeDictionary(){    
        if(dictionary == null){
            try {
                dictionary = new PhonemeDictionary();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PhonemeDictionary.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PhonemeDictionary.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        return dictionary;
    }
    
}
