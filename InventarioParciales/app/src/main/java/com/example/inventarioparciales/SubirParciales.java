package com.example.inventarioparciales;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubirParciales extends AppCompatActivity {

    private static final int File = 1 ;
    DatabaseReference myRef;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.uploadImageView)
    ImageView mUploadImageView;
    EditText editTextTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_parciales);

        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef= database.getReference("user1");
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);

        mUploadImageView.setOnClickListener(v -> fileUpload());
    }

    public void fileUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,File);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String carpeta = editTextTextPersonName.getText().toString();

        if(requestCode == File){

            if(resultCode == RESULT_OK){

                Uri FileUri = data.getData();

                StorageReference Folder = FirebaseStorage.getInstance().getReference().child(carpeta);

                final StorageReference file_name = Folder.child("file"+FileUri.getLastPathSegment());


                file_name.putFile(FileUri).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("link", String.valueOf(uri));
                    myRef.setValue(hashMap);

                    Log.d("Mensaje", "Se subió correctamente");

                }));

            }

        }

    }
}