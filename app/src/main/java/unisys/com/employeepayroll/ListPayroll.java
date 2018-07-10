package unisys.com.employeepayroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import unisys.com.employeepayroll.adapter.EmployeeAdapter;
import unisys.com.employeepayroll.dbhelper.DatabaseHelper;
import unisys.com.employeepayroll.model.Employee;
import unisys.com.employeepayroll.views.RecyclerItemClickListener;

public class ListPayroll extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<Employee> empolyeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_payroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(ListPayroll.this,EmployeeDetail.class);
                        intent.putExtra("detail",empolyeeList.get(position));
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        empolyeeList = new ArrayList<Employee>();
        empolyeeList.addAll(new DatabaseHelper(this).getAllEmployess());
        //creating recyclerview adapter
        EmployeeAdapter adapter = new EmployeeAdapter(this, empolyeeList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (empolyeeList.isEmpty())
        {
            Toast.makeText(this, "No Payroll found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
