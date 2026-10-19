package com.mobleapp.exp_03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnTask1 = findViewById(R.id.btn_task1);
        Button btnTask2 = findViewById(R.id.btn_task2);
        Button btnTask3 = findViewById(R.id.btn_task3);

        btnTask1.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));

        btnTask2.setOnClickListener(v ->
                startActivity(new Intent(this, DialogActivity.class)));

        btnTask3.setOnClickListener(v ->
                startActivity(new Intent(this, MenuActivity.class)));
    }


}