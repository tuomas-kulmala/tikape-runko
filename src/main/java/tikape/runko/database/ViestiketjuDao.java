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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju V WHERE V.id = ?");
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
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viestiketju V WHERE V.id = ?");
        
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
    public List<Viestiketju> getViimeisetketjut(Integer viestialueId)throws SQLException{
        Connection connection = database.getConnection();
        //PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS lkm FROM Viesti WHERE viestiketju IN (SELECT id FROM Viestiketju WHERE viestialue = ?)");
        PreparedStatement stmt = connection.prepareStatement("SELECT K.id, K.otsikko,MAX(viestinaika) AS viestinaika FROM Viesti V JOIN Viestiketju K ON K.id = V.viestiketju WHERE K.viestialue =? GROUP BY K.otsikko, K.id ORDER BY viestinaika DESC LIMIT 10");
        stmt.setObject(1, viestialueId);
 
        
        ResultSet rs = stmt.executeQuery();
  
        List<Viestiketju> viestiketjut = new ArrayList<>();
        
        while (rs.next()) {
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("otsikko"));
            System.out.println(rs.getString("viestinaika"));
            
            Integer id = rs.getInt("id");
            String viestinaika = rs.getString("viestinaika");
            String otsikko = rs.getString("otsikko");
            Viestiketju v = new Viestiketju(id,viestialueId,otsikko);
            v.setViimeisinaika(viestinaika);
            v.setMaara(this.laskeViestit(id));
            viestiketjut.add(v);

            }
        rs.close();
        stmt.close();
        connection.close();

        return viestiketjut;
    } 
    public int laskeViestit(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) AS lkm FROM Viesti V WHERE  V.viestiketju = ?");
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
    public void lisaa(int id, String otsikko, String viesti,String lahettaja,String lahettajaIp) throws SQLException {
        // Tarkistetaan naivisti syötteiden sisältö
        if (!otsikko.isEmpty() && !viesti.isEmpty()){
            if(lahettaja.isEmpty()){
                lahettaja = "Anonyymi";
            }
            
            // Otsikon pituuden tarkastus
            if(otsikko.length()>255){
                otsikko = otsikko.substring(0, 254);
            }
            Connection connection = database.getConnection();

            // Uusi viestiketju kantaan
            PreparedStatement stmt_1 = connection.prepareStatement("INSERT INTO Viestiketju (viestialue, otsikko) VALUES(?, ?);");
            stmt_1.setObject(1, id);
            stmt_1.setObject(2, otsikko);
            stmt_1.execute();
            stmt_1.close();

            // Haetaan uuden ketjun id kannasta
            PreparedStatement stmt_2 = connection.prepareStatement("SELECT MAX(K.id) AS ketjuid FROM Viestiketju K;");
            ResultSet rs = stmt_2.executeQuery();       
            int ketjuId =0;
            if (rs.next()) {
                ketjuId = Integer.parseInt(rs.getString("ketjuid"));
            }
            rs.close();
            stmt_2.close();

            // Kirjoitetaan uusi viesti tauluun
            PreparedStatement stmt_3 = connection.prepareStatement("INSERT INTO Viesti (lahettaja, viestiketju, viesti,lahettaja_ip) VALUES(?, ?, ?, ?);");
            stmt_3.setObject(1, lahettaja);
            stmt_3.setObject(2, ketjuId);
            stmt_3.setObject(3, viesti);
            stmt_3.setObject(4, lahettajaIp);
            stmt_3.execute();
            stmt_3.close();

            connection.close();
        }
        
    }
}
