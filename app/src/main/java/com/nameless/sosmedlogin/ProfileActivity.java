//package com.nameless.sosmedlogin;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.facebook.AccessToken;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginManager;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.nameless.sosmedlogin.R;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class ProfileActivity extends AppCompatActivity {
//
//    private TextView userName;
//    private Button logout;
//    private GoogleSignInClient googleSignInClient;
//    private ImageView userPhoto;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        userName = findViewById(R.id.userName);
//        logout = findViewById(R.id.logout);
//        userPhoto = findViewById(R.id.userPhoto);
//
//        // Mengambil data dari Intent
//        String name = getIntent().getStringExtra("name");
//        String email = getIntent().getStringExtra("email");
//
//        if (name != null) {
//            userName.setText(name);
//        } else if (email != null) {
//            userName.setText(email);
//        } else {
//            // Mengambil data dari akun Google
//            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
//            if (googleSignInAccount != null){
//                String gName = googleSignInAccount.getDisplayName();
//                userName.setText(gName);
//            }
//        }
//
//        // Konfigurasi sign-in Google
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .build();
//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // Logout
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Logout dari sesi Firebase
//                FirebaseAuth.getInstance().signOut();
//
//                // Logout dari sesi Google
//                googleSignInClient.signOut().addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // Kembali ke MainActivity setelah logout
//                        finish();
//                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                    }
//                });
//
//                // Logout dari sesi Facebook
//                LoginManager.getInstance().logOut();
//            }
//        });
//
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        if (accessToken != null) {
//            GraphRequest request = GraphRequest.newMeRequest(
//                    accessToken,
//                    new GraphRequest.GraphJSONObjectCallback() {
//                        @Override
//                        public void onCompleted(JSONObject object, GraphResponse response) {
//                            try {
//                                if (object.has("picture")) {
//                                    JSONObject pictureObj = object.getJSONObject("picture");
//                                    if (pictureObj.has("data")) {
//                                        JSONObject dataObj = pictureObj.getJSONObject("data");
//                                        String url = dataObj.getString("url");
//                                        // Gunakan URL untuk memuat gambar profil ke ImageView
//                                        // menggunakan perpustakaan pemuat gambar seperti Picasso atau Glide
//                                        // Contoh:
//                                        Picasso.get().load(url).into(userPhoto);
//                                    }
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            // Kode aplikasi lainnya
//                        }
//                    });
//            Bundle parameters = new Bundle();
//            parameters.putString("fields", "id,name,link,picture.type(large)");
//            request.setParameters(parameters);
//            request.executeAsync();
//        }
//    }
//}




package com.nameless.sosmedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nameless.sosmedlogin.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName;
    private Button logout;
    private GoogleSignInClient googleSignInClient;
    private ImageView userPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);
        userPhoto = findViewById(R.id.userPhoto);

        // Mengambil data dari Intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        if (name != null) {
            userName.setText(name);
        } else if (email != null) {
            userName.setText(email);
        } else {
            // Mengambil data dari akun Google
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (googleSignInAccount != null){
                String gName = googleSignInAccount.getDisplayName();
                userName.setText(gName);

                if (googleSignInAccount.getPhotoUrl() != null) {
                    String photoUrl = googleSignInAccount.getPhotoUrl().toString();
                    Picasso.get().load(photoUrl).into(userPhoto);
                }
            }
        }

        // Konfigurasi sign-in Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout dari sesi Firebase
                FirebaseAuth.getInstance().signOut();

                // Logout dari sesi Google
                googleSignInClient.signOut().addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Kembali ke MainActivity setelah logout
                        finish();
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    }
                });

                // Logout dari sesi Facebook
                LoginManager.getInstance().logOut();
            }
        });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                if (object.has("picture")) {
                                    JSONObject pictureObj = object.getJSONObject("picture");
                                    if (pictureObj.has("data")) {
                                        JSONObject dataObj = pictureObj.getJSONObject("data");
                                        String url = dataObj.getString("url");
                                        // Gunakan URL untuk memuat gambar profil ke ImageView
                                        // menggunakan perpustakaan pemuat gambar seperti Picasso atau Glide
                                        // Contoh:
                                        Picasso.get().load(url).into(userPhoto);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Kode aplikasi lainnya
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}

