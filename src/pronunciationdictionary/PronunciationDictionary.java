/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pronunciationdictionary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tdinh
 */
public abstract class PronunciationDictionary {

    protected CmuDictionary cmuDict;
    protected PhonemeDictionary phonemeDict;
    private static PronunciationDictionary pronunDict;

    protected PronunciationDictionary() {
        cmuDict = CmuDictionary.getPronunciationDictionary();
        phonemeDict = PhonemeDictionary.getPhonemeDictionary();
    }

    public static PronunciationDictionary getPronunciationDictionary(String phonemeType) {
        try {
            switch (phonemeType) {
                case "IPA":
                    pronunDict = new IpaPronunciationDictionary();
                    break;
                case "Arpabet":
                    pronunDict = new ArpabetPronunciationDictionary();
                    break;
                default:
                    pronunDict = null;
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(CmuDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pronunDict;
    }

    public List<List<String>> getPronunciation(String term){
        List<List<String>> pronunciationsList = cmuDict
                .getArphabetPronunciation(term);
        List<List<String>> pronunList = new ArrayList<>();
        for(List<String> pList: pronunciationsList){
            List<String> stressPhonemeList = new ArrayList<>();
            for(String arpabet: pList){                
                String phoneme = parsePhoneme(arpabet);
                stressPhonemeList.add(phoneme);
            }
            pronunList.add(stressPhonemeList);
        }
        return pronunList;
    }
    
    protected abstract String parsePhoneme(String phoneme);
    protected abstract Set<String> getTerms(String phoneme);
    
    public static List<String> stringifyPronunciation(List<List<String>> pronunciation){
        List<String> combinePronunciation = new ArrayList<>();
        for(List<String> diffPronunciation: pronunciation){            
            StringBuilder strBuilder = new StringBuilder();
            for(String s: diffPronunciation){
                strBuilder.append(s);
                strBuilder.append(" ");
            }
            strBuilder.deleteCharAt(strBuilder.length()-1);
            combinePronunciation.add(strBuilder.toString());
        }
        return combinePronunciation;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String[] words = {
            "electrochemical","consider", "minute", "accord",
            "evident", "practice", "intend",
            "concern", "commit", "issue", 
            "approach", "establish", "utter",
            "conduct", "engage", "obtain",
            "scarce", "policy", "straight",
            "stock", "apparent", "property", 
            "fancy", "concept", "court",
            "appoint", "passage", "vain", 
            "instance", "coast", "project", 
            "commission", "constant", "circumstances", 
            "constitute", "level", "affect", 
            "institute", "render", "appeal", 
            "generate", "theory", "range", 
            "campaign", "league", "labor", 
            "confer", "grant", "dwell", "entertain"
        };
        printPronunciation(words);
//        findWords("z");
//        String opOption = args[0];
//        String dictOption = args[1];
//        String input = args[2];
//        PronunciationDictionary pronunDict = PronunciationDictionary.getPronunciationDictionary(dictOption);
//        switch(opOption){
//            case "pronounce":
//                System.out.println(pronunDict.getPronunciation(input));
//                break;
//            case "search":
//                System.out.println(pronunDict.getTerms(new Phoneme(input)));
//                break;
//            default:
//                return;
//        }
    }
    
    public static void printPronunciation(String[] wordList){
        PronunciationDictionary pronunDict;
        for(String w: wordList){
            System.out.println(w);
            pronunDict
                    = PronunciationDictionary.getPronunciationDictionary("Arpabet");
            System.out.println("Arpabet:"+
                    stringifyPronunciation(pronunDict.getPronunciation(w)));
            pronunDict
                    = PronunciationDictionary.getPronunciationDictionary("IPA");
            System.out.println("IPA:"+
                    stringifyPronunciation(pronunDict.getPronunciation(w)));
            System.out.println("===\t===\t===");
        }
    }
        
    public static void findWords(String phoneme){
        
        PronunciationDictionary pronunDict
                = PronunciationDictionary.getPronunciationDictionary("Arpabet");
        System.out.println(pronunDict.getTerms("z"));
        pronunDict
                = PronunciationDictionary.getPronunciationDictionary("IPA");
        System.out.println(pronunDict.getTerms("z"));
    }

}
