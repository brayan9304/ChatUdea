package co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import co.edu.udea.compumovil.gr06.lab4fcm.Adapter.AdaptadorRecyclerGrupos;
import co.edu.udea.compumovil.gr06.lab4fcm.R;

public class Grupos extends Fragment implements View.OnClickListener {

    //private ListView miListView;
    private RecyclerView recycler;
    private RecyclerView.Adapter adaptadorRecycler;
    private RecyclerView.LayoutManager lManager;
    private FirebaseDatabase miBD;
    private DatabaseReference grupos;
    private String nuevoGrupo;

    public Grupos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_grupos, container, false);
        FloatingActionButton addGrupo = (FloatingActionButton) contenedor.findViewById(R.id.grupos_floatingbtn_addgrupo_id);
        addGrupo.setOnClickListener(this);

        nuevoGrupo = "";
        miBD = FirebaseDatabase.getInstance();
        grupos = miBD.getReference();
        //miListView = (ListView)contenedor.findViewById(R.id.grupos_listView_id);
        ArrayList<String> valorestemp = new ArrayList<>();
        valorestemp.add("1");
        valorestemp.add("21");
        valorestemp.add("41");
        valorestemp.add("14");
        valorestemp.add("11");
        valorestemp.add("2431");
        valorestemp.add("131");
        valorestemp.add("15");
        valorestemp.add(" 43 dfd dhola1");


        //myAdaptadorGrupos t = new myAdaptadorGrupos(getActivity().getApplicationContext(),valorestemp);
        //miListView.setAdapter(t);

        recycler = (RecyclerView) contenedor.findViewById(R.id.grupos_listView_id);

        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        adaptadorRecycler = new AdaptadorRecyclerGrupos(valorestemp);
        recycler.setAdapter(adaptadorRecycler);


        return contenedor;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.grupos_floatingbtn_addgrupo_id:
                agregarGrupo().show();
                Toast.makeText(getContext(), nuevoGrupo, Toast.LENGTH_SHORT).show();
                nuevoGrupo = "";
                break;
        }
    }

    public AlertDialog.Builder agregarGrupo() {
        AlertDialog.Builder grupo = new AlertDialog.Builder(getActivity())
                .setTitle("Grupo")
                .setMessage("ingresa un nuevo grupo");
        final EditText txtGrupo = new EditText(this.getContext());
        grupo.setView(txtGrupo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nuevoGrupo = txtGrupo.getText().toString();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        return grupo;
    }
}
