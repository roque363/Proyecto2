package com.alarcon.proyecto2;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity {
    private Spinner spTipoPizza;
    private RadioGroup rdTipoMasas;
    private CheckBox checkboxQueso,checkboxJamon;

    private String textSpinner;
    private String textRadioBtn;

    private Double precioPizza = 0.00;
    private Double precioComplemento = 0.00;
    private Double precioTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        spTipoPizza = (Spinner) findViewById(R.id.spTipoPizza);
        rdTipoMasas = (RadioGroup) findViewById(R.id.rdTipoMasas);
        checkboxQueso = (CheckBox) findViewById(R.id.checkboxQueso);
        checkboxJamon = (CheckBox) findViewById(R.id.checkboxJamon);

        spTipoPizza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                textSpinner = parent.getItemAtPosition(position).toString();
                if(position == 1){
                    precioPizza = 20.00;
                }else if (position == 2){
                    precioPizza = 22.00;
                }else if (position == 3){
                    precioPizza = 25.00;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void radioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.rdMasaGruesa:
                if(checked)
                    textRadioBtn = "masa grande";
                break;
            case R.id.rdMasaDelgada:
                if(checked)
                    textRadioBtn = "masa delgada";
                break;
            case R.id.rdMasaArtesanal:
                if(checked)
                    textRadioBtn = "masa artesanal";
                break;
        }
    }

    //Toma de los complementos
    public void checkBoxClicked(View view){
        switch (view.getId()) {
            case R.id.checkboxQueso:
                CheckBox checkboxQueso = (CheckBox) view;
                if (checkboxQueso.isChecked()) {
                    precioComplemento = 8.00;
                    Toast.makeText(this, checkboxQueso.getText() + " seleccionado", Toast.LENGTH_LONG).show();
                }else {
                    precioComplemento = 0.00;
                }
            case R.id.checkboxJamon:
                CheckBox checkboxJamon = (CheckBox) view;
                if (checkboxJamon.isChecked()){
                    precioComplemento = 12.00;
                    Toast.makeText(this, checkboxJamon.getText() + " seleccionado", Toast.LENGTH_LONG).show();
                }else {
                    precioComplemento = 0.00;
                }
        }
    }

    //Mostrar el dialogo personalisado
    public void showDialog(View view){

        if (spTipoPizza.getSelectedItemPosition() == 0 ){
            Toast.makeText(this, "Tipo de pizza no seleccionada", Toast.LENGTH_LONG).show();
        }else if (rdTipoMasas.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Tipo de masa no seleccionada", Toast.LENGTH_LONG).show();
        }else{
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);
            //Titulo del allert dialog
            dialog.setTitle("Confirmacion de pedido");

            Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
            Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
            TextView textDialog = (TextView) dialog.findViewById(R.id.txtDialogo);

            precioTotal = precioPizza + precioComplemento;

            textDialog.setText("Su pedido de " + textSpinner + " con " + textRadioBtn + " a S/." + precioTotal.toString() + " + IGV está en proceso de envío");

            // Click cancel to dismiss android custom dialog box
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spTipoPizza.setSelection(0);
                    rdTipoMasas.clearCheck();
                    checkboxQueso.setChecked(false);
                    checkboxJamon.setChecked(false);

                    Toast.makeText(getApplicationContext(), "Pedido cancelado!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });

            // Your android custom dialog ok action
            // Action for custom dialog ok button click
            dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notification notification = new NotificationCompat.Builder(PedidoActivity.this)
                            .setContentTitle("notificacion de pedido")
                            .setContentText("Su pedido se ha realizado correctamente")
                            .setSmallIcon(R.drawable.ic_logo2)
                            .setColor(ContextCompat.getColor(PedidoActivity.this, R.color.primary))
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setAutoCancel(true)
                            .build();

                    // Notification manager
                    NotificationManager notificationManager = (NotificationManager) PedidoActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, notification);

                    Toast.makeText(getApplicationContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(PedidoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            dialog.show();
        }

    }


}
