package com.sferea.mati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.getmati.mati_sdk.MatiButton;
import com.getmati.mati_sdk.MatiLoginButton;
import com.getmati.mati_sdk.MatiSdk;
import com.getmati.mati_sdk.Metadata;
import com.getmati.mati_sdk.ui.data_prefetch.DataPrefetchActivity;

public class MainActivity extends AppCompatActivity {

    MatiButton mMatiButton;
    int REQUEST_CODE = 2576;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Mati example
        mMatiButton = findViewById(R.id.matiKYCButton);
        mMatiButton.setParams("CLIENT_ID",
                "FLOW_ID",//
                "Título",
                new Metadata.Builder()  //Our metadata or JSON to send us to backend
                        .with("KEY_N","valueN")
                        .with("USER", "ID_USER")
                        .build());

        //TODO: Custom example

        findViewById(R.id.customButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlow("CLIENT_ID", "FLOW_ID",
                        new Metadata.Builder() //Our metadata or JSON to send us to backend
                                .with("KEY_N","valueN")
                                .with("Usuario", "ID_USER")
                                .build());

            }
        });


    }

    //This function will be executed with its own call
    public void startFlow( String clientId, String flowId, Metadata metadata){
        Intent intent = new Intent(this, DataPrefetchActivity.class);
        intent.putExtra("ARG_CLIENT_ID", clientId);
        intent.putExtra("ARG_FLOW_ID", flowId);
        intent.putExtra("ARG_METADATA",Metadata.toJson(metadata));
        startActivityForResult(intent,REQUEST_CODE);//Start MatiActivity

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MatiSdk.REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText( this,"SUCCESS!!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText( this,"CANCELLED!!!", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

//