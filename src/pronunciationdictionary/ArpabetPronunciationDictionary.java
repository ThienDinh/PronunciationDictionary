/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pronunciationdictionary;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author tdinh
 */
public class ArpabetPronunciationDictionary extends PronunciationDictionary {

    public ArpabetPronunciationDictionary() throws IOException {
        super();
    }

    @Override
    public Set<String> getTerms(String phoneme) {
        Set<String> terms = cmuDict.getTerms(phoneme);
        return terms;
    }

    @Override
    protected String parsePhoneme(String arpabet) {
        return arpabet;
    }

}
