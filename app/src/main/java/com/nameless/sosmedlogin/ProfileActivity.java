package com.nameless.sosmedlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class ProfileActivity extends AppCompatActivity {

    TextView userName;
    Button logout;
    GoogleSignInClient gClient;
    GoogleSignInOptions gOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        logout = findViewById(R.id.logout);

        // Facebook
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");

        if (name != null) {
            userName.setText(name);
        } else if (email != null) {
            userName.setText(email);
        } else {
            // firebase
            GoogleSignInAccount gAccount = GoogleSignIn.getLastSignedInAccount(this);
            if (gAccount != null){
                String gName = gAccount.getDisplayName();
                userName.setText(gName);
            }
        }

        //Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {
                    // Logout dari sesi Firebase
                    FirebaseAuth.getInstance().signOut();
                }

                GoogleSignInAccount googleAccount = GoogleSignIn.getLastSignedInAccount(ProfileActivity.this);
                if (googleAccount != null) {
                    // Logout dari sesi Google
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(ProfileActivity.this, gso);
                    googleSignInClient.signOut();
                }

                // Logout dari sesi Facebook
                LoginManager.getInstance().logOut();

                // Kembali ke MainActivity setelah logout
                finish();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });



    }
}