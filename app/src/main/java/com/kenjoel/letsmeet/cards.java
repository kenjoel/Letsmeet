package com.kenjoel.letsmeet;

public class cards {
    private String name;
    private String userId;

    public cards(String name, String userId) {
        this.name = name;
        this.userId = userId;
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
}
