package tikape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.*;
import tikape.database.collector.*;
import tikape.pojo.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:smoothiet.db");
        database.setDebugMode(true);

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao raakaaineDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosraakaaineDao = new AnnosRaakaAineDao(database);

        get("/smoothielista", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/smoothiet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("raakaaineet", raakaaineDao.findAll());
            map.put("annosraakaaineet", annosraakaaineDao.findAll());

            return new ModelAndView(map, "smoothiet");
        }, new ThymeleafTemplateEngine());

        post("/smoothiet/lisaa", (req, res) -> {
            String nimi = req.queryParams("nimi");

            if (!nimi.equals("")) {
                Annos annos = new Annos(-1, nimi);
                annosDao.save(annos);
            }

            res.redirect("/smoothiet");
            return "";
        });

        post("/smoothiet/koosta", (req, res) -> {
            Integer raakaaine = Integer.parseInt(req.queryParams("raakaaine"));
            Integer annos = Integer.parseInt(req.queryParams("annos"));
            Integer jarjestys = 0;
            String jarjestys2 = req.queryParams("jarjestys");
            if (jarjestys2.matches("[0-9]+") && jarjestys2.length() > 0) {
                jarjestys = Integer.parseInt(jarjestys2);
            }

            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");

            List<AnnosRaakaAine> lista = new ArrayList<>();
            lista = annosraakaaineDao.findAll();

            int loytyi = 0;
            for (AnnosRaakaAine arr : lista) {
                if (arr.getAnnosId() == annos && arr.getRaakaAineId() == raakaaine) {
                    loytyi = 1;
                }
            }

            if (loytyi == 0) {
                if (jarjestys > 0 && !(maara.equals(""))) {
                    AnnosRaakaAine annosraakaaine = new AnnosRaakaAine(-1, raakaaine, annos, jarjestys, maara, ohje);
                    annosraakaaineDao.save(annosraakaaine);
                }
            }

            res.redirect("/smoothiet");
            return "";
        });

        post("/smoothiet/:id/delete", (req, res) -> {
            annosDao.delete(Integer.valueOf(req.params("id")));
            annosraakaaineDao.deleteS(Integer.valueOf(req.params("id")));

            res.redirect("/smoothiet");
            return "";
        });

        post("/smoothiet/poistarivi/:id/delete", (req, res) -> {
            annosraakaaineDao.delete(Integer.valueOf(req.params("id")));

            res.redirect("/smoothiet");
            return "";
        });

        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaaineDao.findAll());
            map.put("lukumaarat", annosraakaaineDao.perAnnos());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());

        post("/ainekset", (req, res) -> {
            String nimi = req.queryParams("nimi");

            if (!nimi.equals("")) {
                RaakaAine raakaaine = new RaakaAine(-1, nimi);
                raakaaineDao.save(raakaaine);
            }

            res.redirect("/ainekset");
            return "";
        });

        post("/ainekset/:id/delete", (req, res) -> {
            raakaaineDao.delete(Integer.valueOf(req.params("id")));
            annosraakaaineDao.deleteR(Integer.valueOf(req.params("id")));

            res.redirect("/ainekset");
            return "";
        });

    }
}
