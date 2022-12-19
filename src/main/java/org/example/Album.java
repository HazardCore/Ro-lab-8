package org.example;

import java.io.Serializable;

public class Album implements Serializable {
    String name;
    public String content;

    public Album(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
