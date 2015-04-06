package gasparv.wallet_friendly;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ConfigRubros extends ActionBarActivity {
    BD_WALLET_FRIENDLY Manejador=new BD_WALLET_FRIENDLY(this, "WalletFriendly_DB", null, 1);
    private int ciclo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_rubros);
        /////////////////////////////7
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
        /////////////////////////////////
        GridView gridView=(GridView) findViewById(R.id.gridView);
        final GridView gridView2=(GridView) findViewById(R.id.gridView3);
        ArrayList list=new ArrayList<String>();
        ArrayList list1=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.mitextview,list);
        final ArrayAdapter adapter1=new ArrayAdapter<String>(getApplicationContext(),R.layout.mitextview,list1);
        SQLiteDatabase db=Manejador.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT NOMBRE,TIPO,ValorEsperado FROM Rubros WHERE Ciclo="+String.valueOf(ciclo), null);
        if(c.moveToFirst()) {
            do {
                list.add(c.getString(0));
                if(c.getString(1).equals("0"))
                {list.add("Entrada");}
                else{list.add("Salida");}
                list1.add(c.getString(2));
            } while (c.moveToNext());
            gridView.setAdapter(adapter);
            gridView2.setAdapter(adapter1);
            runOnUiThread(new Runnable() {
                public void run() {
                    adapter.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                }
            });
        }
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String vis = parent.getItemAtPosition(position).toString();
                AlertDialog.Builder alert1 = new AlertDialog.Builder(ConfigRubros.this);
                alert1.setMessage("Ingrese el nuevo valor esperado. (Recuerde: ¡Este valor solo se hará visible en el siguiente ciclo!)");
                final EditText input = new EditText(ConfigRubros.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert1.setView(input);
                AlertDialog.Builder ok1 = alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SQLiteDatabase DBWF = Manejador.getWritableDatabase();
                        int u = position + 1;
                        DBWF.execSQL("UPDATE Rubros SET ValorEsperado="+Integer.parseInt(input.getText().toString())+" WHERE ID=" + u + " ");
                        DBWF.close();
                        ArrayList list1 = new ArrayList<String>();
                        final ArrayAdapter adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.mitextview, list1);
                        SQLiteDatabase db = Manejador.getReadableDatabase();
                        Cursor c = db.rawQuery("SELECT ValorEsperado FROM Rubros", null);
                        if (c.moveToFirst()) {
                            do {
                                list1.add(c.getString(0));

                            } while (c.moveToNext());
                            gridView2.setAdapter(adapter1);
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    adapter1.notifyDataSetChanged();
                                }
                            });
                        }

                    }
                });
                alert1.show();
            }
        });
        TextView tol=(TextView) findViewById(R.id.textView9);
        SQLiteDatabase dt=Manejador.getReadableDatabase();
        Cursor cur =dt.rawQuery("Select tolerancia from Ciclo where ID_ciclo="+String.valueOf(ciclo)+"",null);
        cur.moveToFirst();
        tol.setText(String.valueOf(cur.getInt(0)+"%"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config_rubros, menu);
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
