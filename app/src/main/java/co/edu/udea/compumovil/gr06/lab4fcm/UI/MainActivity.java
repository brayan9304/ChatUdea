package co.edu.udea.compumovil.gr06.lab4fcm.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.edu.udea.compumovil.gr06.lab4fcm.BroadCast.NetworkChangeReceiver;
import co.edu.udea.compumovil.gr06.lab4fcm.Intefaces.conexionInterface;
import co.edu.udea.compumovil.gr06.lab4fcm.Intefaces.dialogEvent;
import co.edu.udea.compumovil.gr06.lab4fcm.R;
import co.edu.udea.compumovil.gr06.lab4fcm.Utilidad;

public class MainActivity extends AppCompatActivity implements dialogEvent, conexionInterface {

    private DialogFragmentUser login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean isConnected;
    private BroadcastReceiver conexionReceiver;
    private static final String TAG = "MainActivity";
    public static final String PASSWORD_FIREBASE_ERROR = "ERROR_WRONG_PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conexionReceiver = new NetworkChangeReceiver();
        NetworkChangeReceiver.registrarReceiver(this);
        ConnectivityManager conexion = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoConexion = conexion.getActiveNetworkInfo();

        if (infoConexion != null) {
            isConnected = infoConexion.isConnected();
        }

        if (!isConnected) {
            Button iniciar = (Button) findViewById(R.id.login_btn_iniciarCorreoYContra_id);
            iniciar.setEnabled(false);
            final Snackbar sinConexion = Snackbar.make(findViewById(R.id.activity_main), R.string.mensaje_error_conexion, Snackbar.LENGTH_INDEFINITE);
            sinConexion.setAction(R.string.login_snackbar_action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sinConexion.dismiss();
                }
            }).show();
        }

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

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

    public void abrirDialogLogin(View v) {
        if (login == null) {
            login = new DialogFragmentUser();
            login.show(getSupportFragmentManager(), "Login");
            return;
        }
        login.show(getSupportFragmentManager(), "Login");
    }

    @Override
    public void iniciarSesion(EditText correo, final EditText clave) {
        String correoValor = correo.getText().toString();
        String claveValor = clave.getText().toString();
        if (Utilidad.validarCampoVacio(correo, getApplicationContext()) & Utilidad.validarCampoVacio(clave, getApplicationContext())) {
            if (Utilidad.validarCampoCorreo(correo, getApplicationContext()) & Utilidad.validarContraseña(clave, getApplicationContext())) {
                mAuth.signInWithEmailAndPassword(correoValor, claveValor).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), R.string.mensaje_success_login, Toast.LENGTH_SHORT).show();
                            login.dismiss();
                        } else {
                            Utilidad.validarConexionFirebase(task, getApplicationContext(), clave);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void mostrarMensajeConexion() {
        ConnectivityManager conexion = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoConexion = conexion.getActiveNetworkInfo();

        if (infoConexion != null) {
            isConnected = infoConexion.isConnected();
        } else {
            isConnected = false;
        }
        Button iniciar = (Button) findViewById(R.id.login_btn_iniciarCorreoYContra_id);
        final Snackbar sinConexion = Snackbar.make(findViewById(R.id.activity_main), R.string.mensaje_error_conexion, Snackbar.LENGTH_LONG);
        if (!isConnected) {
            iniciar.setEnabled(false);
            sinConexion.setAction(R.string.login_snackbar_action, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sinConexion.dismiss();
                }
            }).show();
        } else {
            iniciar.setEnabled(true);
        }
    }
}
