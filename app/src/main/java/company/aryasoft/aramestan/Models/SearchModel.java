package company.aryasoft.aramestan.Models;

public class SearchModel {

    private String FirstName;
    private String LastName;
    private String FatherName;
    private String DeadYear;
    private boolean Sex;


    public SearchModel() {
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public String getDeadDate() {
        return DeadYear;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public void setDeadDate(String deadYear) {
        DeadYear = deadYear;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }
}
