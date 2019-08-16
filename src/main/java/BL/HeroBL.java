package BL;

import DAL.HeroDAL;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Hero;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.SQLException;
import java.util.ArrayList;

public class HeroBL {
    private HeroDAL heroDAL;

    public HeroBL(HeroDAL heroDAL) {
        this.heroDAL = heroDAL;
    }

    public ArrayList<Hero> getAllHeroes() throws Exception {
        return heroDAL.getAllHeroes();
    }

    public Hero getSpecificHero(int id) throws SQLException {
        return heroDAL.getSpecificHero(id);
    }

    public ObjectNode getHeroAsJson(int id) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        Hero hero = heroDAL.getSpecificHero(id);
        ObjectNode node = mapper.createObjectNode();
        node.put("id", hero.getId());
        node.put("firstName", hero.getfName());
        node.put("LastName", hero.getlName());
        node.put("luck", hero.getLuck());
        node.put("powerLevel", hero.getPower_level());
        node.put("area", hero.getArea().toString());
        node.put("specialPower", hero.getsPower());
        node.put("powerCategory", hero.getPower_category().toString());
        return node;
    }

    public ArrayList<Hero> getAllHeroesThatSavedCivilian(int cid) throws SQLException {
        return heroDAL.getAllHeroesThatSavedCivilian(cid);
    }

    public Hero addNewHero(Hero hero) throws SQLException {
        return heroDAL.addNewHero(hero);
    }


}
