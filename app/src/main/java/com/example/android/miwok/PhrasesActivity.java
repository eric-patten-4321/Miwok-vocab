package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> phraseWords = new ArrayList<>();
        phraseWords.add(new Word("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));
        phraseWords.add(new Word("What is your name?", "tinne oyaase'ne", R.raw.phrase_what_is_your_name));
        phraseWords.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phraseWords.add(new Word("How are you feeling?", "michekses?", R.raw.phrase_how_are_you_feeling));
        phraseWords.add(new Word("I'm feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        phraseWords.add(new Word("Are you coming?", "eenes'aa", R.raw.phrase_are_you_coming));
        phraseWords.add(new Word("Yes, I'm coming", "hee'eenem", R.raw.phrase_yes_im_coming));
        phraseWords.add(new Word("I'm coming", "eenem", R.raw.phrase_im_coming));
        phraseWords.add(new Word("Let's go", "yoowutis", R.raw.phrase_lets_go));
        phraseWords.add(new Word("Come here", "ani'nem", R.raw.phrase_come_here));

        WordAdapter itemsAdapter = new WordAdapter(this, phraseWords, R.color.category_phrases);
        ListView listView = findViewById(R.id.list_of_words);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, phraseWords.get(i).getmAudioFileId());

                    mMediaPlayer.start();
                    // set up listener on mediaplayer so we can stop and release player once file stops playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        //if mediaPlayer is not null, it may be playing something already
        if(mMediaPlayer != null) {
            //free mediaplayer resources
            mMediaPlayer.release();
            //setting to null indicates that mediaplayer is not ready/configured to play anything.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
