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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lich96tb on 9/13/2017.
 */

public class ChatRiengAdapter extends ArrayAdapter<Chat> {
    FirebaseUser idd = FirebaseAuth.getInstance().getCurrentUser();
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<Chat> objects;
    DatabaseReference mDatabase;
    String g;

    public ChatRiengAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Chat> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        Chat chat = getItem(position);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View row;
        if (idd.getUid().equals(chat.getId())) {
            row = inflater.inflate(R.layout.gui1, null);
        } else {
            row = inflater.inflate(R.layout.nhan1, null);
        }

        TextView txtTen = row.findViewById(R.id.txtTen);
        TextView txtNoiDung = row.findViewById(R.id.txtNoiDung);
        TextView txtThoiGian = row.findViewById(R.id.txtThoiGian);
        final ImageView imgNhan=row.findViewById(R.id.imgnhan1);
        if(!chat.getId().equals(idd.getUid())) {
            mDatabase.child("listBanbe").child(chat.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    g = dataSnapshot.child("anh").getValue().toString();
                    Picasso.with(context).load(g).into(imgNhan);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Picasso.with(context).load(g).into(imgNhan);
        txtTen.setText(chat.getTen());
        txtNoiDung.setText(chat.getNoidung());
        txtThoiGian.setText(chat.getThoigian());
        if (idd.getUid().equals(chat.getId())) {
            imgNhan.setVisibility(View.INVISIBLE);
        }

        return row;
    }
}

