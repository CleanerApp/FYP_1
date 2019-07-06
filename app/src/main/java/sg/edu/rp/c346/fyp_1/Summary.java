package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    TextView tvAbout, tvRate, tvName, tvPhoneNo, tvEmail, tvFeedback;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        tvAbout = findViewById(R.id.tvAbout);
        tvRate = findViewById(R.id.tvRate);
        tvName = findViewById(R.id.tvName);
        tvPhoneNo = findViewById(R.id.tvPhoneNo);
        tvEmail = findViewById(R.id.tvEmail);
        tvFeedback = findViewById(R.id.tvFeedback);

        Intent i = getIntent();

        String[] info = i.getStringArrayExtra("info");

        String About = getString(R.string.checkbox);
        String Rate  = getString(R.string.rate);
        String Provide = getString(R.string.provide);

        tvAbout.setText(About + " " +info[0]);
        tvRate.setText(Rate + " " + info[1]);
        tvName.setText("Name: " + info[2]);
        tvPhoneNo.setText("Phone Number: " + info[3]);
        tvEmail.setText("Email: " + info[4]);
        tvFeedback.setText(Provide + " " + info[5]);


        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Summary.this, Home.class);
                startActivity(i);
            }
        });

    }
}
