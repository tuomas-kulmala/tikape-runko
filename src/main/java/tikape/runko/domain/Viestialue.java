/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Tuomas
 */
public class Viestialue {
    
    private int id;
    private String nimi;
    private String last;
    private int lkm;
    
    
    public Viestialue (int id, String nimi){
        this.id = id;
        this.nimi = nimi;
    }

 
    public String getNimi(){
        return this.nimi;
    }
    public int getId(){
        return this.id;
    }
    public String getLast(){
        return this.last;
    }
    public void setViimeinen(String aika){
        this.last = aika;
    }
    public void setLkm(int lkm){
        this.lkm = lkm;
    }
    public int getLkm(){
        return this.lkm;
    }
    
}
