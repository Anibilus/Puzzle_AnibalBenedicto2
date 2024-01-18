package com.example.puzzle_anibalbenedicto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "puzzle_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SCORE = "score";
    private static final String TABLE_SCORES_NIVEL_UNO = "scores_nivel_uno";
    private static final String COLUMN_NIVEL_UNO_ID = "_id_nivel_uno";
    private static final String COLUMN_NIVEL_UNO_SCORE = "score_nivel_uno";
    private static final String TABLE_SCORES_NIVEL_DOS = "scores_nivel_dos";
    private static final String COLUMN_NIVEL_DOS_ID = "_id_nivel_dos";
    private static final String COLUMN_NIVEL_DOS_SCORE = "score_nivel_dos";

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnScore() {
        return COLUMN_SCORE;
    }

    public static String getTableScores() {
        return TABLE_SCORES;
    }

    public static String getTableScoresNivelUno() {
        return TABLE_SCORES_NIVEL_UNO;
    }

    public static String getColumnNivelUnoId() {
        return COLUMN_NIVEL_UNO_ID;
    }

    public static String getColumnNivelUnoScore() {
        return COLUMN_NIVEL_UNO_SCORE;
    }

    public static String getTableScoresNivelDos() {
        return TABLE_SCORES_NIVEL_DOS;
    }

    public static String getColumnNivelDosId() {
        return COLUMN_NIVEL_DOS_ID;
    }

    public static String getColumnNivelDosScore() {
        return COLUMN_NIVEL_DOS_SCORE;
    }

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método llamado al crear la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla principal de puntuaciones
        String CREATE_TABLE_SCORES = "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_SCORE + " INTEGER" +
                ");";
        db.execSQL(CREATE_TABLE_SCORES);

        // Crear la tabla de puntuaciones del nivel uno
        String CREATE_TABLE_SCORES_NIVEL_UNO =
                "CREATE TABLE " + TABLE_SCORES_NIVEL_UNO + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_SCORE + " INTEGER," +
                        COLUMN_NIVEL_UNO_ID + " INTEGER," +
                        COLUMN_NIVEL_UNO_SCORE + " INTEGER" +
                        ");";
        db.execSQL(CREATE_TABLE_SCORES_NIVEL_UNO);

        // Crear la tabla de puntuaciones del nivel dos
        String CREATE_TABLE_SCORES_NIVEL_DOS =
                "CREATE TABLE " + TABLE_SCORES_NIVEL_DOS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_SCORE + " INTEGER," +
                        COLUMN_NIVEL_DOS_ID + " INTEGER," +
                        COLUMN_NIVEL_DOS_SCORE + " INTEGER" +
                        ");";
        db.execSQL(CREATE_TABLE_SCORES_NIVEL_DOS);
    }

    // Método llamado al actualizar la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES_NIVEL_UNO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES_NIVEL_DOS);
        onCreate(db);
    }

    // Métodos para agregar puntuaciones
    public void addScore(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public void addScoreNivelUno(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_NIVEL_UNO_ID, 1);
        values.put(COLUMN_NIVEL_UNO_SCORE, score);
        db.insert(TABLE_SCORES_NIVEL_UNO, null, values);
        db.close();
    }

    public void addScoreNivelDos(String name, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SCORE, score);
        values.put(COLUMN_NIVEL_DOS_ID, 2);
        values.put(COLUMN_NIVEL_DOS_SCORE, score);
        db.insert(TABLE_SCORES_NIVEL_DOS, null, values);
        db.close();
    }

    // Método para obtener todas las puntuaciones del nivel uno
    public Cursor getAllScoresNivelUno() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_NIVEL_UNO_SCORE};
        String selection = COLUMN_NIVEL_UNO_ID + " = ?";
        String[] selectionArgs = {"1"};
        return db.query(TABLE_SCORES_NIVEL_UNO, projection, selection, selectionArgs, null, null, COLUMN_NIVEL_UNO_SCORE + " ASC");
    }

    // Método para obtener todas las puntuaciones del nivel dos
    public Cursor getAllScoresNivelDos() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_NIVEL_DOS_SCORE};
        String selection = COLUMN_NIVEL_DOS_ID + " = ?";
        String[] selectionArgs = {"2"};
        return db.query(TABLE_SCORES_NIVEL_DOS, projection, selection, selectionArgs, null, null, COLUMN_NIVEL_DOS_SCORE + " ASC");
    }
}

