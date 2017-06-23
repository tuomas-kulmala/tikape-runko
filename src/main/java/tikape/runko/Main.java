package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.ViestialueDao;
import tikape.runko.database.ViestiketjuDao;
import tikape.runko.database.ViestiDao;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:opiskelijat.db");
        database.init();


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
        post("/viestialue/viestiketju/:id", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            System.out.println(id);
            viestiDao.lisaa(Integer.parseInt(req.params(":id")),req.queryParams("viesti"),req.queryParams("lahettaja"),req.ip()); 
            res.redirect("/viestialue/viestiketju/" + req.params(":id"));
            return "ok";
        });
        
        
        // Yksittäinen viestiketu ja viestit
        get("/viestialue/viestiketju/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestiketju", viestiketjuDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("viestit", viestiDao.findAll(Integer.parseInt(req.params("id"))));
            
            return new ModelAndView(map, "viestiketju");   
        }, new ThymeleafTemplateEngine());
    }
}
