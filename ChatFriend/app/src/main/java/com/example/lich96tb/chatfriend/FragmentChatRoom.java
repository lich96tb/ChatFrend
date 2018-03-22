package com.example.lich96tb.chatfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.lich96tb.chatfriend.R.id.btnSen;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class FragmentChatRoom extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtSen;
    private ImageView imageSen;
    private TextView txtNoidung;
    private Animation anim_FadeIn;
    private ListView lv;
    private ArrayList<Chat> ds;
    private Chat_Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_chat_room, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        anhxa(view);
        hienthi();

        return view;
    }


    private void anhxa(View view) {
        edtSen = view.findViewById(R.id.edtSen);
        lv = view.findViewById(R.id.lv);
        imageSen = view.findViewById(btnSen);
        txtNoidung = view.findViewById(R.id.txtNoiDung);
        Animation1();
        ds = new ArrayList<>();
        adapter = new Chat_Adapter(getActivity(), R.layout.gui, ds);
        lv.setAdapter(adapter);
        imageSen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung = edtSen.getText().toString();
                if (noidung.equals("")) {
                    Toast.makeText(getActivity(), "chua co noi dung", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    String tg = String.valueOf(dateFormat.format(date));
                    Chat chat = new Chat(mAuth.getCurrentUser().getDisplayName(), noidung, tg, mAuth.getCurrentUser().getUid());
                    mDatabase.child("chatroom").push().setValue(chat);
                    edtSen.setText("");
                }

            }
        });
    }

    private void Animation1() {
        anim_FadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.amin_fadein);
    }

    private void hienthi() {
        mDatabase.child("chatroom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                ds.add(new Chat(chat.getTen(), chat.getNoidung(), chat.getThoigian(), chat.getId()));
                //  txtNoidung.startAnimation(anim_FadeIn);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
