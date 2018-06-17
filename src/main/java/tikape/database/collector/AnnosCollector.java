package tikape.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.database.Collector;
import tikape.pojo.Annos;

public class AnnosCollector implements Collector<Annos> {

    @Override
    public Annos collect(ResultSet rs) throws SQLException {
        return new Annos(rs.getInt("id"), rs.getString("nimi"));
    }

}
