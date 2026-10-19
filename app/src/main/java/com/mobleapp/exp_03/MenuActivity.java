package com.mobleapp.exp_03;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvTest = findViewById(R.id.tv_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.size_small) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            return true;
        } else if (id == R.id.size_medium) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            return true;
        } else if (id == R.id.size_large) {
            tvTest.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            return true;
        } else if (id == R.id.menu_normal) {
            Toast.makeText(this, "你点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.color_red) {
            tvTest.setTextColor(Color.RED);
            return true;
        } else if (id == R.id.color_black) {
            tvTest.setTextColor(Color.BLACK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}