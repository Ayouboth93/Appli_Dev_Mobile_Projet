package com.example.appli_dev_mobile_v2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Habitat {
    private int id;
    private int floor;
    private int area;

    public Habitat(int id, int floor, int area) {
        this.id = id;
        this.floor = floor;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public int getArea() {
        return area;
    }
    public static List<Habitat> getListFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        return gson.fromJson(json, type);
    }

}
/*
public class Habitat {
    private String nom;
    private int etage;
    private List<Integer> listeEquipements;  // Contient les IDs drawables (ic_aspirateur, etc.)

    public Habitat(String nom, int etage, List<Integer> listeEquipements) {
        this.nom = nom;
        this.etage = etage;
        this.listeEquipements = listeEquipements;
    }

    public String getNom() {
        return nom;
    }

    public int getEtage() {
        return etage;
    }

    public List<Integer> getListeEquipements() {
        return listeEquipements;
    }

    public int getNombreEquipements() {
        return listeEquipements == null ? 0 : listeEquipements.size();
    }
    public static Habitat getFromJson(String json){
        Gson gson = new Gson();
        Habitat obj = gson.fromJson(json, Habitat.class);
        return obj;
    }
    public static List<Habitat> getListFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        List<Habitat> list = gson.fromJson(json, type);
        return list;
    }

}
*/