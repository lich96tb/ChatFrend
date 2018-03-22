package com.example.lich96tb.chatfriend;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by lich96tb on 8/2/2017.
 */

public class FragmentListPriend extends Fragment {
    ListView lv1;
    ArrayList<banBe1> ds;
    banBeAdapter adapter1;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_chat_friend, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lv1 = view.findViewById(R.id.lvFriend);
        ds = new ArrayList<>();
       adapter1 = new banBeAdapter(getActivity(), R.layout.item, ds);
       lv1.setAdapter(adapter1);
        ds.clear();
        hienthi();

 lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
         android.app.FragmentManager fragmentManager=getActivity().getFragmentManager();
         FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
         FragmentTinNhanRieng fragmentTinNhanRieng=new FragmentTinNhanRieng();
         Bundle bundle=new Bundle();
         bundle.putString("id",ds.get(i).getId());
         fragmentTinNhanRieng.setArguments(bundle);
         fragmentTransaction.add(R.id.bbb,fragmentTinNhanRieng,"chatrieng");
         fragmentTransaction.commit();
     }
 });

        return view;
    }



    private void hienthi() {
        mDatabase.child("listBanbe").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                banBe1 banbe1 = dataSnapshot.getValue(banBe1.class);
                ds.add(new banBe1(banbe1.getAnh(),banbe1.getTen(),banbe1.getMail(),banbe1.getId(),banbe1.getOnline()));
                //  txtNoidung.startAnimation(anim_FadeIn);
                adapter1.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                banBe1 banbe1 = dataSnapshot.getValue(banBe1.class);
                if(banbe1.getOnline()==1){
                    Toast.makeText(getActivity(),banbe1.getTen()+" online", Toast.LENGTH_SHORT).show();
                }
                ds.clear();
                mDatabase.child("listBanbe").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        banBe1 banbe1 = dataSnapshot.getValue(banBe1.class);
                        ds.add(new banBe1(banbe1.getAnh(),banbe1.getTen(),banbe1.getMail(),banbe1.getId(),banbe1.getOnline()));
                        //  txtNoidung.startAnimation(anim_FadeIn);
                        adapter1.notifyDataSetChanged();
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
