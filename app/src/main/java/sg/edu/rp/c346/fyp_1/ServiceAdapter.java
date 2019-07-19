package sg.edu.rp.c346.fyp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ServiceAdapter extends ArrayAdapter<Service> {
    private TextView tvName;
    private Context parent_context;
    private ArrayList<Service> serviceList;

    public ServiceAdapter(Context context, int resource, ArrayList<Service> services){
        super(context, resource, services);
        this.parent_context = context;
        serviceList = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) parent_context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_service, parent, false);
        tvName = rowView.findViewById(R.id.textViewName);

        Service currentService = serviceList.get(position);
        tvName.setText(currentService.getName());
        return rowView;
    }
}
