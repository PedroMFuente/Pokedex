package com.example.pokedex.models;

import android.os.Build;
import android.util.Log;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.pokedex.R;
import com.example.pokedex.views.MyApplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PokemonEntity extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String item;
    private String date;
    private String type1;
    private String type2;
    private int attack;
    private int hp;
    private String image ="";
    private boolean shiny;

    public PokemonEntity(){
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        boolean flag=false;
        if(name!=null && name.length()>0 && name.length()<=10){
            this.name = name.toUpperCase();
            flag=true;
        }
        return flag;
    }

    public String getItem() {
        return item;
    }

    public boolean setItem(String item) {
        boolean flag=false;
        if(item!=null){
            Pattern p =Pattern.compile("[A-Za-zÑñ ]+");
            Matcher m = p.matcher(item);
            if(m.matches()){
                this.item = item;
                flag=true;
            }
        }
        return flag;
    }

    public String getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean setDate(String date) {
        boolean dateflag=false;
        if(date!=null&&date.length()>0){

            Pattern pat2= Pattern.compile("^([0-2][0-9]|3[0-1])(\\/|-)(0[1-9]|1[0-2])\\2(\\d{4})$");
            Matcher mat = pat2.matcher(date);
            if(mat.matches()){
                LocalDate truedate;
                try{
                    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    truedate=LocalDate.parse(date,formatters);
                    this.date=date;
                    dateflag=true;
                    Log.d("brrr", "setDate: ");
                }catch(Exception e){
                    try{
                        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        truedate=LocalDate.parse(date,formatters);
                        dateflag=true;
                        this.date =date;
                    }catch (Exception ex){
                        dateflag=false;
                    }
                }
            }
        }
        return dateflag;
    }

    public int getAttack() {
        return attack;
    }

    public boolean setAttack(String attack) {
        boolean flag=false;
        if(attack!=null && attack.length()>0){
            int aux=Integer.parseInt(attack);
            if(aux<256){
                this.attack = aux;
                flag=true;
            }
        }
        return flag;
    }

    public int getHp() {
        return hp;
    }

    public boolean setHp(String hp) {
        boolean flag=false;
        if(hp!=null && hp.length()>0){
            int aux=Integer.parseInt(hp);
            if(aux<256){
                this.hp = aux;
                flag=true;
            }
        }

        return flag;
    }

    public String getType1(){
        return type1;
    }

    public boolean setType1(String type){
        boolean flag=false;
        if(type!=null){
            //if(type.equals(MyApplication.getContext().getString(R.string.spinnertype)))
            if(type!="--NINGUNO--"){
                this.type1=type;
                flag=true;
            }
        }
        return flag;
    }

    public String getType2(){
        return type2;
    }

    public boolean setType2(String type){
        boolean flag=false;

        if(type!=null){
            //if(type.equals(MyApplication.getContext().getString(R.string.spinnertype)))
            if(!type.equals(this.getType1())){
                this.type2=type;
                flag=true;
            }
        }
        return flag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
    }

    @Override
    public String toString() {
        return "PokemonEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", item='" + item + '\'' +
                ", date='" + date + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", attack=" + attack +
                ", hp=" + hp +
                ", image='" + image + '\'' +
                ", shiny=" + shiny +
                '}';
    }
}
