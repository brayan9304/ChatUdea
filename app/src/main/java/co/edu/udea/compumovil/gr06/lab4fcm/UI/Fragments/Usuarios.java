package co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private DatabaseReference myRef;
    private List<UsuarioInfo> users;
    private FirebaseAuth mAuth;

    public Usuarios() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contenedor = inflater.inflate(R.layout.fragment_usuarios, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        lManager = new LinearLayoutManager(this.getContext());
        recycler = (RecyclerView) contenedor.findViewById(R.id.usuarios_recyclerView_id);

        recycler.setLayoutManager(lManager);
        return contenedor;
    }

    @Override
    public void onStart(){
        super.onStart();
        users = new ArrayList<>();
        DatabaseReference mensajeRef =  myRef.child(UsuarioInfo.CHILD);
        mensajeRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                users.add(dataSnapshot.getValue(UsuarioInfo.class));
                adaptadorRecycler = new adaptadorRecyclerUsuarios(users);
                recycler.setAdapter(adaptadorRecycler);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
