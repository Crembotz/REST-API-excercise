package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    final String url = "jdbc:postgresql://localhost:5432/";
    final String user = "postgres";
    final String password = "123";
    private static ConnectionManager instance;
    public static ConnectionManager getInstance()
    {
        if(instance==null)
            instance = new ConnectionManager();
        return instance;
    }


    public Connection connect(String dbName) throws SQLException {
        return DriverManager.getConnection(url+dbName, user, password);
    }

    private ConnectionManager(){}

}
