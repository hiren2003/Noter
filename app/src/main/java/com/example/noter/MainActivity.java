package com.example.noter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton Add;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<ClsModel> arrayList = new ArrayList<>();
    RcAdapter rcAdapter;
    DB_HELPER db_helper = new DB_HELPER(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Format f = new SimpleDateFormat("EEEE");
        String str = f.format(new Date());
        getSupportActionBar().setTitle("Hello !");
        toolbar.setSubtitle(str);
        Add = findViewById(R.id.addnote);
        recyclerView = findViewById(R.id.recycleview);
        fill();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        {
            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.lytdg);
                    dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.crv));
                    dialog.show();
                    Button btn = dialog.findViewById(R.id.button);
                    EditText edttitle = dialog.findViewById(R.id.editTextText);
                    EditText edtdes = dialog.findViewById(R.id.editTextText2);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db_helper.add(edttitle.getText().toString(), edtdes.getText().toString());
                            fill();
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    public void fill() {
        arrayList = db_helper.fetch();
        rcAdapter = new RcAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(rcAdapter);
    }
}