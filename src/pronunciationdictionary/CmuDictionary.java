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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tdinh
 */
public class CmuDictionary {
    
    // A Term to a List of Phonemes.
    protected Map<String, List<List<String>>> termsMap;
    // A String representation of a Phoneme to a Set of Terms.
    protected Map<String, Set<String>> phonemesIndex;
    
    private static CmuDictionary pronunDict;
    private CmuDictionary() throws FileNotFoundException, IOException{
        File cmudict = new File(this.getClass()
                .getResource("./cmudict.txt").getFile());
        File symbols = new File(this.getClass()
                .getResource("./cmudict_symbols.txt").getFile());
        BufferedReader fileScanner;
        // TreeMap for ordered keys.
        termsMap = new TreeMap<>();
        // HashMap for nonordered keys.
        phonemesIndex = new TreeMap<>();
        String line;
        //Read in phoneme List.
        fileScanner = new BufferedReader(new FileReader(symbols));
        while((line = fileScanner.readLine()) != null) {
            String arpabetPhoneme = line.toLowerCase().trim();
            phonemesIndex.put(arpabetPhoneme
                    , new TreeSet<>());
        }
        // Populate termsMap and phonemesIndex.
        fileScanner = new BufferedReader(new FileReader(cmudict));
        String previousTerm = "";
        while((line = fileScanner.readLine()) != null) {
            line = line.toLowerCase();
            //^It is not at the end of the stream.
            if(!Character.isAlphabetic(line.codePointAt(0))){
                continue;
            }
            //^First character of the line is an alphabetic.
            Scanner textScanner = new Scanner(line);
            String term = "";
            // Scan the term from this line.
            if(textScanner.hasNext()){
                term = textScanner.next();
            }
            // Check if the term is variation of the previous term.
            if(term.charAt(term.length()-1) == ')'
                    && term.contains(previousTerm)){
                // Don't update the term.
                term = previousTerm;
            }
            else{
                // The term is not a variation of the previous term.
                // Save this Term as previousTerm.
                previousTerm = term;
                termsMap.put(term, new ArrayList<>());
            }
            //^Word is not null.
            // Put phonemese into the list of this pronunciation.            
            ArrayList<String> phonemesList = new ArrayList<>();
            while(textScanner.hasNext()){
                String stressArpabetPhoneme = textScanner.next();
                //Add the phoneme into the list.
                phonemesList.add(stressArpabetPhoneme);                
                // Indexing.
                Set wordSet = phonemesIndex.get(stressArpabetPhoneme);
                wordSet.add(term);
            }
            // Add this list of phonemes into the list of pronunication for
            // this word.
            termsMap.get(term).add(phonemesList);
        }
    }

    public List<List<String>> getArphabetPronunciation(String term) {
        List<List<String>> phonemeParts = termsMap.get(term);
        return phonemeParts;
    }

    public Set<String> getTerms(String arphabet) {
        Set<String> terms = phonemesIndex.get(arphabet);
        return terms;
    }
    
    public static CmuDictionary getPronunciationDictionary(){
        if(pronunDict == null){
            try{
                pronunDict = new CmuDictionary();
            } catch (IOException ex) {
                Logger.getLogger(CmuDictionary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pronunDict;
    }      
}
