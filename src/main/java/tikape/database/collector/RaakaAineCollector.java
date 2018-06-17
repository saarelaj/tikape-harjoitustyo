package tikape.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.database.Collector;
import tikape.pojo.RaakaAine;

public class RaakaAineCollector implements Collector<RaakaAine> {

    @Override
    public RaakaAine collect(ResultSet rs) throws SQLException {
        return new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
    }

}
