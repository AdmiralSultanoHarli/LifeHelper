package com.example.sqlitetest.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitetest.Data;
import com.example.sqlitetest.Database.SqliteDatabase;
import com.example.sqlitetest.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Data> listData;
    private ArrayList<Data> mArrayList;

    private SqliteDatabase mDatabase;

    public DataAdapter(Context context, ArrayList<Data> listData) {
        this.context = context;
        this.listData = listData;
        this.mArrayList = listData;
        mDatabase = new SqliteDatabase(context);
    }

    @NonNull
    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_list_layout, viewGroup, false);
        DataViewHolder dataViewHolder = new DataViewHolder(v);
        return dataViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        final Data data = listData.get(position);

        holder.companyName.setText(data.getCompanyName());
        holder.email.setText(data.getEmail());
        holder.password.setText(data.getPassword());


        holder.editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTaskDialog(data);

            }
        });

        holder.deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteTaskDialog(data);

            }
        });

    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()){

                    listData = mArrayList;

                }else {

                    ArrayList<Data> filteredList = new ArrayList<>();
                    for (Data data : mArrayList){

                        if (data.getCompanyName().toLowerCase().contains(charString) || data.getCompanyName().contains(charString)
                                || data.getCompanyName().toUpperCase().contains(charString)){

                            filteredList.add(data);

                        }

                    }

                    listData = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listData;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                listData = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();

            }
        };

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    private void deleteTaskDialog(final Data data){

        /*LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_data_layout, null);*/

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Data?");
        //builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //delete row from database
                mDatabase.deleteData(data.getId());

                //refresh the activity page
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                return;

            }
        });

        builder.show();

    }

    private void editTaskDialog(final Data data){

        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_data_layout, null);

        final EditText companyNameField = subView.findViewById(R.id.enter_company_name);
        final EditText emailField = subView.findViewById(R.id.enter_email);
        final EditText passwordField = subView.findViewById(R.id.enter_password);

        if (data != null){

            companyNameField.setText(data.getCompanyName());
            emailField.setText(String.valueOf(data.getEmail()));
            passwordField.setText(data.getPassword());

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Data");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Edit Data", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String companyName = companyNameField.getText().toString();
                final String email = emailField.getText().toString();
                final String password = passwordField.getText().toString();
                Data dataKh = new Data(data.getId(), companyName, email, password);

                if (companyName.isEmpty() && email.isEmpty() && password.isEmpty()){

                    Toast.makeText(context, "Click delete if you want to delete it", Toast.LENGTH_SHORT).show();

                }else if(TextUtils.equals(companyName, data.getCompanyName()) && TextUtils.equals(email, data.getEmail())
                        && TextUtils.equals(password, data.getPassword())){

                    Toast.makeText(context, "You're not changing anything!", Toast.LENGTH_SHORT).show();

                }else {

                    //mDatabase.updateContacts(new Data(data.getId(), name, ph_no));
                    //Data data = new Data();
                    mDatabase.updateData(dataKh);
                    //Refresh Activity

                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(context, "Task Canceled", Toast.LENGTH_SHORT).show();

            }
        });

        builder.show();

    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        public TextView companyName, email, password;
        public ImageView deleteData;
        public ImageView editData;


        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.company_name);
            email = itemView.findViewById(R.id.email_name);
            password = itemView.findViewById(R.id.password);
            deleteData = itemView.findViewById(R.id.delete_data);
            editData = itemView.findViewById(R.id.edit_data);

        }
    }

}
