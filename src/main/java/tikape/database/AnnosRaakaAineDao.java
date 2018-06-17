package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.database.collector.AnnosRaakaAineCollector;
import tikape.pojo.AnnosRaakaAine;
import tikape.pojo.RaakaAineLkm;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database database;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnosRaakaAine save(AnnosRaakaAine object) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine (RaakaAineId, AnnosId, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, object.getRaakaAineId());
            stmt.setInt(2, object.getAnnosId());
            stmt.setInt(3, object.getJarjestys());
            stmt.setString(4, object.getMaara());
            stmt.setString(5, object.getOhje());
            stmt.executeUpdate();
        }

        return object;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        List<AnnosRaakaAine> annosraakaaineet = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, RaakaAineId, AnnosId, jarjestys, maara, ohje FROM AnnosRaakaAine").executeQuery()) {

            while (result.next()) {
                annosraakaaineet.add(new AnnosRaakaAine(result.getInt("id"), result.getInt("RaakaAineId"), result.getInt("AnnosId"), result.getInt("jarjestys"), result.getString("maara"), result.getString("ohje")));
            }
        }

        return annosraakaaineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void deleteR(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE RaakaAineId = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public void deleteS(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE AnnosId = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    public List<RaakaAineLkm> perAnnos() throws SQLException {
        List<RaakaAineLkm> lista = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT RaakaAineId, COUNT(*) AS lkm FROM AnnosRaakaAine GROUP BY RaakaAineId").executeQuery()) {

            while (result.next()) {
                lista.add(new RaakaAineLkm(result.getInt("RaakaAineId"), result.getInt("lkm")));
            }
        }

        return lista;
    }
}
