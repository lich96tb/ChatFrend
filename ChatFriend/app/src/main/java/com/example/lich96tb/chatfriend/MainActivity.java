package com.example.lich96tb.chatfriend;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.lich96tb.chatfriend.AppKey.AppKey.PREFERENCES_NAME;
import static com.example.lich96tb.chatfriend.R.drawable.a;
import static com.example.lich96tb.chatfriend.R.drawable.f;
import static com.example.lich96tb.chatfriend.R.id.imageView;
import static com.example.lich96tb.chatfriend.R.menu.main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    ViewPager viewPager;
    TabLayout viewTab;
    ImageView img;
    TextView txtNavTen, txtNavEmail;
    Dialog dialog;
    DatabaseReference mDatabase;
    android.app.FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Chat Friend");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ///////////////


        ///////////////////////////
        View view = navigationView.getHeaderView(0);
        txtNavTen = view.findViewById(R.id.txtNavTen);
        txtNavEmail = view.findViewById(R.id.txtnavEmail);
        txtNavEmail.setText(mAuth.getCurrentUser().getEmail());
        txtNavTen.setText(mAuth.getCurrentUser().getDisplayName());
        img = view.findViewById(imageView);
        Picasso.with(this)
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .placeholder(a) // optional
                .error(a)         // optional
                .into(img);
        thuchien();
        /////////thong bao khi co tin nhan den
        thongbao();


    }

    private void thongbao() {
        mDatabase.child("TinNhan").child(mAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                TinNhan tinnhan = dataSnapshot.getValue(TinNhan.class);
                Toast.makeText(MainActivity.this, tinnhan.getTen() + "  nhắn tin cho bạn", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {
        // su dungj phims cungs
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
        //dong mo keo
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnDangXuat) {

            mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).child("online").setValue(0);
            mAuth.signOut();
            SharedPreferences share = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            editor.putString("mail", "");
            editor.putString("pass", "");
            editor.commit();
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentThayDoi fragmentThayDoi = new FragmentThayDoi();
            fragmentTransaction.add(R.id.bbb, fragmentThayDoi, "thaydoi");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_trangchu) {
            FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
            FragmentThayDoi fragmentthaydoi = (FragmentThayDoi) getFragmentManager().findFragmentByTag("thaydoi");

            FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
            FragmentGioiThieu fragmentGioiThieu = (FragmentGioiThieu) getFragmentManager().findFragmentByTag("gioithieu");
            FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
            Fragmen_doc_bao fragmen_doc_bao = (Fragmen_doc_bao) getFragmentManager().findFragmentByTag("docbao");
            FragmentTransaction fragmentTransaction4 = fragmentManager.beginTransaction();
            FragmentTinNhanRieng fragmentTinNhanRieng = (FragmentTinNhanRieng) getFragmentManager().findFragmentByTag("chatrieng");


            if (fragmentthaydoi != null) {
                fragmentTransaction1.remove(fragmentthaydoi);
                fragmentTransaction1.commit();
            } else if (fragmentGioiThieu != null) {
                fragmentTransaction2.remove(fragmentGioiThieu);
                fragmentTransaction2.commit();
            } else if (fragmen_doc_bao != null) {
                fragmentTransaction3.remove(fragmen_doc_bao);
                fragmentTransaction3.commit();
            } else if (fragmentTinNhanRieng != null) {
                fragmentTransaction4.remove(fragmentTinNhanRieng);
                fragmentTransaction4.commit();
            }

        } else if (id == R.id.nav_slideshow) {
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            FragmentGioiThieu fragmentGioiThieu = new FragmentGioiThieu();
            fragmentTransaction.add(R.id.bbb, fragmentGioiThieu, "gioithieu");
            fragmentTransaction.commit();


        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "cai dat", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_pas) {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Loading...");
            dialog.setCancelable(false);
            dialog.show();
            ///doi mat khau
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = mAuth.getCurrentUser().getEmail();

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "Kiểm tra mail để đổi mật khẩu", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "Xin thử lại sau", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void thuchien() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewTab = (TabLayout) findViewById(R.id.viewTab);
        FragmentManager manager = getSupportFragmentManager();
        FragmentAdapter adater = new FragmentAdapter(manager);
        viewPager.setAdapter(adater);
        viewTab.setupWithViewPager(viewPager);
        viewTab.setTabsFromPagerAdapter(adater);

    }


    @Override
    protected void onStart() {
        banBe1 banBe1 = new banBe1(String.valueOf(mAuth.getCurrentUser().getPhotoUrl()), mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getUid(), 1);
        mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).setValue(banBe1);
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).child("online").setValue(0);
        mAuth.signOut();
        super.onDestroy();
    }


}
