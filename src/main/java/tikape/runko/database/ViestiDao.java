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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti V WHERE V.id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Integer id = rs.getInt("id");
        String lahettaja = rs.getString("lahettaja");
        Integer viestiketju = rs.getInt("lahettaja");
        String otsikko = rs.getString("otsikko");
        String viesti = rs.getString("viesti");
        String viestinaika = rs.getString("viestinaika");
        String lahettajaIp = rs.getString("lahettaja_ip");

        Viesti v = new Viesti(id, lahettaja, viestiketju, viesti,viestinaika,lahettajaIp);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    } 

    public List<Viesti> findAll(int ketjuId) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti V WHERE V.viestiketju = ? ORDER BY V.viestinaika DESC");
        stmt.setObject(1, ketjuId);
        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String lahettaja = rs.getString("lahettaja");
            Integer viestiketju = rs.getInt("viestiketju");
            String viesti = rs.getString("viesti");
            String viestinaika = rs.getString("viestinaika");
            String lahettajaIp = rs.getString("lahettaja_ip");
            
            //System.out.println(viestinaika);

            viestit.add(new Viesti(id, lahettaja, viestiketju, viesti,viestinaika,lahettajaIp));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
        public void lisaa(int id, String viesti,String lahettaja,String lahettajaIp) throws SQLException {
        // Tarkistetaan naivisti syötteiden sisältö
        if (!viesti.isEmpty()){
            if(lahettaja.isEmpty()){
                lahettaja = "Anonyymi";
            }
            Connection connection = database.getConnection();
            
            //Viestin pituuden tarkastus
            if(viesti.length()>400){
                viesti = viesti.substring(0, 399);
            }

            // Kirjoitetaan uusi viesti tauluun
            PreparedStatement stmt_3 = connection.prepareStatement("INSERT INTO Viesti (lahettaja, viestiketju, viesti,lahettaja_ip) VALUES(?, ?, ?, ?);");
            stmt_3.setObject(1, lahettaja);
            stmt_3.setObject(2, id);
            stmt_3.setObject(3, viesti);
            stmt_3.setObject(4, lahettajaIp);
            stmt_3.execute();
            stmt_3.close();

            connection.close();
        }
    }
}
