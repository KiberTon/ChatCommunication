package com.kibekin.whatsappclone;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class WhatsAppMessageAdapter extends ArrayAdapter<WhatsAppMessage> {

    public WhatsAppMessageAdapter(Context context, int resource, List<WhatsAppMessage> whatsAppMessageList) {
        super(context, resource, whatsAppMessageList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.message_item, parent, false);
        }
        ImageView imageViewPhoto = convertView.findViewById(R.id.imageViewPhoto);
        TextView textViewText = convertView.findViewById(R.id.textViewText);
        TextView textViewName = convertView.findViewById(R.id.textViewName);

        WhatsAppMessage whatsAppMessage = getItem(position);
        assert whatsAppMessage != null;
        boolean isText = whatsAppMessage.getImageUrl() == null;
        if (isText) {
            textViewText.setVisibility(View.VISIBLE);
            imageViewPhoto.setVisibility(View.GONE);
            textViewName.setText(whatsAppMessage.getText());
        } else {
            textViewText.setVisibility(View.GONE);
            imageViewPhoto.setVisibility(View.VISIBLE);
            Glide.with(imageViewPhoto.getContext()).load(whatsAppMessage.getImageUrl()).into(imageViewPhoto);
        }

        textViewName.setText(whatsAppMessage.getName());
        textViewText.setText(whatsAppMessage.getText());
        return convertView;
    }
}
