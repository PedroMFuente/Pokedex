package com.example.pokedex.interfaces;

import com.example.pokedex.models.PokemonEntity;

import java.util.ArrayList;

public interface ListInterface {
    public interface View {
        void startFormActivity();
        void startSearchActivity();
        void startAboutActivity();
        void startFormActivity(String id);
        void DeletePokemon();
    }

    public interface Presenter {
        void onClickFloatingButton();
        void onClickSearchButton();
        void onClickAboutButton();
        void onClickRecycledViewItem(String id);
        ArrayList<PokemonEntity> getAll();
        void setPokeFirstTime();
        boolean DeletePokemon(String id);
        boolean InsertItemAgain(PokemonEntity poke);
        ArrayList<PokemonEntity> searchdate(String n);
        ArrayList<PokemonEntity> searchname(String n);
        ArrayList<PokemonEntity> searchtype(String n);
    }
}
