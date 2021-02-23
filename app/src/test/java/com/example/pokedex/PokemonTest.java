package com.example.pokedex;

import com.example.pokedex.models.PokemonEntity;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

public class PokemonTest {

    private PokemonEntity pokemon;

    @Before
    public void setUp(){
        this.pokemon= new PokemonEntity();
    }

    @Test
    public void Test_Name(){
        Assert.assertEquals(true, this.pokemon.setName("Pokemon"));
        Assert.assertEquals(false, this.pokemon.setName(""));
    }

    @Test
    public void Test_Item(){
        Assert.assertEquals(true, this.pokemon.setItem("Cinta elegida"));
        Assert.assertEquals(true, this.pokemon.setItem("Bolaluminosa"));
        Assert.assertEquals(false, this.pokemon.setItem("1Cinta elegida"));
    }

    @Test
    public void Test_Date(){
        Assert.assertEquals(true,this.pokemon.setDate("23/02/2021"));
        Assert.assertEquals(false, this.pokemon.setDate("30/02/2018"));
        Assert.assertEquals(false, this.pokemon.setDate("23-02-2021"));
        Assert.assertEquals(true, this.pokemon.setDate("18/05/2020"));
    }

    @Test
    public void Test_Hp(){
        Assert.assertEquals(true, this.pokemon.setHp("50"));
        Assert.assertEquals(false, this.pokemon.setHp("-10"));
        Assert.assertEquals(false, this.pokemon.setHp("Hp"));
    }

    @Test
    public void Test_Attack(){
        Assert.assertEquals(true, this.pokemon.setAttack("50"));
        Assert.assertEquals(false, this.pokemon.setAttack("-10"));
        Assert.assertEquals(false, this.pokemon.setAttack("Attack"));
    }

}
