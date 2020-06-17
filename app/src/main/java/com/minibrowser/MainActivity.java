package com.minibrowser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {


    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    EditText url;
    Button search, cancle;
    Button backward, forward, home, reload, mic;
    Button google, youtube, wikipedia, cricbuzz;
    Button amazon, flipkart, snapdeal, clubfactory, aliexpress, shopclus, ebay, mistore;
    Button facebook, instagram, twitter, snapchat;
    Button paytm, phonepe,gpay, freecharge;
    Button dailyhunt, bbcnews, gnews, ndtv;
    Button ola, oyo, goibibo, zomato;
    private long backpress;
    private Toast backtoast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CheckConnection checkConnection = new CheckConnection();
        if(checkConnection.isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT);
        }
        else {
            startActivity(new Intent(MainActivity.this, NoInternet.class));
        }


        url = findViewById(R.id.editText3);
        search = findViewById(R.id.button41);
        cancle = findViewById(R.id.button);
        backward = findViewById(R.id.button30);
        forward = findViewById(R.id.button31);
        home = findViewById(R.id.button33);
        reload = findViewById(R.id.button32);
        mic = findViewById(R.id.button40);

        google = findViewById(R.id.button1);
        youtube = findViewById(R.id.button2);
        wikipedia = findViewById(R.id.button3);
        cricbuzz = findViewById(R.id.button4);

        amazon = findViewById(R.id.button5);
        flipkart = findViewById(R.id.button6);
        snapdeal = findViewById(R.id.button7);
        clubfactory = findViewById(R.id.button8);
        aliexpress = findViewById(R.id.button9);
        shopclus = findViewById(R.id.button10);
        ebay = findViewById(R.id.button11);
        mistore = findViewById(R.id.button12);

        facebook = findViewById(R.id.button13);
        instagram = findViewById(R.id.button14);
        snapchat = findViewById(R.id.button15);
        twitter = findViewById(R.id.button16);

        paytm = findViewById(R.id.button17);
        phonepe = findViewById(R.id.button18);
        gpay = findViewById(R.id.button19);
        freecharge = findViewById(R.id.button20);

        dailyhunt = findViewById(R.id.button21);
        bbcnews = findViewById(R.id.button22);
        gnews = findViewById(R.id.button23);
        ndtv = findViewById(R.id.button24);

        oyo = findViewById(R.id.button25);
        ola = findViewById(R.id.button26);
        goibibo = findViewById(R.id.button27);
        zomato = findViewById(R.id.button28);

        cancle.setOnClickListener(this);
        mic.setOnClickListener(this);
        search.setOnClickListener(this);
        google.setOnClickListener(this);
        youtube.setOnClickListener(this);
        wikipedia.setOnClickListener(this);
        cricbuzz.setOnClickListener(this);
        amazon.setOnClickListener(this);
        flipkart.setOnClickListener(this);
        snapdeal.setOnClickListener(this);
        clubfactory.setOnClickListener(this);
        aliexpress.setOnClickListener(this);
        shopclus.setOnClickListener(this);
        ebay.setOnClickListener(this);
        mistore.setOnClickListener(this);
        facebook.setOnClickListener(this);
        instagram.setOnClickListener(this);
        twitter.setOnClickListener(this);
        snapchat.setOnClickListener(this);
        paytm.setOnClickListener(this);
        phonepe.setOnClickListener(this);
        gpay.setOnClickListener(this);
        freecharge.setOnClickListener(this);
        dailyhunt.setOnClickListener(this);
        bbcnews.setOnClickListener(this);
        gnews.setOnClickListener(this);
        ndtv.setOnClickListener(this);
        oyo.setOnClickListener(this);
        ola.setOnClickListener(this);
        goibibo.setOnClickListener(this);
        zomato.setOnClickListener(this);


    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if ((requestCode == RESULT_OK && null!=data)){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    url.setText(result.get(0));
                }
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void openWebsite() {

        String url_address = url.getText().toString();
        String url_https = url_address.replaceAll("https://www.", "");
        String https = "https://";
        String www = "www.";
        String main_url = "https://www.google.com/search?source=hp&ei=H4oyXfXqEdzbz7sP6Py5iAg&q=";

        if(url_address.isEmpty()) {
            Toast.makeText(this, "Please Input web address", Toast.LENGTH_SHORT).show();
        }

        else if(url_address.startsWith(https)) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", url_address);
            startActivity(intent);
            url.setText("");
        }

        else if(url_address.startsWith(www)) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", https+url_address);
            startActivity(intent);
            url.setText("");
        }

        else if(url_address.endsWith(".com") || url_address.endsWith(".in") || url_address.endsWith(".edu")) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", https+www+url_https);
            startActivity(intent);
            url.setText("");
        }
        else {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", main_url+url_address);
            startActivity(intent);
            url.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        if(backpress + 2000 > System.currentTimeMillis()){
            backtoast.cancel();
            finish();
            return;
        }else {
            backtoast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backtoast.show();
        }

        backpress = System.currentTimeMillis();
    }

//    public void  checkConnection() {
//        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
//        if(null!=activeNetwork) {
//            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                Toast toast = Toast.makeText(this,"Connected", Toast.LENGTH_SHORT);
//            }
//        }
//        else {
//             startActivity(new Intent(MainActivity.this, NoInternet.class));
//        }
//    }

    @Override
    public void onClick(View v) {
        if(v == mic) {
            speak();
        }
        if(v == cancle) {
            url.setText("");
        }
        if(v == search) {
            openWebsite();
        }
        if (v == google) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.google.com");
            startActivity(intent);
        }
        if (v == youtube) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.youtube.com");
            startActivity(intent);
        }
        if (v == wikipedia) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.wikipedia.com");
            startActivity(intent);
        }
        if (v == cricbuzz) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.cricbuzz.com");
            startActivity(intent);
        }
        if (v == amazon) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.amazon.in");
            startActivity(intent);
        }
        if (v == flipkart) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.flipkart.com");
            startActivity(intent);
        }
        if (v == snapdeal) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.snapdeal.com");
            startActivity(intent);
        }
        if (v == clubfactory) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.clubfactory.com");
            startActivity(intent);
        }
        if (v == aliexpress) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.aliexpress.com");
            startActivity(intent);
        }
        if (v == shopclus) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.shopclues.com");
            startActivity(intent);
        }
        if (v == ebay) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://in.ebay.com");
            startActivity(intent);
        }
        if (v == mistore) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.mi.com");
            startActivity(intent);
        }
        if (v == facebook) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.facebook.com");
            startActivity(intent);
        }
        if (v == instagram) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.instagram.com");
            startActivity(intent);
        }
        if (v == twitter) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.twitter.com");
            startActivity(intent);
        }
        if (v == snapchat) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.snapchat.com");
            startActivity(intent);
        }
        if (v == paytm) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.paytm.com");
            startActivity(intent);
        }
        if (v == phonepe) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.phonepe.com");
            startActivity(intent);
        }
        if (v == gpay) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.gpay.com");
            startActivity(intent);
        }
        if (v == freecharge) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.freecharge.com");
            startActivity(intent);
        }
        if (v == dailyhunt) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://m.dailyhunt.in");
            startActivity(intent);
        }
        if (v == bbcnews) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.bbc.com");
            startActivity(intent);
        }
        if (v == gnews) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://news.google.com");
            startActivity(intent);
        }
        if (v == ndtv) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.ndtv.com");
            startActivity(intent);
        }
        if (v == oyo) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.oyorooms.com");
            startActivity(intent);
        }
        if (v == ola) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.olacabs.com");
            startActivity(intent);
        }
        if (v == goibibo) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.goibibo.com");
            startActivity(intent);
        }
        if (v == zomato) {
            Intent intent = new Intent(MainActivity.this, URLSearch.class);
            intent.putExtra("url_address", "https://www.zomato.com");
            startActivity(intent);
        }
    }
}
