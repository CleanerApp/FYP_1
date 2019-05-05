package sg.edu.rp.c346.fyp_1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.rp.c346.fyp_1.Model.User;

public class ProfileActivity extends AppCompatActivity {

    ImageView profilePic;
    TextView profileName, profileEmail;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.imageViewProfilePic);
        profileName = findViewById(R.id.tvProfileName);
        profileEmail = findViewById(R.id.tvProfileEmail);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                profileName.setText("Name: " + userProfile.getUserName());
                profileEmail.setText("Email: " + userProfile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
