package com.se17.edonation;

public class Charity {
    private String CharityName;
    private String ImageUrl;
    private String About;

    public Charity() {
    }

    @Override
    public String toString() {
        return "Charity{" +
                "CharityName='" + CharityName + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", About='" + About + '\'' +
                '}';
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getCharityName() {
        return CharityName;
    }

    public void setCharityName(String charityName) {
        CharityName = charityName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
