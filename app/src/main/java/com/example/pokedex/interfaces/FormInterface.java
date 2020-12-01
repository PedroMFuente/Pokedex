package com.example.pokedex.interfaces;

public interface FormInterface {
    public interface View {
        void finishFormActivity();
        void deleteFormActivity();
    }

    public interface Presenter {
        void onClickSaveButton();
        void onClickDeleteButton();
        String getError(String name);
    }
}
