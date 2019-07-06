package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import sg.edu.rp.c346.fyp_1.Model.UserProfile;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextView profileName, profileEmail, profileCode;
    Button logout, changePassword;

    FirebaseDatabase firebaseDatabase;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String name, email, password, securecode;

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
        //db = FirebaseFirestore.getInstance();

        final String user_email = profileEmail.getText().toString().trim();
        final String user_name = profileName.getText().toString().trim();
        final String user_code = profileCode.getText().toString().trim();
        final UserProfile userProfile = new UserProfile(email, name, password, securecode);

//        DocumentReference docRef = db.collection("users").document(user_email);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()){
//                        profileName.setText("Name:" + userProfile.getUserName());
//                    }
//                }
//            }
//        });

//        docRef.collection("users").document(user_email).set(userProfile).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                profileName.setText("Name: " + userProfile.getUserName());
//                profileEmail.setText("Email: " + userProfile.getUserEmail());
//                profileCode.setText("Secure Code: " + userProfile.getUserSecureCode());
//
//            }
//        });



//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//
//        DatabaseReference databaseReference = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                profileName.setText("Name: " + userProfile.getUserName());
//                profileEmail.setText("Email: " + userProfile.getUserEmail());
//                profileCode.setText("Secure Code: " + userProfile.getUserSecureCode());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

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

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }

}
