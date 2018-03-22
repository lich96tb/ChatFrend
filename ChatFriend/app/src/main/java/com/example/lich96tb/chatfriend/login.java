package com.example.lich96tb.chatfriend;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static com.example.lich96tb.chatfriend.AppKey.AppKey.PREFERENCES_NAME;

public class login extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail, edtPass;
    private Button btnDangNhap;
    private TextView txtDangKy, txtQuenMk;
    private FirebaseAuth mAuth;
    private Dialog dialog2, dialog;
    private CheckBox checkBox;


    String TAG = "DO TRONG LICH";
    SignInButton btnDangNhapGoogle;
    private final static int RC_SIGN_IN = 2;
    GoogleApiClient mGoogleApiClient;
    FirebaseAuth.AuthStateListener mAuthListener;

    //3. Khi khởi tạo hoạt động của bạn, hãy kiểm tra xem người dùng có đăng nhập không:
    @Override
    protected void onStart() {
        SharedPreferences share = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        edtEmail.setText(share.getString("mail", ""));
        edtPass.setText(share.getString("pass", ""));
        super.onStart();
        if (edtPass.getText().toString().trim().equals("")==false){
            checkBox.setChecked(true);
            dangnhap();
        }

        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        anhxa();
        DangNhapGoogle();
    }

    private void DangNhapGoogle() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent inten = new Intent(login.this, MainActivity.class);
                    startActivity(inten);
                }
            }
        };


/////1. xac thuc nguoi dung
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void anhxa() {
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        btnDangNhapGoogle = (SignInButton) findViewById(R.id.btnGoogle);
        edtEmail = (EditText) findViewById(R.id.edtMail);
        edtPass = (EditText) findViewById(R.id.edtPass);
        txtDangKy = (TextView) findViewById(R.id.txtDangKy);
        txtQuenMk = (TextView) findViewById(R.id.txtQuenMk);
        txtDangKy.setOnClickListener(this);
        txtQuenMk.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDangNhap:
                dangnhap();

                break;
            case R.id.txtDangKy:
                Intent intent = new Intent(this, DangKy.class);
                startActivity(intent);
                break;
            case R.id.btnGoogle:
                dialog = new ProgressDialog(this);
                dialog.setTitle("Loading...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                dialog.setCancelable(false);
                signIn();
                break;
            case R.id.txtQuenMk:
                dialog = new Dialog(this);
                dialog.setContentView(R.layout.dailogquenmatkhau);
                dialog.setTitle("ChatFriend");
                final EditText edtDailog = dialog.findViewById(R.id.dlEmail);
                Button btndlXacNhan = dialog.findViewById(R.id.dlXacNhan);
                dialog.show();
                btndlXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2 = new ProgressDialog(login.this);
                        dialog2.setTitle("Loading...");
                        dialog2.setCanceledOnTouchOutside(false);
                        dialog2.show();
                        dialog2.setCancelable(false);
                        ///doi mat khau
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        auth.sendPasswordResetEmail(edtDailog.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            dialog2.dismiss();
                                            Toast.makeText(login.this, "Kiểm tra mail để đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        } else {
                                            dialog2.dismiss();
                                            Toast.makeText(login.this, "Xin thử lại sau", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void dangnhap() {
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        if (email.equals("") || password.equals("") || email.equals(password)) {
            Toast.makeText(this, "Không để chống", Toast.LENGTH_SHORT).show();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Loading...");
            dialog.setCancelable(false);
            dialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                dialog.dismiss();
                                if (checkBox.isChecked()) {
                                    SharedPreferences share = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putString("mail", edtEmail.getText().toString().trim());
                                    editor.putString("pass", edtPass.getText().toString().trim());
                                    editor.commit();

                                } else {
                                    SharedPreferences share = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.putString("mail", "");
                                    editor.putString("pass", "");
                                    editor.commit();
                                }
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                dialog.dismiss();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(login.this, "that bai", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Toast.makeText(this, "du lieu " + data.getData().toString(), Toast.LENGTH_SHORT).show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                dialog.dismiss();
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                dialog.dismiss();
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "mAuth went wrong ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //4.Sau khi người dùng thành công đăng nhập, nhận được một mã thông báo ID từ
    // GoogleSignInAccountđối tượng, trao đổi nó cho một chứng chỉ Firebase,
    // và xác thực với Firebase sử dụng chứng chỉ Firebase:

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
