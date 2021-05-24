package sg.edu.rp.c346.id19045784.myreminderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    TextView tv;
    ArrayList<Reminder> alReminder;
    ReminderAdapter aa;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set the findview by id
        lv = findViewById(R.id.lv);
        tv = findViewById(R.id.textView);
        DBHelper dbReminder = new DBHelper(MainActivity.this);
        alReminder = new ArrayList<>();
        alReminder = dbReminder.getAllReminders();
        //check if Arraylist is empty if not add database value into arraylist
         if (!alReminder.isEmpty()) {
            aa = new ReminderAdapter(this, R.layout.row, alReminder);
            lv.setAdapter(aa);
         }
         else {
             //sets the Main activty view to the text view when list view is empty
             lv.setEmptyView(tv);
             aa = new ReminderAdapter(this, R.layout.row, alReminder);
             lv.setAdapter(aa);
         }

         lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 new AlertDialog.Builder(MainActivity.this)
                         .setTitle("Delete entry")
                         .setMessage("Are you sure you want to delete this entry?")

                         // Specifying a listener allows you to take an action before dismissing the dialog.
                         // The dialog is automatically dismissed when a dialog button is clicked.
                         .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 // Continue with delete operation
                                 DBHelper dbh = new DBHelper(MainActivity.this);
                                 dbh.deleteReminder(alReminder.get(position).getID());
                                 alReminder.clear();
                                 alReminder = dbh.getAllReminders();
                                 aa = new ReminderAdapter(MainActivity.this, R.layout.row, alReminder);
                                 lv.setAdapter(aa);
                                 dbh.close();

                             }
                         })

                         // A null listener allows the button to dismiss the dialog and take no further action.
                         .setNegativeButton(android.R.string.no, null)
                         .setIcon(android.R.drawable.ic_dialog_alert)
                         .show();

             }
         });

    }
    //inflate the main activity with the soft option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //check if user presses the 1st soft menu option
        if (item.getItemId() == R.id.AddSelection){
            Intent intent = new Intent(this,AddEvents.class);
            startActivityForResult(intent, 9);
        }
        else{

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK & requestCode == 9) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                alReminder = dbh.getAllReminders();
                aa = new ReminderAdapter(this, R.layout.row, alReminder);
                lv.setAdapter(aa);
                }

            if  (resultCode == RESULT_OK & requestCode == 10){
                if (alReminder.isEmpty()){
                    lv.setEmptyView(tv);
                }
                else {
                    DBHelper dbh = new DBHelper(MainActivity.this);
                    alReminder.clear();
                    alReminder = dbh.getAllReminders();
                    aa = new ReminderAdapter(MainActivity.this, R.layout.row, alReminder);
                    lv.setAdapter(aa);
                }
            }

    }
    }
