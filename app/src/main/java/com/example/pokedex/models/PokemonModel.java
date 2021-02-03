package com.example.pokedex.models;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class PokemonModel {

    public ArrayList<PokemonEntity> getAllSumarize(){
        ArrayList<PokemonEntity> allpoke=new ArrayList<>();
        ArrayList<PokemonEntity> resumeAll=new ArrayList<>();

        try{
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            RealmResults<PokemonEntity> result = realm.where(PokemonEntity.class).findAll();
            allpoke.addAll(realm.copyFromRealm(result));
            realm.commitTransaction();

            realm.close();
            for (PokemonEntity p: allpoke){
                PokemonEntity pe= new PokemonEntity();
                pe.setImage(p.getImage());
                pe.setId(p.getId());
                pe.setName(p.getName());
                pe.setAttack(Integer.toString(p.getAttack()));
                pe.setHp(Integer.toString(p.getHp()));
                pe.setItem(p.getItem());
                pe.setType1(p.getType1());
                pe.setType2(p.getType2());
                pe.setShiny(p.isShiny());

                resumeAll.add(pe);
            }
        }catch(Exception e){

        }

        return resumeAll;
    }

    public boolean insert (PokemonEntity poke){
        boolean result=false;
        poke.setId(UUID.randomUUID().toString());
        Realm realm = Realm.getDefaultInstance();
        if(poke!=null){
            try{
                realm.beginTransaction();
                realm.copyToRealm(poke);
                realm.commitTransaction();
                realm.close();
                result=true;
            }catch(Exception e){
                Log.d("Insert","Fallo");
            }
        }

        return result;
    }

    public boolean insertwithoutid(PokemonEntity p){
        boolean result=false;
        Realm realm= Realm.getDefaultInstance();
        if(p!=null){
            try{
                realm.beginTransaction();
                realm.copyToRealm(p);
                realm.commitTransaction();
                realm.close();
                result=true;
            }catch(Exception e){
                //error
            }
        }
        return result;
    }

    public boolean update(PokemonEntity poke){
        boolean result=false;
        Realm realm = Realm.getDefaultInstance();

        if(poke!=null){
            try{
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(poke);
                realm.commitTransaction();
                realm.close();
                /*
                realm.executeTransaction(r->{

                });*/
                result=true;
            }catch(Exception e){
                Log.d("Update","Fallo");
            }
        }

        return result;
    }

    public boolean delete(String id){
        boolean result=false;
        Realm realm= Realm.getDefaultInstance();

        if(id!=null){
            try{
                realm.beginTransaction();
                PokemonEntity poke = realm.where(PokemonEntity.class).equalTo("id",id).findFirst();
                poke.deleteFromRealm();
                realm.commitTransaction();
                realm.close();
                /*
                realm.executeTransaction(r->{

                });*/
                result=true;
            }catch(Exception e){
                Log.d("Delete","fallo");
            }
        }
        return result;
    }

    public PokemonEntity getPokeEntity(String id){
        PokemonEntity poke=null;
        Realm realm= Realm.getDefaultInstance();

        if(id!=null){
            try{
                realm.beginTransaction();
                poke=realm.where(PokemonEntity.class).equalTo("id",id).findFirst();
                realm.commitTransaction();
                realm.close();
            }catch (Exception e){
                Log.d("getEntity","fallo");
            }
        }

        return poke;
    }

    public List<String> getSpinnerType(){
        List<String> spinnervalues=new ArrayList<>();
        List<PokemonEntity> pokelist=new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();

        try{
            realm.beginTransaction();
            RealmResults<PokemonEntity> auxlist= realm.where(PokemonEntity.class).distinct("type1").findAll();
            pokelist.addAll(realm.copyFromRealm(auxlist));
            realm.commitTransaction();
            realm.close();
        }catch(Exception e){
            //error
        }

        for(PokemonEntity p:pokelist){
            spinnervalues.add(p.getType1());
        }

        return spinnervalues;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PokemonEntity> SearchName(String n){
        ArrayList<PokemonEntity> allpoke= new ArrayList<>();
        ArrayList<PokemonEntity> searchby= new ArrayList<>();
        Log.d("PASO",n);
        Realm realm=Realm.getDefaultInstance();
        try{
            RealmResults<PokemonEntity> aux=realm.where(PokemonEntity.class).equalTo("name",n).findAll();
            allpoke.addAll(realm.copyFromRealm(aux));
            realm.close();

            Log.d("PASO","xcv"+aux.toString());

            for(PokemonEntity p: allpoke){
                Log.d("PASO","Los pokes"+p.toString());
                PokemonEntity paux= new PokemonEntity();
                paux.setId(p.getId());
                paux.setName(p.getName());
                paux.setItem(p.getItem());
                paux.setImage(p.getImage());
                searchby.add(paux);
            }
        }catch(Exception e){

        }
        return searchby;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PokemonEntity> SearchDate(String n){
        ArrayList<PokemonEntity> allpoke= new ArrayList<>();
        ArrayList<PokemonEntity> searchby= new ArrayList<>();
        Realm realm=Realm.getDefaultInstance();
        try{
            RealmResults<PokemonEntity> aux=realm.where(PokemonEntity.class).equalTo("date",n).findAll();
            allpoke.addAll(realm.copyFromRealm(aux));
            realm.close();

            for(PokemonEntity p: allpoke){
                PokemonEntity paux= new PokemonEntity();
                paux.setId(p.getId());
                paux.setName(p.getName());
                paux.setItem(p.getItem());
                paux.setImage(p.getImage());
                searchby.add(paux);
            }
        }catch(Exception e){

        }
        return searchby;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<PokemonEntity> SearchType(String n){
        ArrayList<PokemonEntity> allpoke= new ArrayList<>();
        ArrayList<PokemonEntity> searchby= new ArrayList<>();

        try{
            Realm realm=Realm.getDefaultInstance();
            RealmResults<PokemonEntity> aux=realm.where(PokemonEntity.class).equalTo("type1",n).findAll();
            allpoke.addAll(realm.copyFromRealm(aux));
            realm.close();

            for(PokemonEntity p: allpoke){
                PokemonEntity paux= new PokemonEntity();
                paux.setId(p.getId());
                paux.setName(p.getName());
                paux.setItem(p.getItem());
                paux.setImage(p.getImage());
                searchby.add(paux);
            }
        }catch(Exception e){

        }
        return searchby;
    }

}
