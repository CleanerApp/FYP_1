package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.rp.c346.fyp_1.Model.User;
import sg.edu.rp.c346.fyp_1.Model.UserProfile;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilePic;
    TextView profileName, profileEmail, profileCode;
    Button logout, changePassword;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.imageViewProfilePic);
        profileName = findViewById(R.id.tvProfileName);
        profileEmail = findViewById(R.id.tvProfileEmail);
        profileCode = findViewById(R.id.tvSecureCode);


        logout = findViewById(R.id.btnLogout);
        changePassword = findViewById(R.id.btnChangePassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText("Name: " + userProfile.getUserName());
                profileEmail.setText("Email: " + userProfile.getUserEmail());
                profileCode.setText("Secure Code: " + userProfile.getUserSecureCode());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }


        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, UpdatePassword.class));

            }
        });


    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }


}
