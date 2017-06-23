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
    private String lahettaja;
    private int viestiketju;
    private String viesti;
    private String lahettajaIp;
    private String viestinaika;
    
    public Viesti(int id, String lahettaja, int viestiketju,String viesti,String viestinaika, String lahettajaIp){
        this.id = id;
        this.lahettaja = lahettaja;
        this.viestiketju = viestiketju;
        this.viesti = viesti;
        this.viestinaika = viestinaika;
        this.lahettajaIp = lahettajaIp;
    }
    public String getLahettaja(){
        return this.lahettaja;
    }
    public int getViestiketju(){
        return this.viestiketju;
    }

    public String getViesti(){
        return this.viesti;
    }
    public String getViestinaika(){
        return this.viestinaika;
    }

    public String getLahettajaip(){
        return this.lahettajaIp;
    }
}
