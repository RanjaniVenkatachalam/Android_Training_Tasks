package rv.ss4.recyclerview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;


public class SqliteDatabase extends SQLiteOpenHelper {
   public static final String DATABASE_NAME="versions_db";
    public static final String TABLE_NAME="version_info";
    public static final String COL_1="id";
    public static final String COL_2="name";
    public static final String COL_3="version";

    public SqliteDatabase (Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+TABLE_NAME+"(id INTEGER  PRIMARY KEY  AUTOINCREMENT,name TEXT,version TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insert(String name,String version){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("version",version);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return  true;
    }

    public Cursor readData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result=db.rawQuery("select * from "+TABLE_NAME,null);
        return result ;
    }

    public boolean update(Data data){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",data.getAndroidVersions());
        contentValues.put("version",data.getVersion());
        sqLiteDatabase.update(TABLE_NAME,contentValues,"id=?",new String[] {String.valueOf(data.getId())});
        return true;

    }
    public void delete(long id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,"id = ?",new String[] {String.valueOf(id)});
    }

}
