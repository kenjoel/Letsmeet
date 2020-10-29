package com.kenjoel.letsmeet.models;

public class cards {
    private String name;
    private String userId;
    private String profileImageUrl;

    public cards(String name, String userId, String imageUrl) {
        this.name = name;
        this.userId = userId;
        this.profileImageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageUrl() {
        return profileImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.profileImageUrl = imageUrl;
    }
}
