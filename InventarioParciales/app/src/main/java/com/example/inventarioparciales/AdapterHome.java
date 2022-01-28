package com.example.inventarioparciales;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolderHome> {

    ArrayList<MateriasHome> listHome;

    public AdapterHome(ArrayList<MateriasHome> listHome) {
        this.listHome = listHome;
    }

    @NonNull
    @Override
    public ViewHolderHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_home,parent,false);
        return new ViewHolderHome(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHome holder, int position) {
        holder.txtnombre.setText(listHome.get(position).getNombre());
        //holder.txtinformacion.setText(listHome.get(position).getInfo());
        holder.foto.setImageResource(listHome.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listHome.size();
    }

    public class ViewHolderHome extends RecyclerView.ViewHolder {
        TextView txtnombre,txtinformacion;
        ImageView foto;

        public ViewHolderHome(@NonNull View itemView) {
            super(itemView);
            txtnombre = itemView.findViewById(R.id.idNombre);
            //txtinformacion = itemView.findViewById(R.id.idInfo);
            foto = itemView.findViewById(R.id.idImagen);
        }
    }
}
