package it.bancon.wascheduler.main;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.bancon.wascheduler.MainActivity;
import it.bancon.wascheduler.NewScheduleActivity;
import it.bancon.wascheduler.R;

public class MainAppActivity extends AppCompatActivity {

    FloatingActionButton FABAddScheduleProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        FABAddScheduleProgram = findViewById(R.id.addScheduleFAB);

        FABAddScheduleProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewScheduleActivity.class);
                startActivity(intent);

            }
        });


    }
}