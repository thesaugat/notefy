package com.thesaugat.notefy.db;

import android.provider.BaseColumns;

public class NoteEntry implements BaseColumns {
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESC = "note";
    public static final String COLUMN_NAME_DATE = "date";
}