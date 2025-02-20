package com.example.appli_dev_mobile_v2;

import java.util.List;

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
}
