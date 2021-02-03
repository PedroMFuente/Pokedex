package com.example.pokedex.interfaces;

import com.example.pokedex.models.PokemonEntity;

import java.util.List;

public interface FormInterface {
    public interface View {
        void finishFormActivity();
        void deleteFormActivity();
        void PermissionRefused();
        void PermissionGiven();
        void PermissionRequest();
        void SelectPhoto();
        void SavePokemon();
        void NoSavePokemon();
        void DeleteAcp();
        void Deletent();
    }

    public interface Presenter {
        void onClickSaveButton(PokemonEntity poke);
        void onClickEditButton(PokemonEntity poke);
        void onClickDeleteButton(String id);
        PokemonEntity GetPokemonById(String id);
        void PermissionRefused();
        void PermissionGiven();
        void ShowGallery();
        void BackToList();
        void WriteExternalStoragePermission();
        List<String> GetType();
        String getError(String name);
    }
}
