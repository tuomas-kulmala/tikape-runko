/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.SQLException;
import tikape.runko.database.ViestialueDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.database.Database;

/**
 *
 * @author Tuomas
 */
public class Alkudata {
    private Database database;
    
    public Alkudata(Database database){
        this.database = database;
    }
    public void luo()throws SQLException{
        ViestialueDao viestialueDao = new ViestialueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        
        viestialueDao.lisaa("TestausAlue");
        //viestiketjuDao.lisaa(, otsikko, viesti, lahettaja, lahettajaIp);
    }
}
