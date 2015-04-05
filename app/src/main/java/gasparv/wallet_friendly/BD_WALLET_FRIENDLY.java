package gasparv.wallet_friendly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD_WALLET_FRIENDLY extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE Rubros (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ValorEsperado REAL,ValorActual REAL, Ciclo INTEGER,TIPO BOOLEAN  )";
    public BD_WALLET_FRIENDLY(Context contexto, String nombre,
                              SQLiteDatabase.CursorFactory factory, int version) {
       super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF Rubros EXISTS ");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}
