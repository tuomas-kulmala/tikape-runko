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
import tikape.runko.domain.Opiskelija;
import tikape.runko.domain.Viestiketju;

/**
 *
 * @author Tuomas
 */
public class ViestiketjuDao {
        
    private Database database;
        
    public ViestiketjuDao(Database database) {
        this.database = database;
    }
    
    public Viestiketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer viestialue = rs.getInt("viestialue");
        String otsikko = rs.getString("otsikko");

        Viestiketju v = new Viestiketju(id, viestialue, otsikko);
        

        rs.close();
        stmt.close();
        connection.close();

        return v;
    } 

    public List<Viestiketju> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju WHERE id = ?");
        
        ResultSet rs = stmt.executeQuery();
        List<Viestiketju> viestiketjut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer viestialue = rs.getInt("viestialue");
            String otsikko = rs.getString("otsikko");

            viestiketjut.add(new Viestiketju(id,viestialue, otsikko));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    }

 
}
