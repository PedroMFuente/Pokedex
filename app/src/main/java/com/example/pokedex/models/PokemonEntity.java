package com.example.pokedex.models;

import java.time.LocalDate;

public class PokemonEntity {

    private String name;
    private String item;
    private LocalDate date;
    private int attack;
    private int hp;

    public PokemonEntity(){
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean flag=false;
        if(name.length()<=10){
            this.name = name;
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
