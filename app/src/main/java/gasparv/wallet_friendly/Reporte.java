package gasparv.wallet_friendly;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class Reporte extends ActionBarActivity {
    BD_WALLET_FRIENDLY Manejador=new BD_WALLET_FRIENDLY(this, "WalletFriendly_DB", null, 1);;
    private int ciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        SQLiteDatabase db_prev=Manejador.getReadableDatabase();
        Cursor p=db_prev.rawQuery("SELECT * FROM Ciclo",null);
        boolean sw=false;
        if(!p.moveToFirst())
        {   sw=true;
            ciclo=1;
        }
        else{
            Cursor p1=db_prev.rawQuery("SELECT MAX(ID_ciclo) from Ciclo",null);
            p1.moveToFirst();
            ciclo=p1.getInt(0);
        }
        db_prev.close();
        TextView bgeneral = (TextView) findViewById(R.id.textView5);
        TextView bin = (TextView) findViewById(R.id.textView6);
        TextView bout = (TextView) findViewById(R.id.textView7);
        SQLiteDatabase db=Manejador.getReadableDatabase();
        Cursor entradas = db.rawQuery("SELECT SUM(ValorActual) FROM Rubros WHERE TIPO=0 AND Ciclo="+String.valueOf(ciclo),null);
        Cursor salidas = db.rawQuery("SELECT SUM(ValorActual) FROM Rubros WHERE TIPO=1 AND Ciclo="+String.valueOf(ciclo),null);
        entradas.moveToFirst();
        salidas.moveToFirst();
        bgeneral.setText(entradas.getInt(0) + " - " + salidas.getInt(0)+" = "+(entradas.getInt(0)-salidas.getInt(0)));
        Cursor exp_in = db.rawQuery("SELECT SUM(ValorEsperado) FROM Rubros WHERE TIPO=0 AND Ciclo="+String.valueOf(ciclo),null);
        Cursor real_in = db.rawQuery("SELECT SUM(ValorActual) FROM Rubros WHERE TIPO=0 AND Ciclo="+String.valueOf(ciclo),null);
        exp_in.moveToFirst();
        real_in.moveToFirst();
        bin.setText(exp_in.getInt(0) + " - " + real_in.getInt(0)+" = "+(exp_in.getInt(0)-real_in.getInt(0)));
        Cursor exp_out = db.rawQuery("SELECT SUM(ValorEsperado) FROM Rubros WHERE TIPO=1 AND Ciclo="+String.valueOf(ciclo),null);
        Cursor real_out = db.rawQuery("SELECT SUM(ValorActual) FROM Rubros WHERE TIPO=1 AND Ciclo="+String.valueOf(ciclo),null);
        exp_out.moveToFirst();
        real_out.moveToFirst();
        bout.setText(exp_out.getInt(0) + " - " + salidas.getInt(0)+" = "+(entradas.getInt(0)-salidas.getInt(0)));
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reporte, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
