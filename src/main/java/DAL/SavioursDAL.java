package DAL;

import entities.Hero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SavioursDAL {
    private String dbName;
    public SavioursDAL(String dbName){
        this.dbName = dbName;
    }
    public void addNewEntry(int[] cid, ArrayList<Hero> heroes) throws SQLException {
        int numOfCitizens = cid.length;
        int numOfHeroes = heroes.size();
        String query = "insert into saviours values";
        int numOfQueryParams = numOfCitizens * numOfHeroes;
        int i, j;
        for (i = 0; i < numOfQueryParams; i++) {
            query += "(?,?),";
        }
        query = query.substring(0, query.length() - 1) + ";";
        try (Connection connection = ConnectionManager.getInstance().connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            int initVal;
            for (i = 0; i < numOfHeroes; i++) {
                initVal = 2 * i + 1;
                for (j = initVal; j < numOfQueryParams * 2; j += numOfHeroes * 2)
                    stmt.setInt(j, heroes.get(i).getId());
            }
            for (i = 0; i < numOfCitizens; i++) {
                initVal = 2 + i * numOfHeroes * 2;
                for (j = initVal; j < initVal + numOfHeroes * 2; j += 2)
                    stmt.setInt(j, cid[i]);
            }
            stmt.executeUpdate();

        }
    }
}
