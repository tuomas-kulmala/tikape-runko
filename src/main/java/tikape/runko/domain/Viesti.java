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
public class Viesti {
    
    private int id;
    private int lahettaja;
    private int viestiketju;
    private String otsikko;
    private String viesti;
    private String viestinaika;
    
    public Viesti(int id, int lahettaja, int viestiketju, String otsikko,String viesti,String viestinaika){
        this.id = id;
        this.lahettaja = lahettaja;
        this.viestiketju = viestiketju;
        this.otsikko = otsikko;
        this.viesti = viesti;
    }
    public int getLahettaja(){
        return this.lahettaja;
    }
    public int getViestiketju(){
        return this.viestiketju;
    }
    public String getOtsikko(){
        return this.otsikko;
    }
    public String getViesti(){
        return this.viesti;
    }
    public String viestinAika(){
        return this.viestinaika;
    }
}
