package com.mobleapp.exp_03;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private final String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private final int[] images = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant};
    private final ArrayList<Integer> animalTypes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycler_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.recycler_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            for (int i = 0; i < names.length; i++) animalTypes.add(i);
        } else {
            animalTypes.addAll(savedInstanceState.getIntegerArrayList("animalTypes"));
        }
        List<Animal> animals = new ArrayList<>();
        for (int type : animalTypes) animals.add(new Animal(names[type], images[type]));
        AnimalAdapter adapter = new AnimalAdapter(animals);
        RecyclerView recyclerView = findViewById(R.id.animal_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        TextView count = findViewById(R.id.animal_count);
        count.setText(getString(R.string.animal_count, animals.size()));

        findViewById(R.id.btn_add_animal).setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle(R.string.choose_animal)
                        .setItems(names, (dialog, which) -> {
                            int position = animals.size();
                            animals.add(new Animal(names[which], images[which]));
                            animalTypes.add(which);
                            adapter.notifyItemInserted(position);
                            count.setText(getString(R.string.animal_count, animals.size()));
                            recyclerView.scrollToPosition(position);
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList("animalTypes", animalTypes);
        super.onSaveInstanceState(outState);
    }
}
