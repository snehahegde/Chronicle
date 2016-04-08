package com.example.sneha.chronicle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sneha.chronicle.R;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static PhotoNoteModelHelper photoNoteModelHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoNoteModelHelper = new PhotoNoteModelHelper(this);

        final ArrayList<PhotoNoteModel> photoNoteModelArrayList = photoNoteModelHelper.query();
        String[] result = new String[photoNoteModelArrayList.size()];
        int i = 0;
        for ( PhotoNoteModel photoNoteModel : photoNoteModelArrayList ) {
            result[i] = photoNoteModel.getCaption();
            i++;
        }

        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,result));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewPhotoActivity.class);
                String name = (String) parent.getItemAtPosition(position);

                intent.putExtra("selectedModel", (Serializable) photoNoteModelArrayList.get(position));
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        final ArrayList<PhotoNoteModel> photoNoteModelArrayList = photoNoteModelHelper.query();
        String[] result = new String[photoNoteModelArrayList.size()];
        int i = 0;
        for ( PhotoNoteModel photoNoteModel : photoNoteModelArrayList ) {
            result[i] = photoNoteModel.getCaption();
            i++;
        }

        final ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,result));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewPhotoActivity.class);
                String name = (String) parent.getItemAtPosition(position);

                intent.putExtra("selectedModel", (Serializable) photoNoteModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_uninstall) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:com.example.sneha.photonotes"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
