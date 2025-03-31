package com.example.appli_dev_mobile_v2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Habitat {
    private static int habitat_id;
    private int floor;
    private int area;
    private String firstname;
    private String lastname;
    private  List<String> appliances; // liste des noms d'équipement

    // Getters
    public static int getHabitat_id() { return habitat_id; }
    public int getFloor() { return floor; }
    public int getArea() { return area; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public  List<String> getAppliances() { return appliances; }

    // setter
    public static void setHabitatId(int id) {
        habitat_id = id;
    }

    // Pour le nombre d'équipements
    public int getNumberOfAppliances() {
        if (appliances == null) return 0;
        return appliances.size();
    }

    // Méthode de parsing
    public static List<Habitat> getListFromJson(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Habitat>>(){}.getType();
        return gson.fromJson(json, type);
    }

    // Convertir la liste d'équipements (String) en liste d'icônes (int)
    // Ex: "Aspirateur" -> R.drawable.ic_aspirateur
    public List<Integer> getListeEquipementsIcons() {
        List<Integer> icons = new ArrayList<>();
        if (appliances == null) return icons;

        for (String equip : appliances) {
            String lower = equip.toLowerCase();
            switch (lower) {
                case "aspirateur":
                    icons.add(R.drawable.ic_aspirateur);
                    break;
                case "fer à repasser":
                case "fer a repasser":
                    icons.add(R.drawable.ic_fer_a_repasser);
                    break;
                case "machine à laver":
                case "machine a laver":
                    icons.add(R.drawable.ic_machine_a_laver);
                    break;
                case "climatiseur":
                    icons.add(R.drawable.ic_climatiseur);
                default:
                    icons.add(R.drawable.ic_launcher_foreground); // une icône par défaut
                    break;
            }
        }
        return icons;
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