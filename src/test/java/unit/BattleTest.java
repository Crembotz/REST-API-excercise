package unit;

import DAL.BattleDAL;
import entities.Battle;
import org.junit.After;
import org.junit.Test;
import org.postgresql.geometric.PGpoint;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class BattleTest {

    private BattleDAL battleDAL = new BattleDAL("herodbtest");
    private int insertedBattleId = -1;

    @Test
    public void testAddNewBattle() {
        String vName = "Dr. Vegapunk";
        float powerLevel = 12.3f;
        String point = "(1,1)";
        int[] cid = new int[1];
        cid[0] = 567;
        try {
            Battle battle = battleDAL.addNewBattle(vName, powerLevel, true, point);
            assertNotNull(battle);
            insertedBattleId = battle.getId();
        } catch (SQLException e) {
            fail("Insertion has failed");
            e.printStackTrace();
        }
    }

    @Test
    public void testGetHeroBattles() {
        ArrayList<Battle> battles = new ArrayList<>();
        Battle battle = new Battle(77, "Spring Man", 44.77f, true, new PGpoint(3, 3));
        battles.add(battle);
        battle = new Battle(78, "Metal Man", 99.99f, true, new PGpoint(6, 6));
        battles.add(battle);
        ArrayList<Battle> fromDB = null;
        try {
            fromDB = battleDAL.getBattlesByHeroId(421);
            assertEquals(battles, fromDB);
        } catch (SQLException e) {
            fail("Unable to retrieve the battles this hero has participated in from the database.");
            e.printStackTrace();
        }
    }

    @After
    public void removeInsertedBattle() {
        if (insertedBattleId > -1) {
            try {
                battleDAL.removeBattleEntry(insertedBattleId);
                insertedBattleId = -1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
