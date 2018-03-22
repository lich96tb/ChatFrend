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
 * Created by lich96tb on 9/16/2017.
 */

public class TinNhanAdapter extends ArrayAdapter<TinNhan> {
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<TinNhan> objects;

    public TinNhanAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<TinNhan> objects) {
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
        ImageView img = row.findViewById(R.id.imgtnDaiDien);
        TextView txtTen = row.findViewById(R.id.txttnTen);
        TextView txtNoiDung = row.findViewById(R.id.txttnnoidung);
        ImageView imgOnline = row.findViewById(R.id.imgtnOnline);
        TinNhan tinNhan = this.objects.get(position);
        txtTen.setText(tinNhan.getTen());
        txtNoiDung.setText(tinNhan.getNoidung());
        Picasso.with(context)
                .load(tinNhan.getAnh())
                .into(img);
//        if(tinNhan.online==1){
//            imgOnline.setImageResource(R.drawable.online);
//        }else imgOnline.setImageResource(R.drawable.off);


        return row;
    }
}
