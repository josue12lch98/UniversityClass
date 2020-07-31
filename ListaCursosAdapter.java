package com.example.universityclass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityclass.entidades.Cursos;
import com.example.universityclass.entidades.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ListaCursosAdapter extends RecyclerView.Adapter<ListaCursosAdapter.UsuariosViewHolder> {
private Cursos[] cursos;
private Context contexto;

public ListaCursosAdapter(Cursos[] cursos, Context contexto){
    this.cursos = cursos;
    this.contexto=contexto;

}
public static class UsuariosViewHolder extends RecyclerView.ViewHolder{
 public TextView textView;
 public CardView cardView;
 public ImageView imageView;
 public TextView textViewNombreProf;
 public TextView textViewFecha;
 public Button inscribirseButton;

    public UsuariosViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textView =itemView.findViewById(R.id.EstudianteText);
        this.cardView=itemView.findViewById(R.id.CardViewClasesGrupales);
        this.imageView=itemView.findViewById(R.id.imageViewProfesor);
        this.textViewNombreProf=itemView.findViewById(R.id.temaText);
        this.textViewNombreProf=itemView.findViewById(R.id.temaText);
        this.textViewFecha=itemView.findViewById(R.id.fechaText);
    this.inscribirseButton=itemView.findViewById(R.id.inscribirseButton);
    }
}

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_cursos,parent,false);
        UsuariosViewHolder usuariosViewHolder=new UsuariosViewHolder(itemView);

        return usuariosViewHolder;
    }

    @Override
    public void onBindViewHolder( UsuariosViewHolder holder, int position) {
        final Cursos c = cursos[position];
        holder.textView.setText(c.getNombre());
        String foto_url;
        if(c.getFoto_url()==null){
            foto_url="https://file2.instiz.net/data/file2/2016/06/20/b/7/c/b7cd93926e00d879f265faec29cf8257.jpg";

        }
        else
            {
        foto_url=c.getFoto_url();
        }

        Picasso.get().load(foto_url).into(holder.imageView);

        holder.textViewNombreProf.setText(c.getNombreProfesor()+" "+c.getApellidoProfesor() );
        holder.textViewFecha.setText("Fecha: " + c.getFecha());

holder.inscribirseButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("UniversityClass");
        String uid=FirebaseAuth.getInstance().getUid();
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String correo=user.getEmail();
        Usuario usuario=new Usuario();
        usuario.setCorreo(correo);
        usuario.setNombre(user.getDisplayName());
        databaseReference.child("Profesores/listaprofesores/"+c.getIdProfesor()+"/asesoriasGrupales/listaAsesorias").child(c.getKey()).child("Inscritos").push().setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
                alertDialog.setTitle("Inscripción exitosa");
                alertDialog.setMessage("Se ha insrcito exitosamente en el curso: "+c.getNombre());
                alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });
        databaseReference.child("usuarios/"+user.getUid()).child("cursos/listacursos").child(c.getKey()).setValue(c);

    }
});
    }



    @Override
    public int getItemCount() {
        return cursos.length;
    }
}
