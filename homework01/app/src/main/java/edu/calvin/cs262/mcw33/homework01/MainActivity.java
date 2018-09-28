package edu.calvin.cs262.mcw33.homework01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editText1;
    private EditText editText2;
    private String operator;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get a reference to the value1 and value2
        this.editText1 = findViewById(R.id.value1);
        this.editText2 = findViewById(R.id.value2);

        // Setup our spinner
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operators, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        this.resultText = findViewById(R.id.result);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save the values of value1 and value2
        savedInstanceState.putString("value1", this.editText1.getText().toString());
        savedInstanceState.putString("value2", this.editText2.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.editText1.setText(savedInstanceState.getString("value1"));
        this.editText2.setText(savedInstanceState.getString("value2"));
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        this.operator = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    public void calculateResult(View view) {
        double value1;
        double value2;
        try {
            value1 = Integer.parseInt(this.editText1.getText().toString());
            value2 = Integer.parseInt(this.editText2.getText().toString());
        } catch (NumberFormatException e) {
            this.resultText.setText(R.string.calculate_error);
            return;
        }

        double result = 0;
        switch (this.operator) {
            case "+": result = value1 + value2; break;
            case "-": result = value1 - value2; break;
            case "/": result = value1 / value2; break;
            case "*": result = value1 * value2; break;
            default: this.resultText.setText(R.string.calculate_error); break;
        }
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        this.resultText.setText(df.format(result));
    }
}
