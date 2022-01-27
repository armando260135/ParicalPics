package com.example.inventarioparciales;

import static com.example.inventarioparciales.VerSemestres.rutasemestre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterListSemestre extends RecyclerView.Adapter<AdapterListSemestre.ViewHolderSemestre> {

    private List<ListElementSemestre> semestresdata;
    private LayoutInflater semestreinflater;
    private Context context;

    public AdapterListSemestre(List<ListElementSemestre> itemList, Context context) {
        this.semestresdata = itemList;
        this.context = context;
        this.semestreinflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterListSemestre.ViewHolderSemestre onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = semestreinflater.inflate(R.layout.list_semestres,null);
        return new ViewHolderSemestre(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListSemestre.ViewHolderSemestre holder, int position) {
        rutasemestre = "";
        holder.binData(semestresdata.get(position));
        ListElementSemestre listElementSemestre = semestresdata.get(position);
        String semestre = listElementSemestre.getSemestre();
        holder.itemView.setOnClickListener(view -> {
            Intent datosSemestreClick = new Intent( context, ShowAllImagesFromStorage.class);
            datosSemestreClick.putExtra("semestreClick",semestre);
            context.startActivity(datosSemestreClick);
        });
    }

    @Override
    public int getItemCount() {
        return semestresdata.size();
    }

    public void setItems(List<ListElementSemestre> items){
        semestresdata = items;
    }

    public class ViewHolderSemestre  extends RecyclerView.ViewHolder{

        ImageView iconImage;
        TextView semestre,materiassemestre,abrir;

        public ViewHolderSemestre(@NonNull View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageSemestre);
            semestre = itemView.findViewById(R.id.nombreSemestre);
            materiassemestre = itemView.findViewById(R.id.nombreMateriaSemestre);
            abrir = itemView.findViewById(R.id.abrirSemestre);
        }

        void binData(final ListElementSemestre item) {
            semestre.setText(item.getSemestre());
            materiassemestre.setText(item.getMateriasemestre());
            abrir.setText(item.getAbrir());
        }

    }
}
