package com.example.lich96tb.chatfriend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKy extends AppCompatActivity {
    EditText edtTen1, edtPass1, edtPass11, edtEmail1;
    private Button btnDangKy1, btnHuy1;
    DatabaseReference mDatabase;
    String ten;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        anhxa();
        getSupportActionBar().hide();
    }

    private void anhxa() {
        edtTen1 = (EditText) findViewById(R.id.edtTen1);
        edtEmail1 = (EditText) findViewById(R.id.edtEmail1);
        edtPass1 = (EditText) findViewById(R.id.edtPass1);
        edtPass11 = (EditText) findViewById(R.id.edtPass11);
        btnDangKy1 = (Button) findViewById(R.id.btnDangKy1);
        btnHuy1 = (Button) findViewById(R.id.btnHuy1);
        btnHuy1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent inten = new Intent(DangKy.this, login.class);
                startActivity(inten);
            }
        });
        btnDangKy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangky();
            }
        });
    }


    private void dangky() {
        String email = edtEmail1.getText().toString();
        String password = edtPass1.getText().toString();
        String password11 = edtPass11.getText().toString();
        ten = edtTen1.getText().toString();
        if (password.equals(password11)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(ten)
                                        .setPhotoUri(Uri.parse("https://i.pinimg.com/236x/0e/2e/3a/0e2e3a0b92373f2c9d2dd2302e4d05d2--icons.jpg"))
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                                    banBe1 banBe1 = new banBe1(String.valueOf(mAuth.getCurrentUser().getPhotoUrl()), mAuth.getCurrentUser().getDisplayName(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getUid(),0);
                                                    mDatabase.child("listBanbe").child(mAuth.getCurrentUser().getUid()).setValue(banBe1);
                                                    Toast.makeText(DangKy.this, "dang ky thanh cong", Toast.LENGTH_SHORT).show();
                                                    Intent inten = new Intent(DangKy.this, login.class);
                                                    startActivity(inten);
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(DangKy.this, "đăng ký lỗi", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        } else {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
    }
}