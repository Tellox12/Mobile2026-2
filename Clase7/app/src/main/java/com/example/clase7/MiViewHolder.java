package com.example.clase7;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MiViewHolder extends RecyclerView.ViewHolder {
    private final TextView textView;
    private ImageView imageView;

    public MiViewHolder(View view) {
        super(view);

        textView = (TextView) view.findViewById(R.id.textView);
        imageView = (ImageView) view.findViewById(R.id.imgPersonaje);
    }

    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}