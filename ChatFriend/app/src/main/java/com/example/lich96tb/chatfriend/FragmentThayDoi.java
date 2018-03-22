package com.example.lich96tb.chatfriend;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lich96tb on 11/27/2017.
 */

public class FragmentThayDoi extends Fragment implements View.OnClickListener {
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;
    int REQUEST_CODE = 11;
    int t = 0;
    ImageView imgCaNhan;
    TextView txtEmailCaNhan;
    EditText edtTenCaNhan;
    Button btnSuaCaNhan;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Uri downloadUrl;

    FirebaseUser user;
    String ten;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_thay_doi_thong_tin_ca_nhan,container,false);
        super.onCreate(savedInstanceState);
        //thu muc sto cuar fire
        storageRef = storage.getReferenceFromUrl("gs://chatfriend-b436f.appspot.com");
        mAuth = FirebaseAuth.getInstance();
        imgCaNhan = (ImageView) view.findViewById(R.id.imgCaNhan);
        edtTenCaNhan = (EditText) view.findViewById(R.id.edtTenCaNhan);
        txtEmailCaNhan = (TextView) view.findViewById(R.id.txtEmail);
        btnSuaCaNhan = (Button) view.findViewById(R.id.btnChinhSua);
        truyendulieu();
        imgCaNhan.setOnClickListener(this);
        return view;
    }
    private void truyendulieu() {
        // Toast.makeText(activity, ""+mAuth.getCurrentUser().getPhotoUrl(), Toast.LENGTH_SHORT).show();
        Picasso.with(getActivity())
                .load(mAuth.getCurrentUser().getPhotoUrl())
                .placeholder(R.drawable.a) // optional
                .error(R.drawable.f)         // optional
                .into(imgCaNhan);
        txtEmailCaNhan.setText(mAuth.getCurrentUser().getEmail());
        edtTenCaNhan.setText(mAuth.getCurrentUser().getDisplayName() + "");
        btnSuaCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thuchienthaydoi();
            }
        });

    }

    private void thuchienthaydoi() {
        ten = edtTenCaNhan.getText().toString();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (t != 0) {
            save();
        } else {
            save2();
        }


    }

    private void save2() {
        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("loading");
        progressDialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(ten)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //doi ten thanh cong
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            banBe1 banBe1 = new banBe1(mAuth.getCurrentUser().getPhotoUrl() + "", ten, mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getUid(),1);
                            mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).setValue(banBe1);
                            Toast.makeText(getActivity(), "doi ten thanh cong", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            FragmentThayDoi fragmentThayDoi= (FragmentThayDoi) getFragmentManager().findFragmentByTag("thaydoi");
                            if(fragmentThayDoi!=null){
                                fragmentTransaction.remove(fragmentThayDoi);
                                fragmentTransaction.commit();
                            }

                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgCaNhan:
                t = 1;
                Intent inten = new Intent(Intent.ACTION_PICK);
                inten.setType("image/*");
                startActivityForResult(inten, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getActivity().getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            imgCaNhan.setImageBitmap(bitmap);
        }

    }

    private void save() {
        //displaying a progress dialog while upload is going on
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading");
        progressDialog.show();
        //dat ten cho anh
        Calendar calendar = Calendar.getInstance();
        StorageReference mountainsRef = storageRef.child("image" + calendar.getTimeInMillis() + ".png");


        // Get the data from an ImageView as bytes
        imgCaNhan.setDrawingCacheEnabled(true);
        imgCaNhan.buildDrawingCache();
        Bitmap bitmap = imgCaNhan.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(ten)

                        .setPhotoUri(downloadUrl)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //doi ten thanh cong
                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    banBe1 banBe1 = new banBe1(mAuth.getCurrentUser().getPhotoUrl() + "", ten, mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getUid(),1);
                                    mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).setValue(banBe1);
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "thay doi thanh cong", Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager=getFragmentManager();
                                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                                    FragmentThayDoi fragmentThayDoi= (FragmentThayDoi) getFragmentManager().findFragmentByTag("thaydoi");
                                    if(fragmentThayDoi!=null){
                                        fragmentTransaction.remove(fragmentThayDoi);
                                        fragmentTransaction.commit();
                                    }

                                }
                            }
                        });
            }
        });
    }
    
}
