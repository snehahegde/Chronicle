package com.example.sneha.chronicle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Sneha on 2/5/2016.
 */
public class PhotoNoteModelHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PhotoNotes.db";
    public static final String DATABASE_TABLE = "PhotoNotes";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "_id";
    public static final String CAPTION_COLUMN = "description";
    public static final String FILEPATH_COLUMN = "filepath";

    private static final String DATABASE_CREATE = String.format("CREATE TABLE %s (" +
                    " %s integer primary key autoincrement, " +
                    " %s text, " +
                    " %s text)",
            DATABASE_TABLE, COLUMN_ID, CAPTION_COLUMN, FILEPATH_COLUMN);

    public PhotoNoteModelHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public void insert(String caption, String filePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valuesToInsert = new ContentValues();
        valuesToInsert.put(PhotoNoteModelHelper.CAPTION_COLUMN, caption);
        valuesToInsert.put(PhotoNoteModelHelper.FILEPATH_COLUMN, filePath);
        db.insert(PhotoNoteModelHelper.DATABASE_TABLE, null, valuesToInsert);
    }

    public ArrayList<PhotoNoteModel> query() {
        SQLiteDatabase database = this.getWritableDatabase();
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {CAPTION_COLUMN, FILEPATH_COLUMN};
        Cursor cursor = database.query(DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        ArrayList<PhotoNoteModel> modelList = new ArrayList<>(cursor.getCount());
        int i = 0;
        while ( cursor.moveToNext()) {
            modelList.add(i,new PhotoNoteModel(cursor.getString(0), cursor.getString(1)));
            i++;
        }
       return modelList;
    }
}
