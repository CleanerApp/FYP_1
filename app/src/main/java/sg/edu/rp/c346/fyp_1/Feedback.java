package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    String follow, contact;
    String name, num, email;

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
                follow = cbFollow.getText().toString();
                feedback.put("About", radio1);
                feedback.put("Rate", radio2);

                if (validate()){
                    feedback.put("Name", etName.getText().toString());
                    feedback.put("Phone No", etPhoneNo.getText().toString());
                    feedback.put("Email", etEmail.getText().toString());
                    feedback.put("Feedback", etFeedback.getText().toString());


                    db.collection("Feedback").add(feedback).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(Feedback.this, "Submitted Successfully", Toast.LENGTH_LONG).show();
                            cbContact.setChecked(false);
                            cbFollow.setChecked(false);
                            etName.setText("");
                            etPhoneNo.setText("");
                            etEmail.setText("");
                            etFeedback.setText("");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(Feedback.this, "Submitted Failed", Toast.LENGTH_LONG).show();
                        }
                    });

                }



            }
        });



    }

    private Boolean validate(){
        Boolean result = false;

         name = etName.getText().toString();
         num = etPhoneNo.getText().toString();
         email = etEmail.getText().toString();
        //String fb = etFeedback.getText().toString();

        if (!validateName() | !validatePhoneNo() | !validateEmail()){

        } else {
            result = true;
        }

        return result;
    }

    public boolean validateName(){
        String enterName = etName.getText().toString().trim();

        if (enterName.isEmpty()){
            etName.setError("Field can't be empty");
            return false;
        } else {
            etName.setError(null);
            return true;
        }

    }

    public boolean validatePhoneNo(){
        String enterPhoneNo = etPhoneNo.getText().toString().trim();

        if (enterPhoneNo.isEmpty()){
            etPhoneNo.setError("Field can;t be empty");
            return false;
        } else {
            etPhoneNo.setError(null);
            return true;
        }
    }


    public boolean validateEmail(){
        String enterEmail = etEmail.getText().toString().trim();

        if (enterEmail.isEmpty()) {
            etEmail.setError("Field can't be empty");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(Feedback.this, Home.class));
                return true;
            case R.id.sign_in:
                startActivity(new Intent(Feedback.this, MainActivity.class));
                return true;
            case R.id.contact_us:
                startActivity(new Intent(Feedback.this, contactus.class));
                return true;
            case R.id.view_service:
                startActivity(new Intent(Feedback.this, ServiceList.class));
                return true;
            case R.id.view_bookings:
                startActivity(new Intent(Feedback.this, BookingsActivity.class));
                return true;
            case R.id.view_cleaner:
                startActivity(new Intent(Feedback.this, CleanerInformation.class));
                return true;
            case R.id.view_password:
                startActivity(new Intent(Feedback.this, UpdatePassword.class));
                return true;
            case R.id.view_logout:
                startActivity(new Intent(Feedback.this, Home.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
