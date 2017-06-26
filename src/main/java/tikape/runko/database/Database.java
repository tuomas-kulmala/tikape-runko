package tikape.runko.database;

import java.sql.*;
import java.util.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;

        init();
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE IF EXISTS Viestialue");
        lista.add("DROP TABLE IF EXISTS Viestiketju");
        lista.add("DROP TABLE IF EXISTS Viesti");
        
        lista.add("CREATE TABLE Viestialue(id SERIAL PRIMARY KEY,nimi VARCHAR(255));");
        lista.add("CREATE TABLE Viestiketju(id SERIAL PRIMARY KEY,viestialue INT NOT NULL,otsikko VARCHAR(255),FOREIGN KEY(viestialue) REFERENCES Viestialue(id));");
        lista.add("CREATE TABLE Viesti(id SERIAL PRIMARY KEY,lahettaja VARCHAR,lahettaja_ip VARCHAR,viestiketju INT NOT NULL,viesti VARCHAR(600),viestinaika DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(viestiketju) REFERENCES Viestiketju(id));");
     
        /* Viestiketjujen lisäys iirretty Aloitusdata moduuliin
        lista.add("INSERT INTO Viestialue (nimi) VALUES('Ohjelmointi');");
        lista.add("INSERT INTO Viestiketju (viestialue,otsikko) VALUES((SELECT MAX(id)FROM Viestialue),'Miksi c++ on niin vaikeaa?')");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('aloittelija',(SELECT MAX(id)FROM Viestiketju),'En ymmärrä, miksi tämä on niin vaikeaa');");
        
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('chiefmaster',(SELECT MAX(id)FROM Viestiketju),'Mikä siinä on niin vaikea käsittää');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM Viestiketju),'Ehkä olet tyhmä');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('teme',(SELECT MAX(id)FROM Viestiketju),'Trolli trololoo');");
        
        lista.add("INSERT INTO Viestiketju (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Python ohjeet hakusessa')");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Matti',(SELECT MAX(id)FROM Viestiketju),'Mistä kannattaa etsiä Python ohjeita?');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Pythonist',(SELECT MAX(id)FROM Viestiketju),'Katso online dokumentaatiosta tai Stack Overflowsta');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM Viestiketju),'No ei niitä ainakaan täältä kannata kysellä');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Matti',(SELECT MAX(id)FROM Viestiketju),'Joo huomasin :D');");
        
        lista.add("INSERT INTO Viestialue (nimi) VALUES('Lemmikit');");
        lista.add("INSERT INTO Viestiketju (viestialue,otsikko) VALUES((SELECT MAX(id)FROM Viestiketju),'Mitä lemmikkejä sinulla on?')");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Eemeli',(SELECT MAX(id)FROM Viestiketju),'Mitä lemmikkejä teillä on kotona?');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('dogMan',(SELECT MAX(id)FROM Viestiketju),'Minulla on koira');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Milli',(SELECT MAX(id)FROM Viestiketju),'Otin juuri kissanpennun');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM Viestiketju),'vihaankoiria!?!');");
        
        lista.add("INSERT INTO Viestialue (nimi) VALUES('Lentokoneet');");
        lista.add("INSERT INTO Viestiketju (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Tupolev vai Airbus')");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('kyselijä',(SELECT MAX(id)FROM Viestiketju),'Kumpia Finnairin kannattaisi hankkia?');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('tietäjä',(SELECT MAX(id)FROM Viestiketju),'Ei kumpiakaan. Ei pysy ilmassa');");
       
        lista.add("INSERT INTO Viestialue (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Oliko saksalaisilla helikopteri toisessa maailmansodassa')");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('hekomies',(SELECT MAX(id)FROM Viestiketju),'Ei mutta Venäläisillä oli MI-24');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Araska',(SELECT MAX(id)FROM Viestiketju),'Tämä kysymys ei kuulu tälle alueelle');");

       */
        
        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Viestialue");
        lista.add("DROP TABLE Viestiketju");
        lista.add("DROP TABLE Viesti");
        
        lista.add("CREATE TABLE Viestialue(id INTEGER PRIMARY KEY AUTOINCREMENT,nimi VARCHAR(255));");
        lista.add("CREATE TABLE Viestiketju(id INTEGER PRIMARY KEY AUTOINCREMENT,viestialue INT NOT NULL,otsikko VARCHAR(255),FOREIGN KEY(viestialue) REFERENCES Viestialue(id));");
        lista.add("CREATE TABLE Viesti(id INTEGER PRIMARY KEY AUTOINCREMENT,lahettaja VARCHAR,lahettaja_ip VARCHAR,viestiketju INT NOT NULL,viesti VARCHAR(600),viestinaika DATETIME DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(viestiketju) REFERENCES Viestiketju(id));");
     
        /* Viestiketjujen lisäys iirretty Aloitusdata moduuliin
        lista.add("INSERT INTO VIESTIALUE (nimi) VALUES('Ohjelmointi');");
        lista.add("INSERT INTO VIESTIKETJU (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Miksi c++ on niin vaikeaa?')");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('aloittelija',(SELECT MAX(id)FROM VIESTIKETJU),'En ymmärrä, miksi tämä on niin vaikeaa');");
        
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('chiefmaster',(SELECT MAX(id)FROM VIESTIKETJU),'Mikä siinä on niin vaikea käsittää');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM VIESTIKETJU),'Ehkä olet tyhmä');");
        lista.add("INSERT INTO Viesti (lahettaja, viestiketju, viesti) VALUES('teme',(SELECT MAX(id)FROM VIESTIKETJU),'Trolli trololoo');");
        
        lista.add("INSERT INTO Viestiketju (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Python ohjeet hakusessa')");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Matti',(SELECT MAX(id)FROM VIESTIKETJU),'Mistä kannattaa etsiä Python ohjeita?');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Pythonist',(SELECT MAX(id)FROM VIESTIKETJU),'Katso online dokumentaatiosta tai Stack Overflowsta');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM VIESTIKETJU),'No ei niitä ainakaan täältä kannata kysellä');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Matti',(SELECT MAX(id)FROM VIESTIKETJU),'Joo huomasin :D');");
        
        lista.add("INSERT INTO VIESTIALUE (nimi) VALUES('Lemmikit');");
        lista.add("INSERT INTO VIESTIKETJU (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Mitä lemmikkejä sinulla on?')");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Eemeli',(SELECT MAX(id)FROM VIESTIKETJU),'Mitä lemmikkejä teillä on kotona?');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('dogMan',(SELECT MAX(id)FROM VIESTIKETJU),'Minulla on koira');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Milli',(SELECT MAX(id)FROM VIESTIKETJU),'Otin juuri kissanpennun');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Anonymous',(SELECT MAX(id)FROM VIESTIKETJU),'vihaankoiria!?!');");
        
        lista.add("INSERT INTO VIESTIALUE (nimi) VALUES('Lentokoneet');");
        lista.add("INSERT INTO VIESTIKETJU (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Tupolev vai Airbus')");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('kyselijä',(SELECT MAX(id)FROM VIESTIKETJU),'Kumpia Finnairin kannattaisi hankkia?');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('tietäjä',(SELECT MAX(id)FROM VIESTIKETJU),'Ei kumpiakaan. Ei pysy ilmassa');");
       
        lista.add("INSERT INTO VIESTIKETJU (viestialue,otsikko) VALUES((SELECT MAX(id)FROM VIESTIALUE),'Oliko saksalaisilla helikopteri toisessa maailmansodassa')");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('hekomies',(SELECT MAX(id)FROM VIESTIKETJU),'Ei mutta Venäläisillä oli MI-24');");
        lista.add("INSERT INTO VIESTI (lahettaja, viestiketju, viesti) VALUES('Araska',(SELECT MAX(id)FROM VIESTIKETJU),'Tämä kysymys ei kuulu tälle alueelle');");
        */
        
        return lista;
    }
}