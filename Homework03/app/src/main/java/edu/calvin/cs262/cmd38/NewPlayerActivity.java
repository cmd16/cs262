package edu.calvin.cs262.cmd38;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NewPlayerActivity extends AppCompatActivity {
    public static final String EXTRA_EMAIL_REPLY =
            "edu.calvin.cs262.cmd38.EMAIL_REPLY";
    public static final String EXTRA_NAME_REPLY =
            "edu.calvin.cs262.cmd38.EMAIL_NAME";

    private EditText mEditPlayerNameView;
    private EditText mEditPlayerEmailView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        mEditPlayerNameView = findViewById(R.id.edit_player_name);
        mEditPlayerEmailView = findViewById(R.id.edit_player_email);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditPlayerEmailView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String playerName = mEditPlayerNameView.getText().toString();
                    String playerEmail = mEditPlayerEmailView.getText().toString();
                    replyIntent.putExtra(EXTRA_NAME_REPLY, playerName);
                    replyIntent.putExtra(EXTRA_EMAIL_REPLY, playerEmail);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
