package sg.edu.rp.c346.fyp_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CleanerAdapter extends ArrayAdapter<Cleaner> {

    private TextView tvName, tvAge, tvGender, tvContact, tvLanguage;
    private Context parent_context;
    private ArrayList<Cleaner> cleanerList;

   public CleanerAdapter(Context context, int resource, ArrayList<Cleaner> cleaners){
       super(context, resource, cleaners);
       this.parent_context = context;
       cleanerList = cleaners;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent){
       LayoutInflater inflater = (LayoutInflater) parent_context
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View rowView = inflater.inflate(R.layout.row_cleaner_information, parent, false);
       tvName = rowView.findViewById(R.id.tvName);
       tvAge = rowView.findViewById(R.id.tvAge);
       tvGender = rowView.findViewById(R.id.tvGender);
       tvContact = rowView.findViewById(R.id.tvContact);
       tvLanguage = rowView.findViewById(R.id.tvLanguage);

       Cleaner currentCleaner = cleanerList.get(position);
       tvName.setText(currentCleaner.getName());
       tvAge.setText(currentCleaner.getAge());
       tvGender.setText(currentCleaner.getGender());
       tvContact.setText(currentCleaner.getContact());
       tvLanguage.setText(currentCleaner.getLanguage());

       return rowView;
   }
}
