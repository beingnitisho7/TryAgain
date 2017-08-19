package com.example.dell.tryagain;

/**
 * Created by Dell on 17/08/2017.
 */

public class ListItem {
    private String name;

    public ListItem(String firstname, String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
