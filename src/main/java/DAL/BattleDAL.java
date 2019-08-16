package DAL;

import entities.Battle;
import entities.Hero;
import org.postgresql.geometric.PGpoint;

import java.sql.*;
import java.util.ArrayList;

public class BattleDAL {


    private final String dbName;
    private final ConnectionManager connectionManager;

    public BattleDAL(String dbName) {
        this.dbName = dbName;
        connectionManager = ConnectionManager.getInstance();
    }

    private ArrayList<Battle> createBattleArrayListFromResultSet(ResultSet rs) {
        ArrayList<Battle> battles = new ArrayList<>();
        try {
            while (rs.next()) {
                battles.add(new Battle(rs.getInt(1), rs.getString(2), rs.getFloat(3),
                        rs.getBoolean(4), new PGpoint(rs.getString(5))));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return battles;
    }

    public ArrayList<Battle> getBattlesByHeroId(int id) throws SQLException//Gets all the battles that a certain hero took part in.
    {
        String query = "select battles.bid,battles.vname,battles.vpower,battles.win,battles.location\n" +
                "from hero join battleheroes on hero.hid=battleheroes.hid \n" +
                "join battles on battleheroes.bid=battles.bid where hero.hid=?;";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return createBattleArrayListFromResultSet(rs);
        }
    }

    public Battle addNewBattle(String vName, float vPowerLevel, boolean heroWin, String location) throws SQLException {
        String query = "insert into battles (vname,vpower,win,location) values(?,?,?,?);";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            Battle battle = new Battle(vName, vPowerLevel, heroWin, new PGpoint(location));
            stmt.setString(1, battle.getvName());
            stmt.setFloat(2, battle.getvPower());
            stmt.setBoolean(3, battle.isWin());
            stmt.setObject(4, battle.getLocation());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            battle.setId(rs.getInt(1));
            return battle;
        }
    }


    public ArrayList<Hero> getHeroesThatCanFightVillain(String location) throws SQLException {
        String query = "select * from hero where area @>?;";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, new PGpoint(location));
            ResultSet rs = stmt.executeQuery();
            ArrayList<Hero> heroes = new HeroDAL(dbName).createHeroArrayListFromResultSet(rs);
            return heroes;
        }
    }

    public void removeBattleEntry(int id) throws SQLException {
        String query = "delete from battles where bid=?";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
