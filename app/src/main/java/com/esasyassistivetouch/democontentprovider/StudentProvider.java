package com.esasyassistivetouch.democontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;


public class StudentProvider extends ContentProvider {
    public static final int URI_TABLE_STUDENT_CODE = 1;
    public static final int URI_COLUMN_STUDENT_ID_CODE = 2;
    public static final int URI_COLUMN_STUDENT_NAME_CODE = 3;
    public static final int URI_COLUMN_STUDENT_UNIVERSITY_CODE = 4;
    public static final int DATABASE_VERSION = 1;
    public static final String AUTHOR = "com.esasyassistivetouch.democontentprovider.StudentProvider";
    public static final String DATABASE_BASE_NAME = "StudentInformation";
    public static final String TABLE_NAME_STUDENT = "Information";
    public static final String COLUMN_STUDENT_NAME = "_name";
    public static final String COLUMN_STUDENT_ID = "_id";
    public static final String COLUMN_STUDENT_UNIVERSITY = "_uni";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHOR + "/" + TABLE_NAME_STUDENT);
    public static UriMatcher uriMatcher;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOR, DATABASE_BASE_NAME, URI_TABLE_STUDENT_CODE);
        uriMatcher.addURI(AUTHOR, DATABASE_BASE_NAME + "/#", URI_COLUMN_STUDENT_ID_CODE);
        uriMatcher.addURI(AUTHOR, DATABASE_BASE_NAME + "/" + COLUMN_STUDENT_NAME, URI_COLUMN_STUDENT_NAME_CODE);
        uriMatcher.addURI(AUTHOR, DATABASE_BASE_NAME + "/" + COLUMN_STUDENT_UNIVERSITY, URI_COLUMN_STUDENT_UNIVERSITY_CODE);
        DataProviderHelper dataProviderHelper = new DataProviderHelper(getContext());
        database = dataProviderHelper.getWritableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_TABLE_STUDENT_CODE:
            case URI_COLUMN_STUDENT_ID_CODE:
            case URI_COLUMN_STUDENT_NAME_CODE:
            case URI_COLUMN_STUDENT_UNIVERSITY_CODE:
                return database.query(TABLE_NAME_STUDENT, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowID = database.insert(TABLE_NAME_STUDENT, "", values);

        if (rowID > 0) {
            Uri uriValue = ContentUris.withAppendedId(CONTENT_URI, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uriValue, null);
            return uriValue;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_TABLE_STUDENT_CODE:
                return database.delete(TABLE_NAME_STUDENT, selection, selectionArgs); // xoa 1 bang trong CSDL

            case URI_COLUMN_STUDENT_ID_CODE:
                String id = uri.getPathSegments().get(1);
                return database.delete(TABLE_NAME_STUDENT, COLUMN_STUDENT_ID + " = ? " + id + // xoa  theo cot _id
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

            case URI_COLUMN_STUDENT_NAME_CODE:
                String studentName = uri.getPathSegments().get(1);
                return database.delete(TABLE_NAME_STUDENT, COLUMN_STUDENT_NAME + " = " + studentName + // xoa theo cot _name
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

            case URI_COLUMN_STUDENT_UNIVERSITY_CODE:
                String studentUniversity = uri.getPathSegments().get(1);
                return database.delete(TABLE_NAME_STUDENT, COLUMN_STUDENT_UNIVERSITY + " = " + studentUniversity + // xoa theo cot _uni
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_COLUMN_STUDENT_ID_CODE:
                return database.update(TABLE_NAME_STUDENT, values, COLUMN_STUDENT_ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

            case URI_COLUMN_STUDENT_NAME_CODE:
                return database.update(TABLE_NAME_STUDENT, values, COLUMN_STUDENT_NAME + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

            case URI_COLUMN_STUDENT_UNIVERSITY_CODE:
                return database.update(TABLE_NAME_STUDENT, values, COLUMN_STUDENT_UNIVERSITY + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    public static class DataProviderHelper extends SQLiteOpenHelper {

        private static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME_STUDENT + " (" + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_UNIVERSITY + " TEXT " + ")";


        DataProviderHelper(Context context) {
            super(context, DATABASE_BASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_STUDENT);
            onCreate(db);
        }
    }
}
