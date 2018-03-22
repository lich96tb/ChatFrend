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
 * Created by lich96tb on 9/13/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<News> objects;

    public NewsAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView txtTitle=row.findViewById(R.id.txtTitle);
        ImageView imageView=row.findViewById(R.id.img);
        TextView tg=row.findViewById(R.id.txtTime);
        TextView kenh=row.findViewById(R.id.txtKenh);
        News news=this.objects.get(position);
        txtTitle.setText(news.getTitle());
        tg.setText(news.getThoiGian().substring(0, 22));
        kenh.setText(news.getKenh());
        Picasso.with(context)
                .load(news.getImageURL())
                .placeholder(R.drawable.f)
                .error(R.drawable.b)
                .into(imageView);
        return row;
    }
}
