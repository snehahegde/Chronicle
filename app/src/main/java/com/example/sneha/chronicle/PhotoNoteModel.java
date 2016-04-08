package com.example.sneha.chronicle;

import java.io.Serializable;

/**
 * Created by Sneha on 2/12/2016.
 */
public class PhotoNoteModel implements Serializable{
    private String caption;
    private String filePath;

    public PhotoNoteModel(String caption, String filePath) {
        this.caption = caption;
        this.filePath = filePath;
    }

    public String getCaption() {
        return caption;
    }

    public String getFilePath() {
        return filePath;
    }
}
