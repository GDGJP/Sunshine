package com.example.ricardo.sunshine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }

    //Onde você coloca os menus a serem utilizados sejam eles menus de configurações ou de ações
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //O que acontece ao selecionar determinado item do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //Colocando o SettngsActivity para aparecer na tela principal.
        //Mudei para switch, no caso de menus acho melhor
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.location_map:
                openPreferredLocationInMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void openPreferredLocationInMap() {
        //Pega a localização de acordo com as preferencias do usuario;
        String location = getString(R.string.pref_location_default);
        //https://developer.android.com/guide/components/intents-common.html#Maps
        //O formato de URI de dados foi da página da documentação acima, onde pode-se anexar um código postal como parametros de query.
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();
        //Abrindo uma nova intent.
        Intent viewOnMapIntent = new Intent(Intent.ACTION_VIEW);
        viewOnMapIntent.setData(geoLocation);
        if (viewOnMapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(viewOnMapIntent);
        } else {
            Log.e(LOG_TAG, "Couldn't open map to location: " + location + ", no available Intent");
        }
    }


}
