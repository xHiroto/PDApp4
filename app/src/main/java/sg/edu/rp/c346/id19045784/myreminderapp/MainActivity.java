package sg.edu.rp.c346.id19045784.myreminderapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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

    int code = 1;

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
        //check if Arraylist is empty if not add database value into arraylist
         if (!alReminder.isEmpty()) {
            alReminder = dbReminder.getAllReminders();
            aa = new ReminderAdapter(this, R.layout.row, alReminder);
            lv.setAdapter(aa);
         }
         else {
             //sets the Main activty view to the text view when list view is empty
             lv.setEmptyView(tv);
             aa = new ReminderAdapter(this, R.layout.row, alReminder);
             lv.setAdapter(aa);
         }

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
            startActivityForResult(intent, code);
        }
        else{

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Integer id = data.getIntExtra("id", 0);
                    String event = data.getStringExtra("event");
                    String desc = data.getStringExtra("desc");
                    String date = data.getStringExtra("date");
                    String timeStart = data.getStringExtra("timeStart");
                    String timeEnd = data.getStringExtra("timeEnd");

                    alReminder.add(new Reminder(id, event, desc, date, timeStart, timeEnd));
                    aa.notifyDataSetChanged();
                }

        }
    }
}