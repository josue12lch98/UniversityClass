package com.example.universityclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.universityclass.entidades.Profesores;
import com.example.universityclass.entidades.RolUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class DisponibilidadActivity extends AppCompatActivity {
    Profesores profesor;
    int rol_idrol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibilidad);
        Intent intent=getIntent();
        ImageView imageView=this.findViewById(R.id.imagenProfesor);
        TextView nombreProfesorView=this.findViewById(R.id.nombreProfesor);
        TextView descripcionProfesor=this.findViewById(R.id.descripcionProfesor);

        RolUser rolUser=leerArchivoTextRol();
        rol_idrol=rolUser.getRol_idrol();

          profesor =(Profesores) intent.getSerializableExtra("profesor");
        String foto_url;

        nombreProfesorView.setText(profesor.getNombre()+" "+ profesor.getApellido());

        String descripcion;

        if(profesor.getDescripcion()==null){
            descripcion="Hola, será un placer poder ayudarte en esta travesía de aprender";}
        else{
            descripcion=profesor.getDescripcion();
        }
        Log.e("descripcion",descripcion);
        descripcionProfesor.setText(descripcion);

        if(profesor.getFoto_url()==null){
            foto_url="https://file2.instiz.net/data/file2/2016/06/20/b/7/c/b7cd93926e00d879f265faec29cf8257.jpg";}
        else{
            foto_url=profesor.getFoto_url();
        }


        Picasso.get().load(foto_url).into(imageView);
    }
   public void asesoriaPersonalizadaOnClick(View view){
        Intent intent=new Intent(DisponibilidadActivity.this,AsesoriaPersonalizada.class);
       intent.putExtra("profesor",profesor);
        startActivity(intent);
}
public void clasesGrupalesOnClick( View view){
    Intent intent=new Intent(DisponibilidadActivity.this,ClasesGrupalesActivity.class);
    intent.putExtra("profesor",profesor);
    startActivity(intent);

}


    Context context = this;

    public RolUser leerArchivoTextRol() {
        String fileName="rol_idrol";
        RolUser rolUser = null;

        try (FileInputStream fileInputStream = openFileInput(fileName);
             FileReader fileReader = new FileReader(fileInputStream.getFD());
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            Gson gson = new Gson();

            String line = bufferedReader.readLine();

            rolUser = gson.fromJson(line, RolUser.class);



           /* for(Trabajo t: arregloTrabajo){
                Log.d("infoApp",t.getJobTitle());
            }*/
            return rolUser;
        } catch (IOException e) {
            e.printStackTrace();
            return rolUser;
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (rol_idrol == 1) {
            menu.setGroupVisible(R.id.group_rol_1, true);
            menu.findItem(R.id.moreOptionsId).setVisible(true);

        } else {

        }
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.listaCursos):
                startActivity(new Intent(this, ListaDeCursosActivity.class));
                return true;
            case (R.id.moreOptionsId):
                if (rol_idrol==1) {
                    View menuitem = findViewById(R.id.moreOptionsId);
                    PopupMenu popupMenu = new PopupMenu(this, menuitem);
                    popupMenu.getMenuInflater().inflate(R.menu.poup_menu_profe, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.cerrarCesionId:
                                    Log.d("infoapp", "cerrando sesion");

                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(new Intent(context, MainActivity.class));
                                    return true;

                                case R.id.agregarclaseID:
                                    startActivity(new Intent(context, CrearClaseGrupal.class));
                                    return true;

                                default:
                                    startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                                    return true;

                            }
                        }
                    });
                    popupMenu.show();
                }else{
                    View menuitem = findViewById(R.id.moreOptionsId);
                    PopupMenu popupMenu = new PopupMenu(this, menuitem);
                    popupMenu.getMenuInflater().inflate(R.menu.poup_menu, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.cerrarCesionId:
                                    Log.d("infoapp", "cerrando sesion");

                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(new Intent(context, MainActivity.class));
                                    return true;

                                default:
                                    startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                                    return true;

                            }
                        }
                    });
                    popupMenu.show();


                }
                return true;
            case (R.id.listaAsesoriasID):
                startActivity(new Intent(context, ListaAsesoriaPersoActivity.class));
                return true;
            default:
                return true;
        }

        //


    }



}