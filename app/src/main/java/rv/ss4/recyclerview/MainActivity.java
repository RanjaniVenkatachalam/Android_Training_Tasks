package rv.ss4.recyclerview;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SqliteDatabase sqliteDatabase;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    Button insertBtn;
    ArrayList<Data> data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqliteDatabase=new SqliteDatabase(this);
        insertBtn=findViewById(R.id.insert);

        recyclerView=findViewById(R.id.recyclerview);
        display();
        myAdapter=new MyAdapter(data);
        RecyclerView.LayoutManager rvLayout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rvLayout);
        recyclerView.setAdapter(myAdapter);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialogbox();
            }
        });
    }

    void display(){
        Cursor result=sqliteDatabase.readData();
        if(result.getCount()==0){

            Toast.makeText(this,"nothing to show",Toast.LENGTH_SHORT).show();
        }else{
            while(result.moveToNext()){
                long id=result.getLong(0);
                String name=result.getString(1);
                String version=result.getString(2);
                data.add(new Data(id,name,version));

            }
        }

    }

    private void addDialogbox() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.version_name, null);
        final EditText textName = subView.findViewById(R.id.editText);
        final EditText versionName=subView.findViewById(R.id.version);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add new version");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD versions", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = textName.getText().toString();
                String version=versionName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "feild empty", Toast.LENGTH_LONG).show();
                }
                else {
                    sqliteDatabase.insert(name,version);
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqliteDatabase != null) {
            sqliteDatabase.close();
        }
    }



}
