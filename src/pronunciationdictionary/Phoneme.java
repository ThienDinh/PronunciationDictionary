/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pronunciationdictionary;

/**
 *
 * @author tdinh
 */
public class Phoneme implements Comparable{
    private final String form;
    private final String location;
    
    public Phoneme(String form, String location){
        this.form = form;
        if(location == null){
            this.location = "unknown";
        }
        else{
            this.location = location;
        }
    }
    
    public String getForm(){
        return form;
    }
    
    public String getLocation(){
        return location;
    }
    
    @Override
    public String toString(){
        return String.format("%s %s", getForm(), getLocation());
    }
    
    private Phoneme phonemeValidity(Object obj){
        if(obj == null){
            throw new NullPointerException("Argument is null.");
        }
        if(!(obj instanceof Phoneme)){
            throw new ClassCastException("Argument is not a phoneme object.");
        }
        return (Phoneme) obj;
    }
    
    @Override
    public int compareTo(Object obj) {        
        Phoneme other = phonemeValidity(obj);           
        return this.form.compareTo(other.form);
    }
    
    @Override
    public boolean equals(Object obj){
        Phoneme other = phonemeValidity(obj);
        return this.form.equals(other.form);
    }
}
