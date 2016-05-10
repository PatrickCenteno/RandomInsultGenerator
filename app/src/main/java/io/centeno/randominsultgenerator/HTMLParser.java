package io.centeno.randominsultgenerator;

import android.content.Context;

/**
 * Created by patrickcenteno on 5/10/16.
 */
public class HTMLParser {

    private final int CHARS_TO_BURN = 6;

    private Context context;
    private static HTMLParser instance;
    private String tag1;
    private String tag2;
    private String html;

    // Constructor for finding two html tags
    private HTMLParser(Context context,
                       String tag1, String tag2, String html){
        this.context = context;
        this.tag1 = tag1;
        this.tag2 = tag2;
        this.html = html;
    }


    public static synchronized HTMLParser getParser(Context context,
                                         String tag1, String tag2, String html){
        if (instance == null)
            instance = new HTMLParser(context, tag1, tag2, html);
        return instance;
    }

    private int[] findPositionOfTags() throws Exception{
        /**
         * Check to ensure that the html text, and both tags
         * are properly initialized before going forward with
         * finding the indices of the text you are trying to extract
         */
        if (html == null)
            throw new Exception("empty html code");
        if (tag1 == null)
            throw new Exception("empty tag1");
        if (tag2 == null)
            throw new Exception("empty tag1");

        int start = html.indexOf(tag1);
        int end = html.indexOf(tag2);

        return new int[]{start, end};
    }

    public String getParsedText() throws Exception{
        int [] temp = findPositionOfTags();
        return trimParsedText(html.subSequence(temp[0], temp[1]));
    }

    private String trimParsedText(CharSequence text){
        int charMinus = tag1.length() + CHARS_TO_BURN;
        return text.toString().substring(charMinus - 1);
    }
}
