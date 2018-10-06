package company.aryasoft.aramestan.Models;

public class DetailDeceased {

    private String BirthDate;
    private String DeadDate;
    private String PlaceMartyr;
    private int RowNumber;
    private int GraveNumber;
    private String Lat;
    private String Lang;
    private String BlockName;

    public DetailDeceased() {
    }

    public DetailDeceased(int deadId, String fullName, String fatherName,
                          String birthDate, String deadDate,
                          String placeMartyr, int rowNumber, int graveNumber,
                          String lat, String lang, String imageName, String defunctTitle, String blockName) {
        BirthDate = birthDate;
        DeadDate = deadDate;
        PlaceMartyr = placeMartyr;
        RowNumber = rowNumber;
        GraveNumber = graveNumber;
        Lat = lat;
        Lang = lang;
        BlockName = blockName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public String getDeadDate() {
        return DeadDate;
    }

    public String getPlaceMartyr() {
        return PlaceMartyr;
    }

    public int getRowNumber() {
        return RowNumber;
    }

    public int getGraveNumber() {
        return GraveNumber;
    }

    public String getLat() {
        return Lat;
    }

    public String getLang() {
        return Lang;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public void setDeadDate(String deadDate) {
        DeadDate = deadDate;
    }

    public void setPlaceMartyr(String placeMartyr) {
        PlaceMartyr = placeMartyr;
    }

    public void setRowNumber(int rowNumber) {
        RowNumber = rowNumber;
    }

    public void setGraveNumber(int graveNumber) {
        GraveNumber = graveNumber;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

}
