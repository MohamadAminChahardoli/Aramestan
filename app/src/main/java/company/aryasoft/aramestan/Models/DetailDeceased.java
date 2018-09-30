package company.aryasoft.aramestan.Models;

public class DetailDeceased {

    private int DeadId;
    private String FullName;
    private String FatherName;
    private String BirthDate;
    private String DeadDate;
    private String PlaceMartyr;
    private int RowNumber;
    private int GraveNumber;
    private String Lat;
    private String Lang;
    private String ImageName;
    private String DefunctTitle;
    private String BlockName;

    public DetailDeceased() {
    }

    public DetailDeceased(int deadId, String fullName, String fatherName,
                          String birthDate, String deadDate,
                          String placeMartyr, int rowNumber, int graveNumber,
                          String lat, String lang, String imageName, String defunctTitle, String blockName) {
        DeadId = deadId;
        FullName = fullName;
        FatherName = fatherName;
        BirthDate = birthDate;
        DeadDate = deadDate;
        PlaceMartyr = placeMartyr;
        RowNumber = rowNumber;
        GraveNumber = graveNumber;
        Lat = lat;
        Lang = lang;
        ImageName = imageName;
        DefunctTitle = defunctTitle;
        BlockName = blockName;
    }

    public int getDeadId() {
        return DeadId;
    }

    public String getFullName() {
        return FullName;
    }

    public String getFatherName() {
        return FatherName;
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

    public String getImageName() {
        return ImageName;
    }

    public String getDefunctTitle() {
        return DefunctTitle;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setDeadId(int deadId) {
        DeadId = deadId;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
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

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setDefunctTitle(String defunctTitle) {
        DefunctTitle = defunctTitle;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }
}
