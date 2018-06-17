package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.collector.RaakaAineCollector;
import tikape.pojo.RaakaAine;

public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RaakaAine save(RaakaAine object) throws SQLException {
        RaakaAine byName = findByName(object.getNimi());

        if (byName != null) {
            return byName;
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi) VALUES (?)");
            stmt.setString(1, object.getNimi());
            stmt.executeUpdate();
        }

        return findByName(object.getNimi());
    }

    private RaakaAine findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, nimi FROM RaakaAine WHERE nimi = ?");
            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();
            if (!result.next()) {
                return null;
            }

            return new RaakaAine(result.getInt("id"), result.getString("nimi"));
        }
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        List<RaakaAine> raakaaineet = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, nimi FROM RaakaAine").executeQuery()) {

            while (result.next()) {
                raakaaineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
            }
        }

        return raakaaineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
