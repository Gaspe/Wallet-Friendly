package gasparv.wallet_friendly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD_WALLET_FRIENDLY extends SQLiteOpenHelper {
    String sqlCreate1 = "CREATE TABLE Rubros (ID INTEGER PRIMARY KEY AUTOINCREMENT,ID_pc INTEGER, NOMBRE TEXT, ValorEsperado REAL,ValorActual REAL, Ciclo INTEGER,TIPO BOOLEAN  )";
    String sqlCreate2 ="CREATE TABLE Ciclo (ID_ciclo INTEGER PRIMARY KEY AUTOINCREMENT, tolerancia INTEGER)";
    public BD_WALLET_FRIENDLY(Context contexto, String nombre,
                              SQLiteDatabase.CursorFactory factory, int version) {
       super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //db.execSQL("DROP TABLE IF Rubros EXISTS ");
        //db.execSQL(sqlCreate);
    }
}
