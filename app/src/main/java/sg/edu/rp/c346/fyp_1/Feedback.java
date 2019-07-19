package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    RadioGroup rg1, rg2;
    CheckBox cbContact, cbFollow;
    EditText etName, etPhoneNo, etEmail, etFeedback;
    Button btnSubmit;
    String follow;
    String contact;

    FirebaseFirestore db;
    final Map<String, String> feedback = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        db = FirebaseFirestore.getInstance();

        cbContact = findViewById(R.id.cbContact);
        cbFollow = findViewById(R.id.cbFollow);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rg1 = findViewById(R.id.rg1);
                int selectedButtonId1 = rg1.getCheckedRadioButtonId();
                final RadioButton rb1 = findViewById(selectedButtonId1);

                rg2 = findViewById(R.id.rg2);
                int selectedButtonId2 = rg2.getCheckedRadioButtonId();
                RadioButton rb2 = findViewById(selectedButtonId2);

                etName = findViewById(R.id.etName);
                etPhoneNo = findViewById(R.id.etPhoneNo);
                etEmail = findViewById(R.id.etEmail);
                etFeedback = findViewById(R.id.etFeedback);

                String radio1 = rb1.getText().toString();
                String radio2 = rb2.getText().toString();
                feedback.put("About", radio1);
                //feedback.put("")


                db.collection("Feedback").add(feedback).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                        Toast.makeText(Feedback.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                        cbContact.setChecked(false);
                        cbFollow.setChecked(false);
                        etName.setText("");

                    }
                });

//
//                String[] info = {rb1.getText().toString(), rb2.getText().toString(), etName.getText().toString(), etPhoneNo.getText().toString(), etEmail.getText().toString(), etFeedback.getText().toString()};
//
//                Intent i = new Intent(Feedback.this, Summary.class);
//
//                Toast.makeText(Feedback.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
//
//                i.putExtra("info", info);
//
//                startActivity(i);

            }
        });



    }

    public void validate(){

    }


}
