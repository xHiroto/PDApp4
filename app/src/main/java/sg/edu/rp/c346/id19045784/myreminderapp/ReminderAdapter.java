package sg.edu.rp.c346.id19045784.myreminderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReminderAdapter extends ArrayAdapter {
    private ArrayList<Reminder> Reminder;
    private Context context;
    private TextView tvDesc, tvDate, tvID, tvEvent, tvTime;




    public ReminderAdapter(Context context, int resource, ArrayList<Reminder> objects){
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        Reminder = objects;
        // Store Context object as we would need to use it later
        this.context = context;

    }

    // getView() is the method ListView will call to get the
    //  View object every time ListView needs a row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        //  "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the TextView object
        tvEvent = rowView.findViewById(R.id.textEvent);
        tvDesc = rowView.findViewById(R.id.textDesc);
        tvDate = rowView.findViewById(R.id.textDate);
        tvTime = rowView.findViewById(R.id.textTime);


        // The parameter "position" is the index of the
        //  row ListView is requesting.
        //  We get back the food at the same index.
        Reminder currentEvent = Reminder.get(position);
        // Set the TextView to show the food
        tvEvent.setText(currentEvent.getTitle());
        tvDesc.setText(currentEvent.getDescription());
        tvDate.setText(currentEvent.getDate());
        tvTime.setText(currentEvent.getStartTime() + " -" + currentEvent.getEndTime());




        // Return the nicely done up View to the ListView
        return rowView;


    }

}
