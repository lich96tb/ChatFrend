package com.example.lich96tb.chatfriend;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class Chat_Adapter extends ArrayAdapter<Chat> {
    Animation anim_FadeIn;
    FirebaseUser idd = FirebaseAuth.getInstance().getCurrentUser();
    @NonNull
    Activity context;
    @LayoutRes
    int resource;
    @NonNull
    List<Chat> objects;

    public Chat_Adapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Chat> objects) {
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
        Animaition();
        View row;
        if (idd.getUid().equals(chat.getId())) {
            row = inflater.inflate(R.layout.gui, null);
        } else {
            row = inflater.inflate(R.layout.nhan, null);
        }

        TextView txtTen = row.findViewById(R.id.txtTen);
        TextView txtNoiDung = row.findViewById(R.id.txtNoiDung);
        TextView txtThoiGian = row.findViewById(R.id.txtThoiGian);
        txtTen.setText(chat.getTen());
        txtNoiDung.setText(chat.getNoidung());
        txtThoiGian.setText(chat.getThoigian());
         txtNoiDung.startAnimation(anim_FadeIn);
        return row;
    }

    private void Animaition() {
        anim_FadeIn= AnimationUtils.loadAnimation(getContext(),R.anim.amin_fadein);
    }
}
