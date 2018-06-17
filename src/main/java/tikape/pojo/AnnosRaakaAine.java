package tikape.pojo;

public class AnnosRaakaAine {

    private Integer id;
    private Integer RaakaAineId;
    private Integer AnnosId;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine() {
    }

    public AnnosRaakaAine(Integer id, Integer RaakaAineId, Integer AnnosId, Integer jarjestys, String maara, String ohje) {
        this.id = id;
        this.RaakaAineId = RaakaAineId;
        this.AnnosId = AnnosId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
        
    public Integer getRaakaAineId() {
        return RaakaAineId;
    }

    public void setRaakaAineId(Integer RaakaAineId) {
        this.RaakaAineId = RaakaAineId;
    }

    public Integer getAnnosId() {
        return AnnosId;
    }

    public void setAnnosId(Integer AnnosId) {
        this.AnnosId = AnnosId;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public void setMaara(String maara) {
        this.maara = maara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

    

}