package com.example.pegatuga.safegas;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistroActivity extends AppCompatActivity {
    private EditText email,pass,name;
    private Button btnR, btnA;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //variables que guardan informacion
        name = (EditText) findViewById(R.id.nombre);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        //autenticacion de firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //boton de registro
        btnR = (Button) findViewById(R.id.btnRegistro);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //limpiar los espacios
                String nombre = name.getText().toString().trim();
                String correo = email.getText().toString().trim();
                String contraseña = pass.getText().toString().trim();
                if (TextUtils.isEmpty(nombre)){
                    Toast.makeText(getApplicationContext(),"Ingrese un nombre", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(getApplicationContext(),"Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(contraseña)) {
                    Toast.makeText(getApplicationContext(),"Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contraseña.length() < 6) {
                    Toast.makeText(getApplicationContext(),"Su contraseña es muy corta, como minimo usar 6 caracteres", Toast.LENGTH_LONG).show();
                    return;
                }
                //progressdialog para que el usuario sepa que esta cargando
                final ProgressDialog progressDialog = ProgressDialog.show(RegistroActivity.this,"Por favor espere","Procesando...",true);
                //validar la autenticacion de firebase
                (firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()))
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()){
                                    startActivity(new Intent(RegistroActivity.this,LoginActivity.class));
                                    Toast.makeText(getApplicationContext(),"Su registro a sido exitoso",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.e("Error",task.getException().toString());
                                    Toast.makeText(RegistroActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btnA = (Button) findViewById(R.id.btnAtras);
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
