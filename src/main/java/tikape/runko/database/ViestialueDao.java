/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tikape.runko.domain.Opiskelija;
import tikape.runko.domain.Viestialue;
import tikape.runko.domain.Viesti;
/**
 *
 * @author Tuomas
 */
public class ViestialueDao {
        
    private Database database;
        
    public ViestialueDao(Database database) {
        this.database = database;
    }
    
    public Viestialue findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestialue WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Viestialue v = new Viestialue(id, nimi);
        v.setViimeinen(this.findViimeinen(id));
        v.setLkm(this.laskeViestit(id));
        
        rs.close();
        stmt.close();
        connection.close();

        return v;
    } 

    public List<Viestialue> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestialue");

        ResultSet rs = stmt.executeQuery();
        List<Viestialue> viestialueet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            Viestialue v = new Viestialue(id,nimi);
            v.setViimeinen(this.findViimeinen(id));  
            v.setLkm(this.laskeViestit(id));
            viestialueet.add(v);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestialueet;
    }
    public String findViimeinen(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(viestinaika) AS viestinaika FROM Viesti WHERE viestiketju IN (SELECT id FROM Viestiketju WHERE viestialue = ?)");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        String viestinaika = rs.getString("viestinaika");

        rs.close();
        stmt.close();
        connection.close();

        return viestinaika;
        
    } 
    public int laskeViestit(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS lkm FROM Viesti WHERE viestiketju IN (SELECT id FROM Viestiketju WHERE viestialue = ?)");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return 0;
        }

        int lkm = Integer.parseInt(rs.getString("lkm"));

        rs.close();
        stmt.close();
        connection.close();

        return lkm;
        
    }
        public void lisaa(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viestialue (nimi) VALUES(?);");
        stmt.setObject(1, nimi);
        
        stmt.execute();
        
        stmt.close();
        connection.close();

        
    }
}

