package in.dineshsunny.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Macbook on 26/07/16.
 */

public class CustomDbAccess {

    CustomDbHelper dbhelper;
    static SQLiteDatabase dbAccess;

    CustomDbAccess(Context context){
        dbhelper = new CustomDbHelper(context);
    }

    public void open(){
        dbAccess = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public long addActivity(String string){

        ContentValues cv = new ContentValues();
        cv.put(CustomDbHelper.COLUMN_NAME, string);

        long pk = dbAccess.insert(CustomDbHelper.TABLE_NAME,
                null,
                cv);
        return pk;
    }

    public static ArrayList<ActivityModel>
                        getAllActivities(){
        ArrayList<ActivityModel> amList
                = new ArrayList<>();
        Cursor cursor = dbAccess.query(
                CustomDbHelper.TABLE_NAME,
                new String[]{
                        CustomDbHelper.COLUMN_ID,
                        CustomDbHelper.COLUMN_NAME},
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            ActivityModel am =
                    convertcusorToActivity(cursor);
            amList.add(am);
            cursor.moveToNext();
        }

        cursor.close();

        return amList;

    }

    private static ActivityModel
            convertcusorToActivity(Cursor cursor) {

        ActivityModel am = new ActivityModel();

        long pk = cursor.getLong(
                cursor.getColumnIndexOrThrow(
                        CustomDbHelper.COLUMN_ID));
        String name = cursor.getString(1);

        am.setPk(pk);
        am.setName(name);
        return am;

    }

    public static int deleteActivity(long pk){

        return dbAccess.delete(
                CustomDbHelper.TABLE_NAME,
                CustomDbHelper.COLUMN_ID+" = "+pk,
                null);

    }

}
