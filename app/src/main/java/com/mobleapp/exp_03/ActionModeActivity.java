package com.mobleapp.exp_03;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionModeActivity extends AppCompatActivity {
    private final ArrayList<String> names = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_action_mode);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.action_mode_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.action_mode_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            names.addAll(Arrays.asList("One", "Two", "Three", "Four", "Five", "Six"));
        } else {
            names.addAll(savedInstanceState.getStringArrayList("names"));
        }
        List<Map<String, Object>> data = new ArrayList<>();
        for (String name : names) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", name);
            item.put("icon", R.mipmap.ic_launcher);
            data.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.action_list_item,
                new String[]{"name", "icon"}, new int[]{R.id.action_name, R.id.action_icon});
        ListView listView = findViewById(R.id.action_list);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.action_empty));

        // ListView manages long-press entry, checked rows, and ActionMode lifecycle.
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                int count = listView.getCheckedItemCount();
                mode.setTitle(getResources().getQuantityString(R.plurals.selected_count, count, count));
                if (count == 0) mode.finish();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.action_mode_menu, menu);
                int count = listView.getCheckedItemCount();
                mode.setTitle(getResources().getQuantityString(R.plurals.selected_count, count, count));
                toolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() != R.id.action_delete) return false;
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                int count = listView.getCheckedItemCount();
                // Delete backwards so removing a row never changes remaining checked indices.
                for (int i = data.size() - 1; i >= 0; i--) {
                    if (checked.get(i)) {
                        data.remove(i);
                        names.remove(i);
                    }
                }
                mode.finish();
                adapter.notifyDataSetChanged();
                Toast.makeText(ActionModeActivity.this,
                        getString(R.string.deleted_count, count), Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // ListView clears checked states when its modal ActionMode finishes.
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList("names", names);
        super.onSaveInstanceState(outState);
    }
}
