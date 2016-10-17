package co.edu.udea.compumovil.gr06.lab4fcm.UI;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.udea.compumovil.gr06.lab4fcm.Adapter.ViewPageAdapater;
import co.edu.udea.compumovil.gr06.lab4fcm.R;
import co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments.Grupos;
import co.edu.udea.compumovil.gr06.lab4fcm.UI.Fragments.Usuarios;

public class SesionActiva extends AppCompatActivity {

    private static final String TAG = "SesionActiva";

    private Toolbar miToolbar;
    private TabLayout miTabLayout;
    private ViewPager miViewPager;

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
                Log.e(TAG, "onOptionsItemSelected: Click");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
