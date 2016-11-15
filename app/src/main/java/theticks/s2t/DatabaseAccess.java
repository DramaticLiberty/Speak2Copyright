package theticks.s2t;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpadurariu on 15.11.2016.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    // Table Names
    private static final String TABLE_STUDIES = "studies";
    private static final String TABLE_AUTHORS = "authors";
    private static final String TABLE_INDUSTRIES = "industries";
    private static final String TABLE_COUNTRIES = "countries";
    private static final String TABLE_STUDIES_TO_AUTHORS = "studies_to_authors";
    private static final String TABLE_STUDIES_TO_INDUSTRIES = "studies_to_industries";
    private static final String TABLE_STUDIES_TO_COUNTRIES = "studies_to_countries";
    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        try {
            this.database = openHelper.getReadableDatabase();
        } catch(Exception e) {
            // TODO Log exceptions and handle it in a recoverable way
        }
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of studies
     */
    public List<String> getStudies() {
        if (database == null) {
            return new ArrayList<String>();
        }

        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT name FROM studies", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<StudiesByCountry> getNumberOfStudiesByCountry() {
        if (database == null) {
            return new ArrayList<StudiesByCountry>();
        }

        List<StudiesByCountry> studiesByCountry = new ArrayList<>();
        String selectQuery =  "SELECT name as country, count(*) as nr FROM " + this.TABLE_STUDIES_TO_COUNTRIES +
                " JOIN " + this.TABLE_COUNTRIES + " c ON country_id=c.id GROUP BY country_id;";
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            StudiesByCountry studiesCountry = new StudiesByCountry(cursor.getString(cursor.getColumnIndex("country")),
                    cursor.getInt(cursor.getColumnIndex("nr")));
            studiesByCountry.add(studiesCountry);
            cursor.moveToNext();
        }
        cursor.close();
        return studiesByCountry;
    }
}
