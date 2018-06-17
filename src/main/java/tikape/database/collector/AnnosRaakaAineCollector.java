package tikape.database.collector;

import java.sql.ResultSet;
import java.sql.SQLException;
import tikape.database.Collector;
import tikape.pojo.AnnosRaakaAine;

public class AnnosRaakaAineCollector implements Collector<AnnosRaakaAine> {

    @Override
    public AnnosRaakaAine collect(ResultSet rs) throws SQLException {
        return new AnnosRaakaAine(rs.getInt("id"), rs.getInt("RaakaAineId"), rs.getInt("AnnosId"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("ohje"));
    }

}
