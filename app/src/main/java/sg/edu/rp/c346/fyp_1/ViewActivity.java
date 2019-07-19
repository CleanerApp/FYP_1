package sg.edu.rp.c346.fyp_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {
    ImageView iv;
    TextView tvName, tvCost, tvDetail;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent in = getIntent();
        service = (Service) in.getSerializableExtra("select");

        tvName = findViewById(R.id.textViewName);
        tvCost = findViewById(R.id.textViewCost);
        tvDetail = findViewById(R.id.textViewDetail);

        tvName.setText(service.getName());
        tvCost.setText("Cost: S$" + service.getCost());
        tvDetail.setText("Description: \n" + service.getDetail());

    }

}
