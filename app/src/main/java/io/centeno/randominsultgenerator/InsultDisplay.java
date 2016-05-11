package io.centeno.randominsultgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class InsultDisplay extends AppCompatActivity {

    private final String TAG = "InsultDisplay";

    private TextView insultDisplay;
    private String insult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insult_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Random Insult Generator");
        setSupportActionBar(toolbar);

        insult = getIntent().getExtras().getString("insult");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        insultDisplay = (TextView) findViewById(R.id.insult_display_text);
        insultDisplay.setText(insult);

    }

    public void shareInsult(View v){
        // When floating action button is clicked
        Log.d(TAG, "shareInsult called ");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, insultDisplay.getText());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Send Insult..."));
    }

}
