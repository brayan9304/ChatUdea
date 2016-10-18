package co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr06.lab4fcm.Adapter.adaptadorRecyclerUsuarios;
import co.edu.udea.compumovil.gr06.lab4fcm.Modelo.UsuarioInfo;
import co.edu.udea.compumovil.gr06.lab4fcm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Usuarios extends Fragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adaptadorRecycler;
    private RecyclerView.LayoutManager lManager;

    public Usuarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_usuarios, container, false);
        List<UsuarioInfo> temp = new ArrayList<>();
        temp.add(new UsuarioInfo("jaime", UsuarioInfo.ESTADO_CONECTADO));
        temp.add(new UsuarioInfo("Andres", UsuarioInfo.ESTADO_CONECTADO));
        temp.add(new UsuarioInfo("laura", UsuarioInfo.ESTADO_DESCONECTADO));

        recycler = (RecyclerView) contenedor.findViewById(R.id.usuarios_recyclerView_id);

        lManager = new LinearLayoutManager(this.getContext());
        recycler.setLayoutManager(lManager);

        adaptadorRecycler = new adaptadorRecyclerUsuarios(temp);
        recycler.setAdapter(adaptadorRecycler);
        return contenedor;
    }

}
