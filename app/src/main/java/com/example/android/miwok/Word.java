package com.example.android.miwok;

/**
 * {@link Word} vocabulary word containing default translation and Miwok translation
 *
 */
public class Word {
    /** default translation */
    private String mDefaultTranslation;
    /** Miwok translation */
    private String mMiwokTranslation;
    private int mAudioFileId;

    // for determining whether or not a Word has an image
    // -1 is outside the range of resource Ids
    private static final int NO_IMAGE_PROVIDED = -1;
    // mImageResourceId set to no_image by default. If it gets an imageResourceId, it is no longer -1
    private int mImageResourceId = NO_IMAGE_PROVIDED;



    /**
     *
     *
     * @param defaultTranslation word in users default language translation
     * @param miwokTranslation word translate
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioFileId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioFileId = audioFileId;
    }

    // Phrases activity does not use image, keep original constructor for it's use.
    public Word(String defaultTranslation, String miwokTranslation, int audioFileId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioFileId = audioFileId;
    }

    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }


    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }


    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getmAudioFileId() {
        return mAudioFileId;
    }

     // if mImageResourceId is not -1, hasImage() == true
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
