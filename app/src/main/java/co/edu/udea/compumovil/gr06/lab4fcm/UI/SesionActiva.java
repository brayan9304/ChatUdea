package co.edu.udea.compumovil.gr06.lab4fcm.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.udea.compumovil.gr06.lab4fcm.Adapter.ViewPageAdapater;
import co.edu.udea.compumovil.gr06.lab4fcm.R;
import co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments.Grupos;
import co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments.Usuarios;

public class SesionActiva extends AppCompatActivity {

    private static final String TAG = "SesionActiva";

    private Toolbar miToolbar;
    private TabLayout miTabLayout;
    private ViewPager miViewPager;

    private FirebaseAuth mAuth;
    private FirebaseUser usuarioActivo;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_activa);

        miToolbar = (Toolbar) findViewById(R.id.sesionActiva_toolbar);
        setSupportActionBar(miToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        miViewPager = (ViewPager) findViewById(R.id.sesionActiva_viewPager);
        configurarViewPager(miViewPager);

        miTabLayout = (TabLayout) findViewById(R.id.sesionActiva_TabLayout);
        miTabLayout.setupWithViewPager(miViewPager);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                usuarioActivo = mAuth.getCurrentUser();
                if (usuarioActivo != null) {

                } else {
                    Intent cerrar = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(cerrar);
                    finish();
                }
            }
        };


    }

    public void configurarViewPager(ViewPager vP) {
        ViewPageAdapater adaptador = new ViewPageAdapater(getSupportFragmentManager());
        adaptador.agregarFragment(new Grupos(), getString(R.string.titulo_fragment_grupos));
        adaptador.agregarFragment(new Usuarios(), getString(R.string.titulo_fragment_usuarios));
        vP.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_options_cerrarSesion:
                Intent cerrar = new Intent(this, MainActivity.class);
                startActivity(cerrar);
                mAuth.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
