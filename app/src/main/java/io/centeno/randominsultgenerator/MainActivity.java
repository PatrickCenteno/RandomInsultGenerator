package io.centeno.randominsultgenerator;

import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    Snackbar snackbar;
    EditText nameText;
    TextView generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (isOnline()) {
            nameText = (EditText) findViewById(R.id.name_edit_text);
            generate = (TextView) findViewById(R.id.generate);
            showDialog();
        } else {
            Log.e(TAG, "not online");
            // Creates snackbar and displays it
            createAndShowSnackbar();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks if internet connection is present
     * Must call this with every click so we can display the snackbar
     * @return boolean true or false if connected
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showDialog() {
        DialogFragment dialogBox = new InstructionsDialogBox();
        dialogBox.show(getFragmentManager(), "InstructionsDialogBox");
    }


    private void createAndShowSnackbar() {
        View rootView = findViewById(R.id.main_layout);
        snackbar = Snackbar.make(rootView,
                "Oops! Please check your Internet Connection",
                Snackbar.LENGTH_INDEFINITE)

                .setActionTextColor(getResources().getColor(R.color.white))
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }
}
