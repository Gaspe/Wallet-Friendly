package gasparv.wallet_friendly;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pantalla_inicio extends ActionBarActivity {
    BD_WALLET_FRIENDLY Manejador=new BD_WALLET_FRIENDLY(this, "WalletFriendly_DB", null, 1);
    private int ciclo=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        final GridView v=(GridView) findViewById(R.id.gridView2);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String vis=parent.getItemAtPosition(position).toString();
                AlertDialog.Builder alert1 = new AlertDialog.Builder(Pantalla_inicio.this);
                alert1.setMessage("¿Cuanto gastó en este rubro?");
                final EditText input = new EditText(Pantalla_inicio.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert1.setView(input);
                AlertDialog.Builder ok1 = alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int suma=Integer.parseInt(vis)+Integer.parseInt(input.getText().toString());
                        SQLiteDatabase DBWF= Manejador.getWritableDatabase();
                        int u=position;
                        DBWF.execSQL("UPDATE Rubros SET ValorActual="+suma+" WHERE ID="+u+" ");
                        DBWF.close();
                        ArrayList list1=new ArrayList<String>();
                        final ArrayAdapter adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list1);
                        SQLiteDatabase db=Manejador.getReadableDatabase();
                        Cursor c=db.rawQuery("SELECT ValorActual FROM Rubros", null);
                        if(c.moveToFirst()) {
                            do {
                                list1.add(c.getString(0));

                            } while (c.moveToNext());
                            v.setAdapter(adapter1);
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
    }
    public void new_cycle(View v){}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_inicio, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Config_rub) {
            return true;
        }
        if (id == R.id.Config_cic) {
            return true;
        }
        if (id == R.id.Gen_rep) {
            
            return true;
        }
        if(id == R.id.menu_new) {
            final String rubro[] = new String[5];
            final String prim_grid[]= new String[2];
            rubro[2]="0";rubro[3]=String.valueOf(ciclo);
                AlertDialog.Builder alert = new AlertDialog.Builder(Pantalla_inicio.this);
                alert.setTitle("Rubro");
                alert.setMessage("Ingrese el nombre del rubro inicial");
                final EditText input = new EditText(this);
                alert.setView(input);
                AlertDialog.Builder ok = alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        rubro[0] = input.getText().toString();
                        prim_grid[0]=rubro[0];
                    }
                });
                AlertDialog.Builder alert1 = new AlertDialog.Builder(Pantalla_inicio.this);
                        alert1.setTitle("Rubro");
                        alert1.setMessage("Ingrese el valor esperado del rubro inicial");
                        final EditText input1 = new EditText(Pantalla_inicio.this);
                        input1.setInputType(InputType.TYPE_CLASS_NUMBER);
                        alert1.setView(input1);
                        AlertDialog.Builder ok1 = alert1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                        rubro[1] = input1.getText().toString();
                        prim_grid[1]=rubro[1];
                        SQLiteDatabase DBWF= Manejador.getWritableDatabase();
                        DBWF.execSQL("INSERT INTO Rubros (NOMBRE,ValorEsperado,ValorActual, Ciclo, Tipo) VALUES (?,?,?,?,?) ",rubro);
                        DBWF.close();
                        GridView gridView=(GridView) findViewById(R.id.gridView);
                        GridView gridView2=(GridView) findViewById(R.id.gridView2);
                        ArrayList list=new ArrayList<String>();
                        ArrayList list1=new ArrayList<String>();
                        final ArrayAdapter adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                        final ArrayAdapter adapter1=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item,list1);
                        SQLiteDatabase db=Manejador.getReadableDatabase();
                        Cursor c=db.rawQuery("SELECT NOMBRE,ValorEsperado FROM Rubros", null);
                        if(c.moveToFirst()) {
                            do {
                                list.add(c.getString(0));
                                list.add(c.getString(1));
                                list1.add("0");
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
                    }
                });
                final String[] items = {"Entrada", "Salida"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Pantalla_inicio.this);
                builder.setTitle("Escoja el tipo de rubro");
                AlertDialog.Builder esc = builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item==0) {
                            rubro[4] = "0";
                        } else {
                            rubro[4] = "1";
                        }
                    }
                });
                alert1.show();
                alert.show();
                esc.show();
        }
        return super.onOptionsItemSelected(item);
    }
}