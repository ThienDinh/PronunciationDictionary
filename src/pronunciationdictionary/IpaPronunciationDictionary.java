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

/**
 *
 * @author tdinh
 */
public class IpaPronunciationDictionary extends PronunciationDictionary{
    
    public IpaPronunciationDictionary() throws IOException{
        super();
    }

    @Override
    public Set<String> getTerms(String ipaPhoneme) {
        Phoneme arpabetPhoneme = phonemeDict
                .convertPhoneme(ipaPhoneme, "IPA", "Arpabet");
        Set<String> terms = cmuDict.getTerms(arpabetPhoneme.getForm());
        return terms;
    }

    @Override
    protected String parsePhoneme(String arpabet) {
        String stress = "";
        // If the phoneme contains stress.
        if (Character
                .isDigit(arpabet
                        .codePointAt(arpabet
                                .length() - 1))) {
            stress = String.valueOf(arpabet
                            .charAt(arpabet
                                    .length() - 1));
            arpabet = arpabet
                    .substring(0, arpabet
                            .length() - 1);
        }
//        System.out.println("Arpabet:" + arpabet);
        String phoneme = phonemeDict
                        .convertPhoneme(arpabet, "Arpabet", "IPA").getForm();
//        System.out.println("Arpabet:" + arpabet);
        switch(stress){
            case "1":
                phoneme = "ˈ" + phoneme;
                break;
            case "2":
                phoneme = "ˌ" + phoneme;
                break;
            default:
                break;
        }
        return phoneme;    
    }
}
