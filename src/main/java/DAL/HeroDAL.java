package DAL;

import entities.Hero;
import org.postgresql.geometric.PGpoint;
import org.postgresql.geometric.PGpolygon;
import types.PowerCategory;

import java.sql.*;
import java.util.ArrayList;

public class HeroDAL {
    private final String dbName;
    private final ConnectionManager connectionManager;

    public HeroDAL(String dbName) {
        this.dbName = dbName;
        connectionManager = ConnectionManager.getInstance();
    }

    public ArrayList<Hero> createHeroArrayListFromResultSet(ResultSet rs) {
        ArrayList<Hero> heroes = new ArrayList<>();
        try {
            while (rs.next()) {
                heroes.add(new Hero(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getFloat(4), rs.getFloat(5),
                        new PGpolygon(rs.getString(6)), rs.getString(7), PowerCategory.toPowerCategory(rs.getString(8))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return heroes;
    }

    public ArrayList<Hero> getAllHeroes() throws Exception {
        String query = "select * from hero";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if(rs==null)
                throw new NullPointerException("No heroes were found.");
            return createHeroArrayListFromResultSet(rs);
        }

    }

    public Hero getSpecificHero(int id) throws SQLException {
        String query = "select * from hero where hid=?;";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return new Hero(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getFloat(4), rs.getFloat(5),
                    new PGpolygon(rs.getString(6)), rs.getString(7), PowerCategory.toPowerCategory(rs.getString(8)));

        }
    }

    public ArrayList<Hero> getAllHeroesThatSavedCivilian(int cid) throws SQLException {
        String query = "select hero.hid,hero.fname,hero.lname,hero.luck,\n" +
                "hero.power_level,hero.area,hero.spower,hero.power_category\n" +
                "from hero join saviours on hero.hid=saviours.hid\n" +
                "join civilians on saviours.cid=civilians.cid where civilians.cid=?;";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, cid);
            ResultSet rs = stmt.executeQuery();
            return createHeroArrayListFromResultSet(rs);
        }

    }

    public Hero addNewHero(Hero hero) throws SQLException {
        if (!isHeroValid(hero))
            return null;
        String query = "insert into hero (fname,lname,luck,power_level,area,spower,power_category) values(?,?,?,?,?,?,?);";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hero.getfName());
            stmt.setString(2, hero.getlName());
            stmt.setFloat(3, hero.getLuck());
            stmt.setFloat(4, hero.getPower_level());
            stmt.setObject(5, hero.getArea());
            stmt.setString(6, hero.getsPower());
            stmt.setObject(7, hero.getPower_category(), Types.OTHER);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            hero.setId(rs.getInt(1));
            return hero;
        }
    }

    public void removeHeroEntry(int id) throws SQLException {
        String query = "delete from hero where hid=?";
        try (Connection connection = connectionManager.connect(dbName);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }


    private boolean isHeroValid(Hero hero) {
        if (hero.getId() < 0 || hero.getfName() == null || hero.getlName() == null || hero.getLuck() < 0
                || hero.getPower_level() < 0 || hero.getsPower() == null || hero.getPower_category() == null)
            return false;
        PGpoint[] points = hero.getArea().points;
        if (!points[0].equals(points[points.length - 1]))
            return false;
        return true;
    }


}
