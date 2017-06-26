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
        
        viestialueDao.lisaa("Ohjelmointi");
        viestiketjuDao.lisaa(1, "Python ohjeet hakusessa", "En ymmärrä, miksi tämä on niin vaikeaa", "aloittelija", "127.0.0.1");
        viestiDao.lisaa(1, "Katso online dokumentaatiosta tai Stack Overflowsta","Pythonmaster","127.0.0.1");
        viestiDao.lisaa(1, "No ei niitä ainakaan täältä kannata kysellä","Anonymous","127.0.0.1");
        viestiDao.lisaa(1, "Joo huomasin :D","Matti","127.0.0.1");
        
        viestiketjuDao.lisaa(1, "Miksi c++ on niin vaikeaa?", "Mistä kannattaa etsiä Python ohjeita?", "Matti", "127.0.0.1");
        viestiDao.lisaa(2, "Mikä siinä on niin vaikea käsittää","chiefmaster","127.0.0.1");
        viestiDao.lisaa(2, "Ehkä olet tyhmä","Anonymous","127.0.0.1");
        viestiDao.lisaa(2, "Trolli trololoo","teme","127.0.0.1");
        
        viestialueDao.lisaa("Lemmikit");
        viestiketjuDao.lisaa(2, "Mitä lemmikkejä sinulla on?", "Mitä lemmikkejä teillä on kotona?", "Eemeli", "127.0.0.1");
        viestiDao.lisaa(3, "Minulla on koira","Anonymous","127.0.0.1");
        viestiDao.lisaa(3, "Otin juuri kissanpennun","teme","127.0.0.1");
        viestiDao.lisaa(3, "vihaankoiria!","miksu","127.0.0.1");
        viestiDao.lisaa(3, "Kissa","Anonymous","127.0.0.1");
        viestiDao.lisaa(3, "Minullakin on kissa","joni","127.0.0.1");
        viestiDao.lisaa(3, "Pelkään koiria","joni","127.0.0.1");
        
        viestialueDao.lisaa("Lentokoneet");
        viestiketjuDao.lisaa(3, "Tupolev vai Airbus", "Kumpia Finnairin kannattaisi hankkia?", "kyselijä", "127.0.0.1");
        viestiDao.lisaa(4, "Ei kumpiakaan. Ei pysy ilmassa","tietäjä","127.0.0.1");
        viestiDao.lisaa(4, "Ehkä olet tyhmä","Anonymous","127.0.0.1");
        
        viestiketjuDao.lisaa(3, "Oliko saksalaisilla helikopteri toisessa maailmansodassa?", "miten oli","utelias","127.0.0.1");
        viestiDao.lisaa(5, "Ei mutta Venäläisillä oli MI-24","hekomies","127.0.0.1");
        viestiDao.lisaa(5, "Tämä kysymys ei kuulu tälle alueelle","pääJohtaja","127.0.0.1");

    }
}
