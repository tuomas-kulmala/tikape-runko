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
    private String viimeisinAika;
    private int maara;
    
    public Viestiketju (int id, int viestialue, String otsikko){
        this.id = id;
        this.viestialue = viestialue;
        this.otsikko = otsikko;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getOtsikko(){
        return this.otsikko;
    }
    
    public String getViimeisinaika(){
        return this.viimeisinAika;
    }
    public void setViimeisinaika(String viimeisinAika){
        this.viimeisinAika = viimeisinAika;
    }
    public void setMaara(int maara){
        //System.out.println("maaara: " + maara);
        this.maara = maara;
    }
    public int getMaara(){
        return this.maara;

    }
}
