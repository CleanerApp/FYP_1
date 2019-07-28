package sg.edu.rp.c346.fyp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BookingAdapter extends ArrayAdapter<Bookings> {
    private TextView tvS, tvD, tvT, tvST, tvPC, tvC, tvE, tvCleaner, tvCost, tvPayment;
    private Context parent_context;
    private ArrayList<Bookings> bookingsList;

    public BookingAdapter(Context context, int resource, ArrayList<Bookings> bookings){
        super(context, resource, bookings);
        this.parent_context = context;
        bookingsList = bookings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) parent_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_bookings, parent, false);
        tvS = rowView.findViewById(R.id.textViewS);
        tvD = rowView.findViewById(R.id.textViewD);
        tvT = rowView.findViewById(R.id.textViewT);
        tvST = rowView.findViewById(R.id.textViewST);
        tvPC = rowView.findViewById(R.id.textViewPC);
        tvC = rowView.findViewById(R.id.textViewC);
        tvE = rowView.findViewById(R.id.textViewE);
        tvCleaner = rowView.findViewById(R.id.textViewCleaner);
        tvCost = rowView.findViewById(R.id.textViewCost);
        tvPayment = rowView.findViewById(R.id.textViewPayment);

        Bookings currentBooking = bookingsList.get(position);
        tvS.setText("Service Type: " + currentBooking.getService());
        tvD.setText("Date: " + currentBooking.getDate());
        tvT.setText("Time: " + currentBooking.getTime());
        tvST.setText("Address: " + currentBooking.getStreet());
        tvPC.setText("Postal Code: " + currentBooking.getPostal());
        tvC.setText("Contact: " + currentBooking.getContact());
        tvE.setText("Email: " + currentBooking.getEmail());
        tvCleaner.setText("Cleaner name: " + currentBooking.getCleaner());
        tvCost.setText("Cost: " + currentBooking.getCost());
        tvPayment.setText("Payment Type: " + currentBooking.getPayment());

        return rowView;
    }
}
