package com.example.inventarioparciales;

import static com.example.inventarioparciales.VerSemestres.rutasemestre;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ShowAllImagesFromStorage extends AppCompatActivity {

    ArrayList<String> imagelist;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageAdapter adapter;
    TextView txtsinparciales;
    private String materiaSelect;
    private String examenSelect;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_images_from_storage);
        imagelist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new ImageAdapter(imagelist,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        txtsinparciales = findViewById(R.id.txtSinParciales);
        materiaSelect = getIntent().getStringExtra("materiaClick");
        examenSelect = getIntent().getStringExtra("examenClick");
        obtenerSemestre(examenSelect);

        String formarruta = "/"+materiaSelect+"/"+rutasemestre;
        Toast.makeText(this, formarruta, Toast.LENGTH_SHORT).show();
        StorageReference listRef = FirebaseStorage.getInstance().getReference().child(formarruta);
        listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if (listResult.getItems().size()>0) {
                    for (StorageReference file : listResult.getItems()) {
                        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                if (uri != null) {
                                    imagelist.add(uri.toString());
                                    Log.e("Itemvalue", uri.toString());
                                } else
                                    Log.e("URI Vacia", uri.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                recyclerView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                    txtsinparciales.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void obtenerSemestre(String examenSelect){
        String semestrepulsado = getIntent().getStringExtra("semestreClick");

        switch (semestrepulsado){
            case "Semestre 2022-1":
                rutasemestre = "Semestre 2022 - 1/"+examenSelect;
                break;
            case "Semestre 2022-2":
                rutasemestre = "Semestre 2022 - 2/"+examenSelect;
                break;
            case "Semestre 2021-1":
                rutasemestre = "Semestre 2021 - 1/"+examenSelect;
                break;
            case "Semestre 2021-2":
                rutasemestre = "Semestre 2021 - 2/"+examenSelect;
                break;
            case "Semestre 2020-1":
                rutasemestre = "Semestre 2020 - 1/"+examenSelect;
                break;
            case "Semestre 2020-2":
                rutasemestre = "Semestre 2020 - 2/"+examenSelect;
                break;
            default:
                rutasemestre="";
        }
    }

}