package unisys.com.employeepayroll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import unisys.com.employeepayroll.R;
import unisys.com.employeepayroll.model.Employee;

/**
 * Created by Belal on 10/18/2017.
 */


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    private List<Employee> employeeList;

    //getting the context and product list with constructor
    public EmployeeAdapter(Context mCtx, List<Employee> employeeList) {
        this.mCtx = mCtx;
        this.employeeList = employeeList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        //LayoutInflater inflater = LayoutInflater.from(mCtx);
        //View view = inflater.inflate(R.layout.payroll_emp_item, null);
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payroll_emp_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Employee employee = employeeList.get(position);

        //binding the data with the viewholder views
        holder.textViewName.setText("Name: "+employee.getName());
        holder.textViewDob.setText("Dob: "+employee.getDob());
        holder.textViewType.setText("Employee Status: "+String.valueOf(employee.getEmpType()));


    }


    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewDob, textViewType;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDob = itemView.findViewById(R.id.textViewDob);
            textViewType = itemView.findViewById(R.id.textViewtype);
        }
    }
}