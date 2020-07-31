package com.example.universityclass;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.universityclass.entidades.AsesoriaGrupal;
import com.example.universityclass.entidades.AsesoriaPersonalizadaEntity;
import com.example.universityclass.entidades.Cursos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ListaAsePersonalizadaAdapter extends RecyclerView.Adapter<ListaAsePersonalizadaAdapter.AsesoriaPersonalizadasViewHolder> {

    private AsesoriaPersonalizadaEntity[] asesoriaPersonalizadas;
    private Context contexto;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("UniversityClass");

    public ListaAsePersonalizadaAdapter(AsesoriaPersonalizadaEntity[] asesoriaPersonalizadas, Context contexto) {
        this.asesoriaPersonalizadas = asesoriaPersonalizadas;
        this.contexto = contexto;

    }

    public static class AsesoriaPersonalizadasViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTema;
        public TextView textViewFecha;
        public TextView textViewEstudiante;
        public CardView cardView;
        public Button buttonRechazar;
        public Button buttonAceptar;
        public TextView textViewNombrePerso;

        public AsesoriaPersonalizadasViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewEstudiante = itemView.findViewById(R.id.EstudianteTextView);
            this.textViewNombrePerso = itemView.findViewById(R.id.nombreEstudianteText2);
            this.textViewTema = itemView.findViewById(R.id.temaTextView);
            this.textViewFecha = itemView.findViewById(R.id.fechaTextView);
            this.cardView = itemView.findViewById(R.id.CardViewClasesGrupales);
            this.buttonAceptar = itemView.findViewById(R.id.aceptarButton);
            this.buttonRechazar = itemView.findViewById(R.id.rechazarButton);

        }
    }

    @NonNull
    @Override
    public AsesoriaPersonalizadasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(contexto).inflate(R.layout.item_rv_profesor, parent, false);
        AsesoriaPersonalizadasViewHolder usuariosViewHolder = new AsesoriaPersonalizadasViewHolder(itemView);
        return usuariosViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AsesoriaPersonalizadasViewHolder holder, int position) {
        final AsesoriaPersonalizadaEntity c = asesoriaPersonalizadas[position];
        holder.textViewTema.setText(c.getDescripcion());

        holder.textViewFecha.setText(c.getFecha());

        String foto_url;
     /*   if(c.getFoto_url()==null){
            foto_url="https://file2.instiz.net/data/file2/2016/06/20/b/7/c/b7cd93926e00d879f265faec29cf8257.jpg";

        }else{
            foto_url=c.getFoto_url();
        }

        Picasso.get().load(foto_url).into(holder.imageView);
*/
        if (c.getRol_idrol() == 1) {

            holder.textViewEstudiante.setText(c.getNombreUsuario());
            holder.buttonRechazar.setOnClickListener(new View.OnClickListener(

            ) {
                @Override
                public void onClick(View v) {


                    databaseReference.
                            child("Profesores/listaprofesores/").child(c.getIdProfesor() + "/asesoriasPersonalizadas/" + c.getKey()).
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().removeValue();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                    AsesoriaPersonalizadaEntity a= c;
                    a.setEsAceptada(false);
                    a.setEsRespondida(true);
                    databaseReference.child("usuarios/"+c.getIdUsuario()+"/solicitudes/"+c.getKey()).setValue(a);

                }


            });

            holder.buttonAceptar.setOnClickListener(new View.OnClickListener(

            ) {
                @Override
                public void onClick(View v) {



                    AsesoriaPersonalizadaEntity a= c;
                    a.setEsAceptada(true);
                    a.setEsRespondida(true);
                    databaseReference.child("usuarios/"+c.getIdUsuario()+"/solicitudes/"+c.getKey()).setValue(a);
                    databaseReference.child("Profesores/listaprofesores/"+c.getIdUsuario()+"/asesoriasPersonalizadas/"+c.getKey()).setValue(a);

                }


            });


        } else if (c.getRol_idrol() == 0) {
            holder.textViewEstudiante.setText(c.getNombreProfesor());
            holder.textViewNombrePerso.setText("Profesor");
            holder.buttonRechazar.setText("Borrar");

            if (c.isEsRespondida()) {
                if (c.isEsAceptada() == true) {
                    holder.buttonAceptar.setText("Aceptada");
                    holder.buttonAceptar.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.buttonAceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });
                    holder.buttonRechazar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
                            alertDialog.setTitle("No se puede borrar");
                            alertDialog.setMessage("El profesor ya aceptó la asesoría ya no es posible borrarla");
                            alertDialog.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alertDialog.show();

                        }
                    });
                } else {
                    holder.buttonAceptar.setText("Rechazada");
                    holder.buttonAceptar.setTextColor(Color.parseColor("#FF0000"));
                    holder.buttonAceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                        }
                    });
                    holder.buttonRechazar.setOnClickListener(new View.OnClickListener(

                    ) {
                        @Override
                        public void onClick(View v) {

                            databaseReference.
                                    child("usuarios/" + FirebaseAuth.getInstance().getUid() + "/solicitudes/" + c.getKey())
                                    .addValueEventListener(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                   dataSnapshot.getRef().removeValue();

                                                               }

                                                               @Override
                                                               public void onCancelled(@NonNull DatabaseError databaseError) {

                                                               }
                                                           }

                                    );
                            databaseReference.
                                    child("Profesores/listaprofesores/"
                                            + FirebaseAuth.getInstance().getUid() + "/asesoriasPersonalizadas/" + c.getIdProfesor()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().removeValue();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                    });
                }


            } else {
                holder.buttonAceptar.setText("Esperando Respuesta");
                holder.buttonAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }

                });
                holder.buttonRechazar.setOnClickListener(new View.OnClickListener(

                ) {
                    @Override
                    public void onClick(View v) {
                        databaseReference.
                                child("usuarios/" + c.getIdUsuario() + "/solicitudes/" + c.getKey())
                                .addValueEventListener(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                               dataSnapshot.getRef().removeValue();

                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                                           }
                                                       }

                                );
                        databaseReference.
                                child("Profesores/listaprofesores/" + c.getIdProfesor() + "/asesoriasPersonalizadas/" + c.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().removeValue();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }


        }


    }


    @Override
    public int getItemCount() {
        return asesoriaPersonalizadas.length;
    }
}


