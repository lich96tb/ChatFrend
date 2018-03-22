package com.example.lich96tb.chatfriend;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

/**
 * Created by lich96tb on 11/27/2017.
 */

public class FragmentTinNhanRieng extends Fragment {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    String idKhach, idChu;
    ChatRiengAdapter adapter;
    ArrayList<Chat> ds;
    ListView lv2;
    EditText edt2;
    ImageView btn2;
    String tong1, tong2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fr_tinnhanrieng,container,false);
        lv2 = (ListView) view.findViewById(R.id.lv2);
        edt2 = (EditText) view.findViewById(R.id.edtSen2);
        btn2 = (ImageView) view.findViewById(R.id.btnSen2);
        ds = new ArrayList<>();
        adapter=new ChatRiengAdapter(getActivity(),R.layout.itemchatrieng,ds);
        lv2.setAdapter(adapter);


        ds.clear();
        //khoi tao firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        //nhan du lieu tu chatFriend
        Bundle bundle=getArguments();
        idKhach = bundle.getString("id");
        idChu = mAuth.getCurrentUser().getUid();
        tong1 = idChu + idKhach;
        tong2 = idKhach + idChu;
        hienthi();









        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String tg = String.valueOf(dateFormat.format(date));
                //them du lieu vao
                String nd = edt2.getText().toString();
                Chat chat = new Chat(mAuth.getCurrentUser().getDisplayName(), nd, tg, mAuth.getCurrentUser().getUid());
                mDatabase.child("chatrieng").child(tong1).push().setValue(chat);
                mDatabase.child("chatrieng").child(tong2).push().setValue(chat);
                edt2.setText("");

                TinNhan tinNhan = new TinNhan(String.valueOf(mAuth.getCurrentUser().getPhotoUrl()), mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getUid(),nd);
                mDatabase.child("TinNhan").child(idKhach).child(mAuth.getCurrentUser().getUid()).setValue(tinNhan);


            }
        });
        return view;
    }

    private void hienthi() {
        //hien thi chat
        mDatabase.child("chatrieng").child(tong1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                ds.add(new Chat(chat.getTen(), chat.getNoidung(), chat.getThoigian(), chat.getId()));
                //  txtNoidung.startAnimation(anim_FadeIn);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(getActivity(), dataSnapshot.child("noidung").getValue().toString(), Toast.LENGTH_SHORT).show();
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
