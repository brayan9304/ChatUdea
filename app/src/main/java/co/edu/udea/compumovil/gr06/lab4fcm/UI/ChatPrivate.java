package co.edu.udea.compumovil.gr06.lab4fcm.UI;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.edu.udea.compumovil.gr06.lab4fcm.Adapter.AdaptadorRecyclerChat;
import co.edu.udea.compumovil.gr06.lab4fcm.BroadCast.NetworkChangeReceiver;
import co.edu.udea.compumovil.gr06.lab4fcm.Intefaces.conexionInterface;
import co.edu.udea.compumovil.gr06.lab4fcm.Modelo.Mensaje;
import co.edu.udea.compumovil.gr06.lab4fcm.R;
import co.edu.udea.compumovil.gr06.lab4fcm.Utilidad;

public class ChatPrivate extends AppCompatActivity implements conexionInterface {

    private static final String TAG = "ChatPivate";
    public static final String CHAT_PRIVATE = "chatPrivate";
    private Toolbar miToolbar;
    private RecyclerView recycler;
    private boolean isConnected;
    private AdaptadorRecyclerChat adaptadorRecycler;
    private RecyclerView.LayoutManager lManager;
    private FirebaseDatabase miBD;
    private DatabaseReference root;
    String usuarioActivo, usuarioActivoID;
    String emisor, receptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        miToolbar = (Toolbar) findViewById(R.id.chat_toolbar);
        setSupportActionBar(miToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            usuarioActivo = user.getDisplayName();
            usuarioActivoID = user.getUid();
        }
        miBD = FirebaseDatabase.getInstance();
        root = miBD.getReference().getRoot();

        String gruposeleccionado = getIntent().getStringExtra(CHAT_PRIVATE);
        TextView textToolbar = (TextView) miToolbar.findViewById(R.id.toolbar_subtitle);
        textToolbar.setText(gruposeleccionado);

        ArrayList<Mensaje> messages = new ArrayList<>();
        recycler = (RecyclerView) findViewById(R.id.chat_recyclerview_id);

        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        adaptadorRecycler = new AdaptadorRecyclerChat(messages);
        recycler.setAdapter(adaptadorRecycler);
    }



    public void enviarMensaje(View v) {
        TextView mensaje = (TextView) findViewById(R.id.chat_txt_mensaje_id);
        if (!mensaje.getText().toString().isEmpty()) {
            Map<String, Object> nuevoMensaje = new HashMap<>();
            String fecha = Utilidad.obtenerFechaActual(getApplicationContext());
            nuevoMensaje.put("mensaje", mensaje.getText().toString());
            nuevoMensaje.put("enviado", emisor);
            nuevoMensaje.put("recibido", receptor);
            nuevoMensaje.put("fecha", fecha);



            mensaje.setText("");

        }
    }

    @Override
    public void mostrarMensajeConexion() {
        validarConexion();
    }

    public void validarConexion() {
        ConnectivityManager conexion = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoConexion = conexion.getActiveNetworkInfo();
        FloatingActionButton btnSend = (FloatingActionButton) findViewById(R.id.chat_btn_enviar_id);
        if (infoConexion != null) {
            isConnected = infoConexion.isConnected();
        } else {
            isConnected = false;
        }
        final Snackbar sinConexion = Snackbar.make(findViewById(R.id.activity_chat), R.string.mensaje_error_conexion, Snackbar.LENGTH_INDEFINITE);
        if (!isConnected) {
            btnSend.setEnabled(false);
            sinConexion.setAction(R.string.login_snackbar_action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sinConexion.dismiss();
                }
            }).show();
        } else {
            btnSend.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

