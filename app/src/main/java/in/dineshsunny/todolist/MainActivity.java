package in.dineshsunny.todolist;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
        FloatingActionButton fab;

    CustomDbAccess dbAcces;
    EditText et;
    TextView tv;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAcces = new CustomDbAccess(this);
        dbAcces.open();

        fab=(FloatingActionButton)findViewById(R.id.fab);
        onFabClick();
        rv = (RecyclerView)
                findViewById(R.id.recyclerView);
        setRecylerView();

    }

    private void setRecylerView() {
        rv.setLayoutManager(
                new LinearLayoutManager(this));
        rv.setAdapter(
                new CustomAdapter(this,
               CustomDbAccess.getAllActivities()));
    }

    private  void onFabClick(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog().show();
            }
        });
    }




    private Dialog showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_card);
        builder.setMessage("Add your Activity")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Dialog d = (Dialog)dialogInterface;
                        et = (EditText) d.findViewById(R.id.dialogEditText);
                        dbAcces.addActivity(
                                et.getText().toString());
                        rv.setAdapter(
                                new CustomAdapter(MainActivity.this,
                                CustomDbAccess.getAllActivities()));

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast(MainActivity.this, "discarded");
            }
        });
        return builder.create();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.database){
            Intent intent = new Intent(this, AndroidDatabaseManager.class);
            startActivity(intent);
        }

        return true;
    }

    public static void showToast(Context context, String str){
        Toast.makeText(
                context, str, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        dbAcces.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dbAcces.close();
        super.onPause();
    }

}
