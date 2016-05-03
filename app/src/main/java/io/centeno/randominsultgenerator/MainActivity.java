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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

                if (nameText.getText().toString().trim().length() > 0) {
                    if (insulteeText.getText().toString().trim().length() == 0){

                        // No text insultee name give. Randomly select from withNoName
                        int val = (int)(Math.random() * (withNoName.length - 1));
                        String endpoint = withNoName[val];
                        String foassUrl = Constants.FOAAS + "/" + endpoint + "/" + nameText.getText();
                        Toast.makeText(getApplicationContext(), foassUrl, Toast.LENGTH_LONG).show();
                    }else{

                        // Name of insultee given, randomly select from withName
                        int val = (int)(Math.random() * (withName.length -1));
                        String endpoint = withName[val];
                        String foassUrl = Constants.FOAAS + "/" + endpoint + "/" +
                                insulteeText.getText() + "/" +
                                nameText.getText();
                        Toast.makeText(getApplicationContext(), foassUrl, Toast.LENGTH_LONG).show();
                    }
                }else{
                    // No text was entered
                    Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_LONG).show();
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

        // Getting withName array
        JsonArrayRequest request = new JsonArrayRequest
                (Request.Method.GET, Constants.INSULTS_NAME, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0){
                            // not sure if this is needed
                            withName = new String[response.length()];
                            withName = getArrayFromJson(response, response.length());
                            for (String s : withName){
                                Log.d(TAG, "in for loop " + s);
                            }
                        }

                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
        });

        // Getting withNoName array
        JsonArrayRequest request2 = new JsonArrayRequest
                (Request.Method.GET, Constants.INSULTS_NO_NAME, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        if (response.length() > 0){
                            withNoName = new String[response.length()];
                            withNoName = getArrayFromJson(response, response.length());
                            for (String s : withNoName){
                                Log.d(TAG, "in for loop " + s);
                            }
                        }
                        // when request is complete, display content for app
                        setLayout();
                        // Instantiate the Views for activity
                        instantiateViews();
                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                });
        APICaller.getInstance(this).addToRequestQueue(request);
        APICaller.getInstance(this).addToRequestQueue(request2);
    }

    private String[] getArrayFromJson (JSONArray array, int length){
        if (length > 0) {
            String[] temp = new String[length];
            for (int i = 0; i < array.length(); i++) {
                try {
                    temp[i] = array.get(i).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return temp;
        }else   return null;
    }
}
