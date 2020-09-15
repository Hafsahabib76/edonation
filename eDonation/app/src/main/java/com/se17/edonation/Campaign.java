package com.se17.edonation;

public class Campaign {
    private String Title, aboutCampaign, city, category;

    public Campaign() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAboutCampaign() {
        return aboutCampaign;
    }

    public void setAboutCampaign(String aboutCampaign) {
        this.aboutCampaign = aboutCampaign;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
