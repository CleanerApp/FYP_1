package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {

    RadioGroup rg1, rg2;
    CheckBox cbContact, cbFollow;
    EditText etName, etPhoneNo, etEmail, etFeedback;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        cbContact = findViewById(R.id.cbContact);
        cbFollow = findViewById(R.id.cbFollow);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rg1 = findViewById(R.id.rg1);
                int selectedButtonId1 = rg1.getCheckedRadioButtonId();
                RadioButton rb1 = findViewById(selectedButtonId1);

                rg2 = findViewById(R.id.rg2);
                int selectedButtonId2 = rg2.getCheckedRadioButtonId();
                RadioButton rb2 = findViewById(selectedButtonId2);

                etName = findViewById(R.id.etName);
                etPhoneNo = findViewById(R.id.etPhoneNo);
                etEmail = findViewById(R.id.etEmail);
                etFeedback = findViewById(R.id.etFeedback);

                String[] info = {rb1.getText().toString(), rb2.getText().toString(), etName.getText().toString(), etPhoneNo.getText().toString(), etEmail.getText().toString(), etFeedback.getText().toString()};

                Intent i = new Intent(Feedback.this, Summary.class);

                Toast.makeText(Feedback.this, "Submitted Successfully", Toast.LENGTH_LONG).show();

                i.putExtra("info", info);

                startActivity(i);

            }
        });


    }


}
