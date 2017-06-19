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
import tikape.runko.domain.Viesti;
/**
 *
 * @author Tuomas
 */
public class ViestiDao {
        
    private Database database;
        
    public ViestiDao(Database database) {
        this.database = database;
    }
    

    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer lahettaja = rs.getInt("viestiketju");
        Integer viestiketju = rs.getInt("lahettaja");
        String otsikko = rs.getString("otsikko");
        String viesti = rs.getString("viesti");
        String viestinaika = rs.getString("viestinaika");

        Viesti v = new Viesti(id, lahettaja, viestiketju, otsikko, viesti,viestinaika);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    } 

    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer lahettaja = rs.getInt("id");
            Integer viestiketju = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String viesti = rs.getString("viesti");
            String viestinaika = rs.getString("viestinaika");
            

            viestit.add(new Viesti(id, lahettaja, viestiketju, otsikko, viesti,viestinaika));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
 
}
