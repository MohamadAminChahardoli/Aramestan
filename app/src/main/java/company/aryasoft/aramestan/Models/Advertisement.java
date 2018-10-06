package company.aryasoft.aramestan.Models;

public class Advertisement {
    private int AdId;
    private String ImageName;
    private String Description;

    public int getAdId() {
        return AdId;
    }

    public String getImageName() {
        return ImageName;
    }

    public String getDescription() {
        return Description.toString();
    }

    public void setAdId(int adId) {
        AdId = adId;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
