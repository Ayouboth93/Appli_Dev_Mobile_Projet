package com.example.appli_dev_mobile_v2;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appli_dev_mobile_v2.Habitat;
import com.example.appli_dev_mobile_v2.R;

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
        TextView tvNombreEquipements = convertView.findViewById(R.id.tvNombreEquipements);
        LinearLayout layoutEquipements = convertView.findViewById(R.id.layoutEquipements);

        // Afficher nom + prénom
        String occupant = habitat.getFirstname() + " " + habitat.getLastname();
        tvNom.setText(occupant);

        // Afficher "ETAGE X"
        tvEtage.setText("ETAGE " + habitat.getFloor());

        // Nombre d’équipements
        int nb = habitat.getNumberOfAppliances();
        tvNombreEquipements.setText(nb + (nb > 1 ? " équipements" : " équipement"));

        // On enlève les anciennes icônes (si recyclage de vues)
        while (layoutEquipements.getChildCount() > 1) {
            layoutEquipements.removeViewAt(1);
        }

        // Ajouter dynamiquement les icônes
        List<Integer> icons = habitat.getListeEquipementsIcons();
        for (int drawableId : icons) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(drawableId);

            // Optionnel : dimensionner l’icône (24dp)
            int size = (int) (getContext().getResources().getDisplayMetrics().density * 24);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = 8; // marge entre icônes
            imageView.setLayoutParams(params);

            layoutEquipements.addView(imageView);
        }

        return convertView;
    }
}
