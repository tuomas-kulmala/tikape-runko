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
public class Viestiketju {
    
    private int id;
    private int viestialue;
    private String otsikko;
    private String viimeisinViesti;
    
    public Viestiketju (int id, int viestialue, String otsikko){
        this.id = id;
        this.viestialue = viestialue;
        this.otsikko = otsikko;
    }
    
}
