package io.centeno.randominsultgenerator;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by patrickcenteno on 4/28/16.
 */
public class InstructionsDialogBox extends DialogFragment {

    final private CharSequence [] instructions = {
            "- Enter your name in the first text box",
            "- To make your insult personal, enter the name of the person getting insulted in the second text box (optional)",
            "- Click generate to see your new insult!",
            "- Share with your friends! (or enemies)",
            "- Powered by www.foaas.com"
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Welcome to Random Insult Generator");
        builder.setItems(instructions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });

        builder.setPositiveButton("Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }
}
