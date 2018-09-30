package company.aryasoft.aramestan.Models;

public class Deceased {

    private int DeadId;
    private String FullName;
    private String FatherName;
    private String ImageName;
    private String DefunctTitle;

    public Deceased() {
    }

    public Deceased(int deadId, String fullName, String fatherName, String imageName, String defunctTitle) {
        DeadId = deadId;
        FullName = fullName;
        FatherName = fatherName;
        ImageName = imageName;
        DefunctTitle = defunctTitle;
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

    public String getImageName() {
        return ImageName;
    }

    public String getDefunctTitle() {
        return DefunctTitle;
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

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setDefunctTitle(String defunctTitle) {
        DefunctTitle = defunctTitle;
    }

}
