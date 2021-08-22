package lasitha.dilshan.image_editor_android_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Initialize variables
    Button btPick;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variables
        btPick = findViewById(R.id.bt_pick);
        imageView = findViewById(R.id.image_view);

        btPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create method
                checkPermission();
            }
        });
    }

    private void checkPermission() {

        //Initialize permission
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //Check condition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            //When device version is greater than equal to version 10
            //Create method
            pickImage();
        }else{
            //When device version is below version 10
            //Check condition
            if (permission != PackageManager.PERMISSION_GRANTED){
                //When permission is not granted
                //Request permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }else{
                //When permission is granted
                //Call method
                pickImage();
            }
        }
    }

    private void pickImage() {

        //Initialize intent
        Intent intent = new Intent(Intent.ACTION_PICK);

        //Set type
        intent.setType("image/*");

        //Start activity for result
        startActivityForResult(intent, 100);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Check condition
        if (requestCode == 100 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Call method
            pickImage();
        }else{
            //When permission is denied
            //Display toast
            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if (resultCode == RESULT_OK){
            //When result is ok
            //Initialize uri
            Uri uri = data.getData();
            switch (resultCode){
                case 100:
                    //When result code is equal to 100
                    //Initialize intent
                    Intent intent = new Intent(MainActivity.this,DsPhotoEditorActivity.class);

                    //Set Data
                    intent.setData(uri);

                    //Set Output directory name
                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY, "Images")
            }
        }
    }
}