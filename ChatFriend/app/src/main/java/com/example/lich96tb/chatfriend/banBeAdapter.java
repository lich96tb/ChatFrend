package com.example.lich96tb.chatfriend;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lich96tb on 8/7/2017.
 */

public class banBeAdapter extends ArrayAdapter<banBe1> {
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<banBe1> objects;

    public banBeAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<banBe1> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtTen = row.findViewById(R.id.txtTenfr);
        TextView txtMail = row.findViewById(R.id.txtMaifr);
        ImageView img=row.findViewById(R.id.img);
        ImageView imgOnline=row.findViewById(R.id.imgOnline);
        banBe1 banbe = this.objects.get(position);
        txtTen.setText(banbe.getTen());
        txtMail.setText(banbe.getMail());
        if (banbe.getOnline()==0){
            imgOnline.setImageResource(R.drawable.off);
        }else {
            imgOnline.setImageResource(R.drawable.online);
        }
        Picasso.with(getContext())
                .load(banbe.getAnh())
                .placeholder(R.drawable.a) // optional
                .error(R.drawable.a)         // optional
                .into(img);

        return row;
    }
}
