package edu.calvin.cs262.cmd38;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private EditText mUrlInput;
    private TextView mPageSourceText;
    private Spinner protocolSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        protocolSpinner = findViewById(R.id.protocolSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protocol_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        protocolSpinner.setAdapter(adapter);

        mUrlInput = findViewById(R.id.urlInput);
        mPageSourceText = findViewById(R.id.pageSourceView);
    }

    public void searchUrl(View view) {

        // get the full url, including protocol
        String userString = mUrlInput.getText().toString();
        String queryString = protocolSpinner.getSelectedItem().toString();
        queryString += userString;

        // Hide the keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field is not empty, start a UrlLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && userString.length() != 0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            // Indicate to the user that we are executing their request
            mPageSourceText.setText(R.string.loading);
        } else {
            if (userString.length() == 0) {
                mPageSourceText.setText(R.string.no_search_term);
            } else {
                mPageSourceText.setText(R.string.no_network);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new UrlLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data == null) {
            mPageSourceText.setText(R.string.no_result);
        }
        mPageSourceText.setText(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Do nothing. Required by interface.
    }
}
