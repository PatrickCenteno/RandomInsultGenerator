package io.centeno.randominsultgenerator;

import android.app.DialogFragment;
import android.app.DownloadManager;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private String [] withName;
    private String [] withNoName;

    Snackbar snackbar;
    EditText nameText;
    EditText insulteeText;
    TextView generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if online before anything is done
        if (isOnline()) {
            // Get Insults from server
            getInsults();

            // Instructions for app
            showDialog();

            // Instantiate the Views for activity
            instantiateViews();
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

    /**
     * Shows the Instructions Dialog Box
     */
    private void showDialog() {
        DialogFragment dialogBox = new InstructionsDialogBox();
        dialogBox.show(getFragmentManager(), "InstructionsDialogBox");
    }


    /**
     * Creates and shows the Snackbar
     * that displays the message when internet is out
     * Also creates the action to dismiss it
     */
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

    /**
     * Sets onClickListener for button that
     * will make API Cal to generate the insult
     * Performs necessary input validation so that API
     * Call is valid
     * Determines what Arrays to utilize and based on whats in the EditTexts
     */
    private void setOnClickListener(){
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, nameText.getText().toString());
                Log.d(TAG, insulteeText.getText().toString());

                if (nameText.getText().toString().trim().length() <= 0) {

                }
            }
        });
    }

    /**
     * Instantiates all views created in XML file and calls
     * setOnClickListner()
     */
    private void instantiateViews(){
        nameText = (EditText) findViewById(R.id.name_edit_text);
        generate = (TextView) findViewById(R.id.generate);
        insulteeText = (EditText) findViewById(R.id.insultee);
        setOnClickListener();
    }

    /**
     * Displays the acivtity's layout and
     * set the action bar
     */
    private void setLayout(){
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Makes API call to backend to retrieve the various choices of
     * Insults available in the FOAAS API
     */
    private void getInsults(){
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, Constants.INSULTS_NAME, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // when request is complete, display content for app
                        setLayout();
                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });
        APICaller.getInstance(this).addToRequestQueue(request);
    }
}
