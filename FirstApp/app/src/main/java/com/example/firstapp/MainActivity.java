package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editTextAmount=findViewById(R.id.editTextAmount);
        EditText editTextNumber=findViewById(R.id.editTextAmount1);
        Button btnCompute=findViewById(R.id.buttonCompute);
        TextView textViewResult=findViewById(R.id.textViewResult);
        ListView listViewResult=findViewById(R.id.listViewResults);
        List<String> data=new ArrayList<>();
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);

        listViewResult.setAdapter(stringArrayAdapter);
        btnCompute.setOnClickListener((view)->{
            double amount=Double.parseDouble(editTextAmount.getText().toString());
            double number=Double.parseDouble(editTextNumber.getText().toString());
            double result=amount*number;
            textViewResult.setText(String.valueOf(result));
            data.add(amount+"*"+number+"=>"+result);
            stringArrayAdapter.notifyDataSetChanged();
            editTextAmount.setText("");
            editTextNumber.setText("");
        });
    }
}