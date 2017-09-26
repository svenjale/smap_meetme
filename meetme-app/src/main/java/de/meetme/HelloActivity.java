package de.meetme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.meetme.*;

public class HelloActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "HelloActivity";
    private static final String HOSTNAME = "<here your IP or hostname>";
    private static final int PORT = 8087;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);


        Log.e(TAG, "run client");

        RestClient httpclient = new RestClient("http://" + HOSTNAME + ":" + PORT);
        try {
            Person p = httpclient.getApiService().getPerson(1);
            Log.e(TAG, p.toString());

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView = (TextView) findViewById(R.id.textViewSignin);

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();        //Email wird aus Editfeld geholt
        String password = editTextPassword.getText().toString().trim();  //Passwort wird aus Editfeld geholt

        if (TextUtils.isEmpty(email)) {                                    //Email Textfeld ist leer
            Toast.makeText(this, "Bitte Email eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (TextUtils.isEmpty(password)) {                                  //Passwort Textfeld ist leer
            Toast.makeText(this, "Bitte Passwort eintragen", Toast.LENGTH_SHORT).show();    //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(HelloActivity.this, "Erfolgreich registriert", Toast.LENGTH_SHORT).show();                                                 //Benutzer ist erfolgreich mit Email und Passwort registriert und eingeloggt, öffne Profil Aktivität
                            if (firebaseAuth.getCurrentUser() != null) { //Profilseite kann geöffnet werden
                                finish();


                            } else {
                                Toast.makeText(HelloActivity.this, "Benutzer konnte nicht registriert werden", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                });
    }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            registerUser();
        }
        Intent regisintent = new Intent(HelloActivity.this, Login_Activity.class);
        startActivity(regisintent);

    }

}


