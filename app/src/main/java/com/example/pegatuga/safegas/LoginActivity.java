package com.example.pegatuga.safegas;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email,password;
    private Button olvidoContraseña,registro,inSesion;

    FirebaseAuth firebaseAuth;

    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //variables que guardan la informacion de los edittext
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        //autenticacion de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //boton de inicio de sesion
        inSesion = (Button) findViewById(R.id.inSesion);
        inSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //quitar los espacios y verificacion de datos
                String correo = email.getText().toString().trim();
                String clave = password.getText().toString().trim();
                //notas para el usuario para que sepa que hacer
                if (TextUtils.isEmpty(correo)){
                    Toast.makeText(getApplicationContext(),"Ingrese un correo por favor",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(clave)){
                    Toast.makeText(getApplicationContext(),"Ingrese una contraseña por favor",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (clave.length()<6){
                    Toast.makeText(getApplicationContext(),"Su contraseña es muy corta, Ingrese 6 caracteres como minimo",Toast.LENGTH_LONG).show();
                    return;
                }
                //ProgressDialog para que el usuario sepa que esta cargando
                final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this,"Por favor espere...","Procesando...",true);
                (firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    //saltar de activity
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    //recadito de bienvenita
                                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.e("ERROR",task.getException().toString());
                                    Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT);
                                }
                            }
                        });
                //boton que abre la pantalla de registro
                registro = (Button) findViewById(R.id.registro);
                registro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
                    }
                });
                //boton que abre la pantalla de recuperacion de contraseña
                olvidoContraseña = (Button) findViewById(R.id.olvidoContraseña);
                olvidoContraseña.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,RecuperarActivity.class));
                    }
                });
            }
        });
    }
}
