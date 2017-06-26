package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.ViestialueDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alkudata;

public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        // käytetään oletuksena paikallista sqlite-tietokantaa
        String jdbcOsoite = "jdbc:sqlite:opiskelijat.db";
        // jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        } 

        Database database = new Database(jdbcOsoite);
        

        //Luodaan alkudataa
        //Alkudata alkudata = new Alkudata(database);
        //alkudata.luo();

        ViestialueDao viestialueDao = new ViestialueDao(database);
        ViestiketjuDao viestiketjuDao = new ViestiketjuDao(database);
        ViestiDao viestiDao = new ViestiDao(database);
        
    /*    get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "viestialueet");
        }, new ThymeleafTemplateEngine()); */

        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestialueet", viestialueDao.findAll());

            return new ModelAndView(map, "viestialueet");
        }, new ThymeleafTemplateEngine());
        
        // Yksittäiset viestialueet ja kymmenen viimeisimmän ketjun lista
        get("/viestialue/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestialue", viestialueDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestiketjut", viestiketjuDao.getViimeisetketjut(Integer.parseInt(req.params("id"))));
            
            return new ModelAndView(map, "viestialue");
        }, new ThymeleafTemplateEngine());
        
        // Viestialueen lisäys
        post("/", (req, res) -> {
            viestialueDao.lisaa(req.queryParams("nimi"));
            res.redirect("/");
            return "ok";
        });
        
        // Viestiketjun lisäys
        post("/viestialue/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            System.out.println(id);
            viestiketjuDao.lisaa(Integer.parseInt(req.params(":id")),req.queryParams("otsikko"),req.queryParams("viesti"),req.queryParams("lahettaja"),req.ip()); 
            res.redirect("/viestialue/" + req.params(":id"));
            return "ok";
        });
        
        // Viestin lisäys
        post("/viestialue/viestiketju/:id/:page", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            System.out.println(id);
            viestiDao.lisaa(Integer.parseInt(req.params(":id")),req.queryParams("viesti"),req.queryParams("lahettaja"),req.ip()); 
            res.redirect("/viestialue/viestiketju/" + req.params(":id") + "/" + req.params("page"));
            return "ok";
        });
        
        
        // Yksittäinen viestiketu ja viestit
        get("/viestialue/viestiketju/:id/:page", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketju", viestiketjuDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestiDao.findAllByBlock(Integer.parseInt(req.params("id")),Integer.parseInt(req.params("page"))));
            
            return new ModelAndView(map, "viestiketju");   
        }, new ThymeleafTemplateEngine());
    }
}
