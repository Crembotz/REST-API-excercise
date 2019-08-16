package BL;

import DAL.BattleDAL;
import DAL.BattleHeroesDAL;
import DAL.SavioursDAL;
import entities.Battle;
import entities.Hero;

import java.sql.SQLException;
import java.util.ArrayList;

public class BattleBL {
    private BattleDAL battleDAL;
    private SavioursDAL savioursDAL;
    private BattleHeroesDAL battleHeroesDAL;
    public BattleBL(BattleDAL battleDAL,SavioursDAL savioursDAL,BattleHeroesDAL battleHeroesDAL) {
        this.battleDAL = battleDAL;
        this.savioursDAL = savioursDAL;
        this.battleHeroesDAL = battleHeroesDAL;
    }

    public ArrayList<Battle> getBattlesByHeroId(int id) throws SQLException//Gets all the battles that a certain hero took part in.
    {
        return battleDAL.getBattlesByHeroId(id);
    }

    public Battle addNewBattle(String vName, float vPowerLevel, String location, int[] cid) throws SQLException {
        ArrayList<Hero> heroes = battleDAL.getHeroesThatCanFightVillain(location);
        int heroPowerLevel = 0;
        boolean heroWin = false;
        for (Hero hero : heroes) {
            heroPowerLevel += hero.getPower_level() + hero.getLuck();
        }
        if (heroPowerLevel > vPowerLevel)
            heroWin = true;
        Battle battle = battleDAL.addNewBattle(vName, vPowerLevel, heroWin, location);
        if (heroes.size() > 0) {
            battleHeroesDAL.addNewEntry(battle.getId(),heroes);
            if (heroWin && cid.length > 0)
                savioursDAL.addNewEntry(cid, heroes);
        }
        return battle;
    }

    public ArrayList<Hero> getHeroesThatCanFightVillain(String location) throws SQLException {
        return battleDAL.getHeroesThatCanFightVillain(location);
    }


}
