package com.example.a6tanvir;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etNumEq;
    Spinner spinnerOrderBy;
    Button btnStartDate;
    TextView tvStartDate;
    Button btnSubmit;

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH) + 1;
    int day = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumEq = findViewById(R.id.num_eq_et_id);
        spinnerOrderBy = findViewById(R.id.order_by_spinner_id);
        btnStartDate = findViewById(R.id.start_date_btn_id);
        tvStartDate = findViewById(R.id.start_date_tv_id);
        btnSubmit = findViewById(R.id.submit_btn_id);

        etNumEq.setInputType(InputType.TYPE_CLASS_NUMBER);

        ArrayAdapter<CharSequence> spinnerOrderByAdapter = ArrayAdapter.createFromResource(this,
                R.array.order_by_spinner, android.R.layout.simple_spinner_item);
        spinnerOrderByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrderBy.setAdapter(spinnerOrderByAdapter);

        tvStartDate.setText(year + "-" + month + "-" + day);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new MyDatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numEq = etNumEq.getText().toString();
                String orderBy = spinnerOrderBy.getSelectedItem().toString();
                String startDate = tvStartDate.getText().toString();

                String dateParts[] = startDate.split("-");
                StringBuffer modifiedStartDate = new StringBuffer();
                modifiedStartDate.append(dateParts[0]);
                modifiedStartDate.append("-");
                if (Integer.parseInt(dateParts[1]) < 10) {
                    modifiedStartDate.append("0" + dateParts[1]);
                } else {
                    modifiedStartDate.append(dateParts[1]);
                }
                modifiedStartDate.append("-");
                if (Integer.parseInt(dateParts[2]) < 10) {
                    modifiedStartDate.append("0" + dateParts[2]);
                } else {
                    modifiedStartDate.append(dateParts[2]);
                }

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("num_eq", numEq);
                intent.putExtra("order_by", orderBy.equals("date") ? "time" : orderBy);
                intent.putExtra("start_date", modifiedStartDate.toString());
                startActivity(intent);
            }
        });
    }
}
