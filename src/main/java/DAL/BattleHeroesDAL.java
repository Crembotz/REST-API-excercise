package DAL;

import entities.Hero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BattleHeroesDAL {
    private String dbName;
    public BattleHeroesDAL(String dbName){
        this.dbName = dbName;
    }
    public void addNewEntry(int bid, ArrayList<Hero> heroes) throws SQLException {
        String query = "insert into battleheroes values";
        int numOfHeroes = heroes.size();
        int i;
        for (i = 0; i < numOfHeroes; i++)
            query += "(?,?),";
        query = query.substring(0, query.length() - 1) + ";";
        try (Connection connection = ConnectionManager.getInstance().connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            int numOfQueryParams = numOfHeroes * 2;
            for (i = 1; i < numOfQueryParams; i += 2)
                stmt.setInt(i, bid);
            for (i = 2; i <= numOfQueryParams; i += 2)
                stmt.setInt(i, heroes.get(i / 2 - 1).getId());
            System.out.println(stmt);
            stmt.executeUpdate();
        }
    }
}
