package com.mirz.lifehelper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.mirz.lifehelper.Adapter.DataAdapter;
import com.mirz.lifehelper.Data;
import com.mirz.lifehelper.Database.SqliteDatabase;
import com.example.sqlitetest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    private ArrayList<Data> allData = new ArrayList<>();
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout fLayout = findViewById(R.id.activity_to_do);

        RecyclerView dataView = findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        dataView.setLayoutManager(linearLayoutManager);
        dataView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        allData = mDatabase.listData();

        if (allData.size() > 0){

            dataView.setVisibility(View.VISIBLE);
            mAdapter = new DataAdapter(this, allData);
            dataView.setAdapter(mAdapter);

        }else {

            dataView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no data in database, start adding now", Toast.LENGTH_SHORT).show();

        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTaskDialog();

            }
        });

    }

    private void addTaskDialog(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_data_layout, null);

        final EditText companyNameField = subView.findViewById(R.id.enter_company_name);
        final EditText emailField = subView.findViewById(R.id.enter_email);
        final EditText passwordField = subView.findViewById(R.id.enter_password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Data");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Add Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String companyName = companyNameField.getText().toString();
                final String email = emailField.getText().toString();
                final String password = passwordField.getText().toString();

                if (TextUtils.isEmpty(companyName)){

                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_SHORT).show();

                }else {

                    Data data = new Data(companyName, email, password);
                    mDatabase.addData(data);

                    finish();
                    startActivity(getIntent());

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(MainActivity.this, "Task Canceled", Toast.LENGTH_SHORT).show();

            }
        });

        builder.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mDatabase != null){

            mDatabase.close();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;

    }

    private void search(SearchView searchView){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (mAdapter != null){

                    mAdapter.getFilter().filter(newText);

                }

                return true;
            }
        });

    }

}
