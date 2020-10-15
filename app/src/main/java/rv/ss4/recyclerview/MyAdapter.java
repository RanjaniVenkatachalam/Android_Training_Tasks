package rv.ss4.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private  List listdata;

    public MyAdapter(List listdata)
    {

        this.listdata = listdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rv_textview, viewGroup, false);
        return new MyViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        final SqliteDatabase sqliteDatabase=new SqliteDatabase(context);
        final Data data= (Data) listdata.get(i);
        viewHolder.name.setText(data.getAndroidVersions());
        viewHolder.version.setText(data.getVersion());

        viewHolder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialogbox(data);
            }
        });

        viewHolder.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteDatabase.delete(data.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });
    }
    @Override
    public int getItemCount() {

        return listdata.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,version;
        Button editbutton,deletebutton;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.text);
            version=itemView.findViewById(R.id.text2);
            editbutton=itemView.findViewById(R.id.editbutton);
            deletebutton=itemView.findViewById(R.id.deleteButton);
        }
    }

    private void updateDialogbox(final Data data) {

        final SqliteDatabase sqliteDatabase=new SqliteDatabase(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View subView = inflater.inflate(R.layout.version_name, null);
        final EditText androidName = subView.findViewById(R.id.editText);
        final EditText versionName=subView.findViewById(R.id.version);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update version name");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Update name", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = androidName.getText().toString();
                String version=versionName.getText().toString();
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(version) ) {
                    Toast.makeText(context, "feild empty", Toast.LENGTH_LONG).show();
                }
                else {
                    sqliteDatabase.update((new Data(data.getId(),name,version)));
                   ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

}
