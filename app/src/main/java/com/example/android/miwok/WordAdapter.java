package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;
    public WordAdapter(@NonNull Context context, @NonNull ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        // if view is null, inflate a new one
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // get the Word at position in array of words
        Word currentWord = getItem(position);

        // find the views for display
        TextView defaultTranslation = listItemView.findViewById(R.id.tv_english_word);
        TextView miwokTranslation = listItemView.findViewById(R.id.tv_miwok_word);
        ImageView wordImage = listItemView.findViewById(R.id.iv_word_pic);

        // null check
        if (currentWord != null) {
            //set the text from current Word
            miwokTranslation.setText(currentWord.getmMiwokTranslation());
            defaultTranslation.setText(currentWord.getmDefaultTranslation());
            if (currentWord.hasImage()) {
                wordImage.setImageResource(currentWord.getImageResourceId());
            } else {
                wordImage.setVisibility(View.GONE);
            }

            //get reference to linearLayout that holdsthe textviews
            LinearLayout layout = listItemView.findViewById(R.id.layout_textHolder);
            // find color that resourceID maps to
            int color = ContextCompat.getColor(getContext(), mColorResourceId);
            //set background color
            layout.setBackgroundColor(color);
        }


//        //This whole block works, but changing it to per instructor, because it is probably a bit mo
//        //standard the way they taught it...
//        // null check
//        if (currentWord != null) {
//            //set the text from current Word
//            miwokTranslation.setText(currentWord.getmMiwokTranslation());
//            defaultTranslation.setText(currentWord.getmDefaultTranslation());
//            wordImage.setImageResource(currentWord.getImageResourceId());
//
//            //getImageResourceID returns an int, so....
//            // if imageResourceId == 0, then there is no image -
//            // remove the imageView by setting visibility to GONE
//            // FOR THIS TO WORK:  in Word change:
//            //          private int mImageResourceId;
//            //          remove hasImage() and static final int NO_IMAGE_BLAH_BLAH
//            if(currentWord.getImageResourceId() == 0) {
//                wordImage.setVisibility(View.GONE);
//            }
//        }

        return listItemView;
    }
}
