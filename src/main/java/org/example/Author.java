package org.example;

import java.io.Serializable;
import java.util.HashMap;

public class Author implements Serializable {
    String name = "";
    public HashMap<String, Album> albums = new HashMap<String, Album>();
    public Author(String name) {
        this.name = name;
    }
}
