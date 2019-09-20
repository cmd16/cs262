package edu.calvin.cs262.cmd38;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
    }

    /**
     * Shows the toast message (currently set to "Hello Toast!").
     *
     * @param view the View object that you want to show the toast message in
     */
    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Increments the counter (mCount) and updates the display in mShowCount to show the new count.
     *
     * @param view the View object that this method is called on
     */
    public void countUp(View view) {
        ++mCount;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }
    }
}
