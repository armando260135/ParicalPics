package com.example.inventarioparciales;

import static com.example.inventarioparciales.VerSemestres.rutasemestre;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ShowAllImagesFromStorage extends AppCompatActivity {

    ArrayList<String> imagelist;
    ViewPager2 viewPager2;
    ImageAdapter adapter;
    TextView txtsinparciales,txtdescargar;
    private String materiaSelect;
    private String examenSelect;
    private static final short REQUEST_CODE = 6545;
    public static String NAME_FILE = "";
    ProgressBar progressBarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_images_from_storage);
        imagelist = new ArrayList<>();
        txtdescargar = findViewById(R.id.txtdescargar);
        viewPager2 = findViewById(R.id.viewPagerParciales);
        progressBarImagen = findViewById(R.id.progress_imagenes);
        materiaSelect = getIntent().getStringExtra("materiaClick");
        examenSelect = getIntent().getStringExtra("examenClick");

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("network", " "+networkInfo);
        } else {
            toast();
            Log.d("network", " "+networkInfo);
        }

        obtenerSemestre(examenSelect);
        consulta();
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        txtdescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(view);
            }
        });
    }

    public void toast() {
        final RelativeLayout relativeLayout = findViewById(R.id.msgAccount);
        relativeLayout.setVisibility(View.VISIBLE);
        Button btnEntentido = findViewById(R.id.entendidoMsg);

        relativeLayout.setOnClickListener(v -> {
            //nothing
        });
        btnEntentido.setOnClickListener(v ->{
            relativeLayout.setVisibility(View.INVISIBLE);
        });
    }
    
    public void consulta(){

        String formarruta = "/"+materiaSelect+"/"+rutasemestre;
        NAME_FILE = formarruta+".jpg";
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
                                    adapter=new ImageAdapter(imagelist);
                                    viewPager2.setAdapter(adapter);
                                } else
                                    Log.e("URI Vacia", uri.toString());
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progressBarImagen.setVisibility(View.GONE);
                                /* recyclerView.setAdapter(adapter);*/
                                /*progressBar.setVisibility(View.GONE);*/
                            }
                        });
                    }
                }else {
                   /* progressBar.setVisibility(View.GONE);
                    txtsinparciales.setVisibility(View.VISIBLE);*/
                }
            }
        });
    }

    public void download(View view) {
        if (isDownloadManagerAvailable()) {
            checkSelfPermission();
        } else {
            Toast.makeText(this, "Download manager is not available", Toast.LENGTH_LONG).show();
        }
    }

    private static boolean isDownloadManagerAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    private void checkSelfPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);

        } else {
            executeDownload(imagelist);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted! Do the work
                    executeDownload(imagelist);
                } else {
                    // permission denied!
                    Toast.makeText(this, "Please give permissions ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void executeDownload(ArrayList<String> lista) {

        for (int i=0 ; i<lista.size(); i++){
            // registrer receiver in order to verify when download is complete
            registerReceiver(new DonwloadCompleteReceiver(), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(lista.get(i)));
            request.setDescription("Downloading file " + NAME_FILE);
            request.setTitle("Downloading");
            // in order for this if to run, you must use the android 3.2 to compile your app
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, NAME_FILE);

            // get download service and enqueue file
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
        }

    }

    public class DonwloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                Toast.makeText(context,"Descarga Exitosa", Toast.LENGTH_LONG).show();
                // DO SOMETHING WITH THIS FILE
            }
        }
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