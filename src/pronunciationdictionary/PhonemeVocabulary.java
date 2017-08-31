/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pronunciationdictionary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tdinh
 */
public class PhonemeVocabulary {
    
    private final List<Phoneme> phonemesList;
    
    public PhonemeVocabulary(){
        phonemesList = new ArrayList<>();
    }
    
    public void addPhoneme(Phoneme phoneme){        
        phonemesList.add(phoneme);
    }
    
    public int indexOf(String phoneme){
        for(int i = 0; i < phonemesList.size(); i++){
            if(phonemesList.get(i).getForm().equals(phoneme)){
                return i;
            }
        }
        return -1;
    }
    
    public Phoneme get(int i){
        return phonemesList.get(i);
    }
}
