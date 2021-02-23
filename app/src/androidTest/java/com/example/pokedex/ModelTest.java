package com.example.pokedex;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.pokedex.models.PokemonEntity;
import com.example.pokedex.models.PokemonModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ModelTest {
    private PokemonModel pokemonmodel;

    @Before
    public void setUp(){
        pokemonmodel = new PokemonModel();
    }

    @Test
    public void createDatabase(){

        PokemonEntity poke = new PokemonEntity();
        poke.setId("1");
        poke.setName("RAICHU");
        poke.setItem("Bolaluminosa");
        poke.setAttack("55");
        poke.setType1("ELECTRICO");
        poke.setHp("50");

        //Insert
        assertEquals(true, this.pokemonmodel.insert(poke));

        //GetArray
        ArrayList<PokemonEntity> pokelist = pokemonmodel.getAllSumarize();
        assertNotEquals(0, pokelist.size());
        //GetArraySpinner
        List<String> typelist = pokemonmodel.getSpinnerType();
        assertNotEquals(0, typelist.size());
        //GetEntity
        PokemonEntity view = pokemonmodel.getPokeEntity(poke.getId());
        assertNotEquals(null, view);

        //SearchDate
        ArrayList<PokemonEntity> datelist = pokemonmodel.SearchDate(poke.getDate());
        assertNotEquals(0, datelist.size());
        //SearchType
        ArrayList<PokemonEntity> stypelist = pokemonmodel.SearchType(poke.getType1());
        assertNotEquals(0, stypelist.size());
        //SearchName
        ArrayList<PokemonEntity> namelist = pokemonmodel.SearchName(poke.getName());
        assertNotEquals(0, namelist.size());

        //Update
        PokemonEntity newpoke = poke;
        newpoke.setId("1");
        newpoke.setName("NewPoke");
        assertEquals(true, pokemonmodel.update(newpoke));

        //Delete
        assertEquals(true, pokemonmodel.delete("1"));
    }

}
