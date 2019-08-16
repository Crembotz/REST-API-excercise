package unit;

import DAL.HeroDAL;
import entities.Hero;
import org.junit.After;
import org.junit.Test;
import org.postgresql.geometric.PGpolygon;
import types.PowerCategory;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HeroTest {

    private HeroDAL heroDAL = new HeroDAL("herodbtest");
    private int insertedHeroId = -1;

    @Test
    public void testGetSpecificHeroById() {
        try {
            PGpolygon polygon = new PGpolygon("((3,3),(4,4),(3,3))");
            int id = 2;
            String fName = "Ray";
            String lName = "man";
            float luck = 7;
            float powerLevel = 88.9f;
            String sPower = "Limbless body";
            Hero hero = new Hero(id, fName, lName, luck, powerLevel, polygon, sPower, PowerCategory.Categories.spower);
            assertEquals(hero, heroDAL.getSpecificHero(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testGetAllHeroesThatSavedCivilian()
    {
        ArrayList heroes = new ArrayList();
        try {
            Hero hero = new Hero(421,"Koa","Tiki",1234,1234,new PGpolygon("((1,1),(6,6),(1,1))"),"Elemental powers", PowerCategory.Categories.spower);
            heroes.add(hero);
            hero = new Hero(422,"Rock","man",88.43f,1000,new PGpolygon("((1,1),(3,3),(6,6),(1,1))"),"Absorbing powers", PowerCategory.Categories.spower);
            heroes.add(hero);
            hero = new Hero(423,"Son","Goku",9999,800000000,new PGpolygon("((1,1),(3,3),(9,9),(1,1))"),"Super strength", PowerCategory.Categories.spower);
            heroes.add(hero);
            assertEquals(heroes, heroDAL.getAllHeroesThatSavedCivilian(888));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddNewHero()
    {
        try {
            Hero hero = new Hero("Natsu", "Dragneel", 777, 9998,
                    new PGpolygon("((3,4),(5,5),(3,4))"), "Dragon slayer magic", PowerCategory.Categories.spower);
            hero = heroDAL.addNewHero(hero);
            assertNotNull(hero);
            insertedHeroId = hero.getId();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void removeInsertedBattle(){
        if (insertedHeroId > -1) {
            try {
                heroDAL.removeHeroEntry(insertedHeroId);
                insertedHeroId = -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}





