package com.example.nosql;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClothingAdapter extends ArrayAdapter<Clothing> {
    public ClothingAdapter(Context context, ArrayList<Clothing> clothingList) {
        super(context, 0, clothingList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.clothing_item, parent, false);
        }

        Clothing clothing = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.itemNameTextView);
        TextView sizeTextView = convertView.findViewById(R.id.itemSizeTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.itemDescriptionTextView);
        ImageView imageView = convertView.findViewById(R.id.itemImageView);

        nameTextView.setText(clothing.getName());
        sizeTextView.setText(clothing.getSize());
        descriptionTextView.setText(clothing.getDescription());

        if (clothing.getImagePath() != null && !clothing.getImagePath().isEmpty()) {
            imageView.setImageURI(Uri.parse(clothing.getImagePath()));
        } else {
            imageView.setImageResource(R.drawable.no_image); // Установи изображение-заглушку
        }

        return convertView;
    }
}
