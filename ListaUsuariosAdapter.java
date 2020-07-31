package com.example.universityclass;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityclass.entidades.Profesores;
import com.squareup.picasso.Picasso;

public class ListaUsuariosAdapter extends RecyclerView.Adapter<ListaUsuariosAdapter.UsuariosViewHolder> {
private static Profesores[] profesores;
private static Context contexto;
public static int mPosition;

public ListaUsuariosAdapter(Profesores[] profesores, Context contexto){
    this.profesores = profesores;
    this.contexto=contexto;

}
public static class UsuariosViewHolder extends RecyclerView.ViewHolder{
 public TextView textView;
 public CardView cardView;
 public ImageView imageView;
 public TextView textViewNombreProf;
 public Button buttonDisponibilidad;

    public UsuariosViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView =itemView.findViewById(R.id.fechaText);
        this.cardView=itemView.findViewById(R.id.CardViewClasesGrupales);
        this.imageView=itemView.findViewById(R.id.imageViewProfesor);
        this.textViewNombreProf=itemView.findViewById(R.id.temaText);
        this.buttonDisponibilidad=itemView.findViewById(R.id.inscribirseButton);


    }
}

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_rm,parent,false);
UsuariosViewHolder usuariosViewHolder=new UsuariosViewHolder(itemView);


        return usuariosViewHolder;
    }

    @Override
    public void onBindViewHolder(UsuariosViewHolder holder, final int position) {
Profesores u = profesores[position];
if (u.getCurso()!=null){
    holder.textView.setText(u.getCurso());
}
else{
    holder.textView.setText("matemática");
}

String foto_url;

if(u.getFoto_url()==null){
    foto_url="https://file2.instiz.net/data/file2/2016/06/20/b/7/c/b7cd93926e00d879f265faec29cf8257.jpg";

}else{
    foto_url=u.getFoto_url();
}

        Picasso.get().load(foto_url).into(holder.imageView);

holder.textViewNombreProf.setText(u.getNombre()+" "+u.getApellido() );

        holder.buttonDisponibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(contexto,DisponibilidadActivity.class);
                Log.d("posición",String.valueOf(position));
                Profesores profesor =profesores[position];
                intent.putExtra("profesor",profesor);
                contexto.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return profesores.length;
    }
}
