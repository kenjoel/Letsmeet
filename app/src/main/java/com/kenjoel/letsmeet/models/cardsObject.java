package com.kenjoel.letsmeet.models;

public class cardsObject {
    private String gender;
    private String name;
    private String phone;
    private String profileImageUrl;

    public cardsObject(String gender, String name, String phone, String imageUrl) {
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.profileImageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }


}
