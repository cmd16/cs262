package edu.calvin.cs262.cmd38;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public int operand1 = 0;
    public int operand2 = 0;
    public String my_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the spinner.
        Spinner spinner = findViewById(R.id.operator_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operators_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void calculateResult(View view) {
        EditText value1 = findViewById(R.id.value_1_edit);
        operand1 = Integer.parseInt(value1.getText().toString());
        EditText value2 = findViewById(R.id.value_2_edit);
        operand2 = Integer.parseInt(value2.getText().toString());
        int result;
        switch (my_operator) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                result = operand1 / operand2;
                break;
            case "%":
                result = operand1 % operand2;
                break;
            default:
                Toast.makeText(getApplicationContext(), "Must select an operand!", Toast.LENGTH_SHORT).show();
                return;
        }
        TextView result_view = findViewById(R.id.result_view);
        result_view.setText(String.format("%d", result));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        my_operator = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        // save values
        EditText value1 = findViewById(R.id.value_1_edit);
        operand1 = Integer.parseInt(value1.getText().toString());
        EditText value2 = findViewById(R.id.value_2_edit);
        operand2 = Integer.parseInt(value2.getText().toString());
        Log.d("MainActivity", String.format("onPause. value1: %d, value2: %d, operator: %s", operand1, operand2, my_operator));
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("MainActivity", String.format("onResume. value1: %d, value2: %d, operator: %s", operand1, operand2, my_operator));
        EditText value1 = findViewById(R.id.value_1_edit);
        value1.setText(String.format("%d", operand1));
        EditText value2 = findViewById(R.id.value_2_edit);
        value2.setText(String.format("%d", operand2));
    }
}
