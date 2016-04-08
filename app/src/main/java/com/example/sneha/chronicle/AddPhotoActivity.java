package com.example.sneha.chronicle;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sneha.chronicle.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPhotoActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1;
    private ImageView imageView;
    private File imageFile;
    private EditText editText;

    private boolean photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if ( savedInstanceState != null) {
            if (savedInstanceState.getCharSequence("filename") != null) {
                String filepath = savedInstanceState.getCharSequence("filename").toString();
                imageFile = new File(filepath);
                imageView = (ImageView) findViewById(R.id.photoCaptured);
                imageView.setImageURI(Uri.fromFile(imageFile));
                photoTaken = true;
            }
        }


        Button capturePhotoButton = (Button)findViewById(R.id.takePhotoButton);
        capturePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoTaken = false;
                createTakePhotoIntent();
            }
        });

        Button saveButton = (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = (EditText) findViewById(R.id.captionText);
                if (editText.getText().toString().equals("") || photoTaken == false) {
                    new AlertDialog.Builder(AddPhotoActivity.this)
                            .setTitle("Warning!")
                            .setMessage("You have not entered all details!")
                            .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else {
                    saveToDatabase();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if ( imageFile != null) {
            outState.putCharSequence("filename", imageFile.getPath());
        }
    }

    private File createImagePath() throws IOException{
        // This method creates a temp file
        String timeCreated = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "PhotoNotes" + timeCreated;
        File directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        imageFile = File.createTempFile(fileName,".jpg",directory);
        return imageFile;

    }

    private void createTakePhotoIntent() {
        Intent capturePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (capturePhotoIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImagePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ( photoFile != null ) {
                capturePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(capturePhotoIntent, CAMERA_REQUEST);
            }

        }
    }

   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                imageView = (ImageView)findViewById(R.id.photoCaptured);
                imageView.setImageURI( Uri.fromFile(imageFile));
                photoTaken = true;

            }
    }

    private void saveToDatabase() {
        editText = (EditText) findViewById(R.id.captionText);
        MainActivity.photoNoteModelHelper.insert(editText.getText().toString(), imageFile.getAbsolutePath());
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
