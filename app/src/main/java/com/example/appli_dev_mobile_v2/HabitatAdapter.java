package com.example.appli_dev_mobile_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appli_dev_mobile_v2.Habitat;

import java.util.List;

public class HabitatAdapter extends ArrayAdapter<Habitat> {

    public HabitatAdapter(Context context, List<Habitat> habitats) {
        super(context, 0, habitats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_habitat, parent, false);
        }

        // Récupérer l’objet Habitat
        Habitat habitat = getItem(position);

        // Récupérer les vues
        TextView tvNom = convertView.findViewById(R.id.nomHabitat);
        TextView tvEtage = convertView.findViewById(R.id.etageHabitat);
        LinearLayout layoutEquipements = convertView.findViewById(R.id.layoutEquipements);
        TextView tvNombreEquipements = convertView.findViewById(R.id.tvNombreEquipements);

        // Affecter le nom
        tvNom.setText(habitat.getId());

        // Affecter l'étage (ex : "ETAGE 1")
        tvEtage.setText("ETAGE " + habitat.getFloor());
        /*
        // Mettre "X équipements"
        int nb = habitat.getNombreEquipements();
        tvNombreEquipements.setText(nb + (nb > 1 ? " équipements" : " équipement"));

        // Retirer d’anciens icônes éventuellement présents
        // (important si les views sont recyclées dans une ListView)
        // On garde tvNombreEquipements (index 0),
        // donc on supprime tout ce qui vient après.
        while (layoutEquipements.getChildCount() > 1) {
            layoutEquipements.removeViewAt(1);
        }

        // Ajouter dynamiquement les icônes correspondant à la liste d’équipements
        for (int drawableId : habitat.getListeEquipements()) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(drawableId);

            // Optionnel : dimensionner l’icône
            int size = (int) getContext().getResources().getDisplayMetrics().density * 24; // 24dp
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = 8; // un petit espace entre les icônes
            imageView.setLayoutParams(params);

            layoutEquipements.addView(imageView);
        }
        */
        return convertView;
    }


}
