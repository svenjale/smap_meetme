package de.meetme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class createevent extends Activity implements View.OnClickListener  {
// implements View.OnClickListener wieder einfügen
    private Button button8;
    private EditText editText5;              //Eventname
    private EditText editText8;             //Beschreibung
    private EditText editText10;            //Ort
    private EditText editText11;            //Datum
    private EditText editText13;//Uhrzeit

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseEvents;
    private DatabaseReference databaseEventteilnehmer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createevent_layout);


      databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        databaseEventteilnehmer = FirebaseDatabase.getInstance().getReference("eventteilnehmer");




        editText5 = (EditText) findViewById(R.id.editText5);
        editText8 = (EditText) findViewById(R.id.editText8);
        editText10 = (EditText) findViewById(R.id.editText10);
        editText11 = (EditText) findViewById(R.id.editText11);
        editText13 = (EditText) findViewById(R.id.editText13);
        button8 = (Button) findViewById(R.id.button8);


        button8.setOnClickListener(this);


    }


    private void createevent() {
        String eventname = editText5.getText().toString().trim();               //Eventname wird aus Editfeld geholt
        String beschreibung = editText8.getText().toString().trim();            //Beschreibung wird aus Editfeld geholt
        String ort = editText10.getText().toString().trim();                    //Ort wird aus Editfeld geholt
        String datum = editText11.getText().toString().trim();                  //Datum wird aus Editfeld geholt
        String uhrzeit = editText13.getText().toString().trim();               //Uhrzeit wird aus Editfeld geholt
        String organisatorID = firebaseAuth.getInstance().getCurrentUser().getUid(); // ID des aktuell eingeloggten Users wird als Organisator gespeichert;

        if (TextUtils.isEmpty(eventname)) {                                                          //Eventname Textfeld ist leer
            Toast.makeText(this, "Bitte Name des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (TextUtils.isEmpty(beschreibung)) {                                                              //Beschreibung Textfeld ist leer
            Toast.makeText(this, "Bitte Beschreibung des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(ort)) {                                                               //Ort Textfeld ist leer
            Toast.makeText(this, "Bitte Ort des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(datum)) {                                                              //Datum Textfeld ist leer
            Toast.makeText(this, "Bitte Datum des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }
        if (TextUtils.isEmpty(uhrzeit)) {                                                                //Uhrzeit Textfeld ist leer
            Toast.makeText(this, "Bitte Uhrzeit des Events eintragen", Toast.LENGTH_SHORT).show();     //wenn Feld leer ist, wird Ausführung unterbrochen
            return;
        }

        if (!TextUtils.isEmpty(eventname)){

            String id = databaseEvents.push().getKey();
            Event event = new Event(id, eventname, beschreibung, ort, datum, uhrzeit, organisatorID);
            databaseEvents.child(id).setValue(event);
            databaseEventteilnehmer.child(id).child("organisator").setValue(organisatorID); // Johann: wahrscheinlich Problem, sobald mehrere Teilnehmer --> prüfen, sobald Eventinfos getestet werden kann
            Toast.makeText(this, "Das Event wurde erstellt", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public void onClick(View view) {
        if (view == button8) {
            createevent();
        }
        Intent regisintent = new Intent(createevent.this, eventlist.class);
        startActivity(regisintent);
    }
}
