package com.example.pokedex.models;

public class ContactEntity {
    //Ejemplo model

    private String name;

    public ContactEntity() {
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if(name.startsWith("a")){
            this.name = name;
            return true;
        }else{
            return false;
        }
    }
}
