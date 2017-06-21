/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.List;
import java.util.ArrayList;
import tikape.runko.database.ViestialueDao;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author Tuomas
 */
public class Viestialue {
    
    private int id;
    private String nimi;
    private String last;
    private int lkm;
    private List<Viestiketju> viestiketjut = new ArrayList<>();
    
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
    public void setViimeisetketjut(List<Viestiketju> viestiketjut){
        this.viestiketjut = viestiketjut;
    }
    public List<Viestiketju> getViimeisetketjut(){
        return viestiketjut;
    }
   /* public List<String> getViimeisetketjut(){
        ArrayList<String> uusiLista = new ArrayList<>();
        uusiLista.add("test1");
        uusiLista.add("test2");
                
        return uusiLista;
    } */
}
