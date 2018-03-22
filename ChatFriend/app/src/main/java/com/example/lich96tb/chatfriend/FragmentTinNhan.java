package com.example.lich96tb.chatfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class FragmentTinNhan extends Fragment {

    ListView lvtn;
    TinNhanAdapter adapter;
    ArrayList<TinNhan> ds;
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    int online;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_chat_rieng, container, false);
        lvtn = view.findViewById(R.id.lvTinNhan);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ds = new ArrayList<>();
        adapter = new TinNhanAdapter(getActivity(), R.layout.itemtinnhan, ds);
        lvtn.setAdapter(adapter);
        mDatabase.child("TinNhan").child(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TinNhan tinNhan = dataSnapshot.getValue(TinNhan.class);
                ds.add(new TinNhan(tinNhan.getAnh(), tinNhan.getTen(), tinNhan.getId(), tinNhan.getNoidung(), online));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                loadlai();
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

        lvtn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent inten = new Intent(getContext(), chatRieng.class);
//                inten.putExtra("id", ds.get(i).getId());
//                startActivity(inten);
                android.app.FragmentManager fragmentManager=getActivity().getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
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

    private void loadlai() {
        ds.clear();
        mDatabase.child("TinNhan").child(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TinNhan tinNhan = dataSnapshot.getValue(TinNhan.class);


                ds.add(new TinNhan(tinNhan.getAnh(), tinNhan.getTen(), tinNhan.getId(), tinNhan.getNoidung(), online));
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
