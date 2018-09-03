package com.example.android.newnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.newnews.favourite_data.NewsDbHelper;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();

        //drawer menu
    private DrawerLayout mDrawerLayout;

        //action bar item that open/close the drawer
    private ActionBarDrawerToggle mToggle;

        //the adapter for the news
    private NewsAdapter mNewsAdapter;

        //URL to make the http request
    private static final String REQUEST_URL =
            "https://newsapi.org/v2/everything?apiKey=a6e8f938771548de8127b3cd1ab6eaf9&language=it&q=calcio";

        //JSON response
    private static String jsonString;

    private static String jsonResponese =
            "{\"status\":\"ok\",\"totalResults\":49008,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Lastampa.it\"},\"author\":\"La Stampa\",\"title\":\"Calcio Chieri - Calcio Derthona /La diretta\",\"description\":\"Il Calcio Chieri è quinto con 50 punti. Un altro avversario quindi ostico per il Calcio Derthona, penultimo in classifica con 17. Ormai parlare di salvezza non ha più senso, non resta che onorare questo finale di campionato....\",\"url\":\"http://www.lastampa.it/2018/03/11/edizioni/alessandria/calcio-chieri-calcio-derthona-la-diretta-nb8lTcWk9NqeG7L0a3lCVK/pagina.html\",\"urlToImage\":\"http://www.lastampa.it/rw/Pub/p4/2018/02/28/Alessandria/Foto/RitagliWeb/R2P18L505884-kF5C-U11012617293394q7E-1024x576%40LaStampa.it.jpg\",\"publishedAt\":\"2018-03-11T05:00:00Z\"},{\"source\":{\"id\":\"ansa\",\"name\":\"ANSA.it\"},\"author\":\"\",\"title\":\"Allegri \\\"il calcio leva, il calcio dà\\\"\",\"description\":\"\\\"Orgoglioso del carattere\\\". Higuain, \\\"grandissimi\\\"\",\"url\":\"http://www.ansa.it/sito/notizie/sport/calcio/2018/04/29/allegri-il-calcio-leva-il-calcio-da_d164ab10-e22b-410b-9ed2-5253716da6f3.html\",\"urlToImage\":\"http://www.ansa.it/webimages/img_700/2018/4/28/3d8af7e76a3b7d0ae3e7d6f7d93a8517.jpg\",\"publishedAt\":\"2018-04-29T08:23:41Z\"},{\"source\":{\"id\":\"google-news\",\"name\":\"Google News\"},\"author\":null,\"title\":\"Scuola Calcio\",\"description\":\"\",\"url\":\"http://feedproxy.google.com/~r/avaxhome/bojL/~3/h_B6I3ihDWE/Scuola-Calcio-74046229.html\",\"urlToImage\":null,\"publishedAt\":\"2018-01-27T13:36:42Z\"},{\"source\":{\"id\":null,\"name\":\"Finofilipino.org\"},\"author\":null,\"title\":\"Calcio intensifies.\",\"description\":\"Calcio intensifies.\",\"url\":\"https://finofilipino.org/post/170830960272/cuernos\",\"urlToImage\":\"https://78.media.tumblr.com/aae151f66d0c3c8535e9d99d95c3f41a/tumblr_p42j7wDWoZ1s9y3qio1_1280.jpg\",\"publishedAt\":\"2018-02-13T11:28:28Z\"},{\"source\":{\"id\":null,\"name\":\"Dribbble.com\"},\"author\":\"Ensar Sewer\",\"title\":\"Udinese Calcio\",\"description\":\"Udinese Calcio Rebrand\",\"url\":\"https://dribbble.com/shots/4072323-Udinese-Calcio\",\"urlToImage\":\"https://cdn.dribbble.com/users/541297/screenshots/4072323/3.png\",\"publishedAt\":\"2018-01-07T13:36:02Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, gli anticipi di serie B\",\"description\":\"18.00 Spezia-Ascoli (Serie B) - SKY SUPER CALCIO e SKY CALCIO 1\\n20.30 Bari-Brescia (Serie B) - SKY SPORT 1,  SKY SPORT MIX, SKY SUPER CALCIO, SKY CALCIO.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-gli-anticipi-di-serie-b-1091812\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/4fb549934589afff804a6d3ab06cbfea-71859-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2018-03-24T04:30:01Z\"},{\"source\":{\"id\":\"ansa\",\"name\":\"ANSA.it\"},\"author\":null,\"title\":\"Parlamentari in campo per beneficenza\",\"description\":\"Triangolare di calcio per beneficenza ad Alba tra la nazionale di calcio dei parlamentari, la\\r\\nsquadra della Torino calcio disability, una rappresentativa dell'Albese Calcio. © ANSA\",\"url\":\"http://www.ansa.it/piemonte/notizie/2017/10/06/parlamentari-in-campo-per-beneficenza_04a69e56-ed98-42f6-a9a0-9c51dba70955.html\",\"urlToImage\":\"http://www.ansa.it/webimages/img_700/2017/10/6/4a2babd72dc9a8eec30acdded9d3ea74.jpg\",\"publishedAt\":\"2017-10-06T13:55:37Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in tv, gli anticipi di Serie B e Danimarca-Irlanda\",\"description\":\"15.00 Pro Vercelli-Empoli (Serie B) - SKY SUPER CALCIO e SKY CALCIO 1\\n18.00 Avellino-Entella (Serie B) - SKY SUPER CALCIO; SKY SPORT MIX e SKY CALCIO.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-gli-anticipi-di-serie-b-e-danimarca-irlanda-1040805\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/0d742792cac6eb6af07804e7dc9a12c4-62503-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2017-11-11T04:30:25Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, la serie A ed i posticipi europei\",\"description\":\"12.00 Leganes-Villarreal (Liga) - FOX SPORTS\\n12.30 Benevento-Milan (Serie A) - SKY SUPER CALCIO, SKY CALCIO 1, SKY CALCIO 6 e PREMIUM SPORT\\n14.30 Bournemouth-Southampton.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-la-serie-a-ed-i-posticipi-europei-1049024\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/cf1115d39d3595ead1db969d7d27ae08-51597-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2017-12-03T04:30:00Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, il 18° turno di serie A\",\"description\":\"11.00 Lazio-Roma (Campionato Primavera TIM) - SPORTITALIA\\n12.30 Lazio-Crotone (Serie A) - PREMIUM SPORT, SKY SUPER CALCIO, SKY CALCIO 1 e SKY CALCIO 6\\n13.00.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-il-18a-turno-di-serie-a-1057224\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/2e797dc7a3a8b92dc15372b2af3c1522-49959-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2017-12-23T04:30:22Z\"},{\"source\":{\"id\":null,\"name\":\"Altervista.org\"},\"author\":\"Calcio Peligno\",\"title\":\"Promozione Girone B: 33^ Giornata - Seo Marketing Article\",\"description\":\"Risultati.Piazzano-Castiglione Val Fino= 2-0Sulmona Calcio-Il Delfino Flacco Porto= 0-1 Bucchianico Calcio-Palombaro Calcio 1975= 11-2 Fossacesia-Passo Cordone= 0-0Fater Angelini Abruzzo-Raiano= 0-0 Ortona Calcio-Sportland F. C. Celano= 5-2Real Trecimi...\",\"url\":\"http://seomarketingarticle.altervista.org/promozione-girone-b-33-giornata-2/\",\"urlToImage\":\"https://2.bp.blogspot.com/-R9oOndf15Po/V97XOdQLapI/AAAAAAAAqD0/_gXKUqQxY00mKF8ytuWk85LbjxD7ABjegCPcBGAYYCw/s1600/promozione.jpg\",\"publishedAt\":\"2018-04-22T20:12:38Z\"},{\"source\":{\"id\":null,\"name\":\"Altervista.org\"},\"author\":\"Calcio Peligno\",\"title\":\"1^ Categoria Girone C: 28^ Giornata - Seo Marketing Article\",\"description\":\"Risultati.S. Pelino-Cugnoli= 0-2Scafa Cast 2017-Federlibertas Bugnara= 2-2Giuliano Teatino 1947-Manoppello Arabona= 3-2Alanno-Pacentro Calcio= 2-1S. Anna-Popoli Calcio= 4-2Pescina Calcio 1950-Pretoro Calcio= 2-5Cepagatti-Roseto 1920= 4-1Polisportiva Mo...\",\"url\":\"http://seomarketingarticle.altervista.org/1-categoria-girone-c-28-giornata/\",\"urlToImage\":\"https://2.bp.blogspot.com/-TaHnBJPwjmM/V97aFCTjbzI/AAAAAAAAqD0/Xre54PYkA20zhGy5sbDLISThbtmscTPogCPcBGAYYCw/s1600/prima%2Bcategoria.jpg\",\"publishedAt\":\"2018-04-22T18:27:45Z\"},{\"source\":{\"id\":null,\"name\":\"Lastampa.it\"},\"author\":\"La Stampa\",\"title\":\"Calcio serie D, Seregno - Calcio Derthona /La diretta\",\"description\":\"A Seregno per provare miracolosamente a tornare in corsa per la salvezza. Compito quasi proibitivo per il Calcio Derthona in Brianza contro un team che, trascinato dai gol di Barzotti e rivitalizzato dalla cura del neo allenatore Bonazzi, ha vinto le ultime d…\",\"url\":\"http://www.lastampa.it/2018/02/21/edizioni/alessandria/sport/calcio-serie-d-seregno-calcio-derthona-la-diretta-bFbCovHu3dJWrQmDzGpDhJ/pagina.html\",\"urlToImage\":\"http://www.lastampa.it/rw/Pub/p4/2018/02/21/Alessandria/Foto/RitagliWeb/KVNVNYD82876-kJzD-U11012501800167PdF-1024x576%40LaStampa.it.JPG\",\"publishedAt\":\"2018-02-21T10:57:13Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, il 32Â° turno di serie B\",\"description\":\"12.30 Parma-Foggia (Serie B) - SKY SUPER CALCIO e SKY CALCIO 1 15.00 Diretta Goal Serie B - SKY SPORT 1 e SKY SUPER CALCIO 15.00 Palermo-Carpi (Serie B) - SKY CALCIO 1 15.00 Ternana-Frosinone (Serie B) - SKY CALCIO 2 15.00 Pescara-Empoli (Serie B) -...\",\"url\":\"https://www.tuttomercatoweb.com/serie-b/oggi-in-tv-il-32a-turno-di-serie-b-1091914\",\"urlToImage\":null,\"publishedAt\":\"2018-03-25T05:30:00Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, il 33° turno di serie B\",\"description\":\"20.30 Diretta Goal Serie B - SKY SPORT 1 e SKY SUPER CALCIO\\n20.30 Entella-Palermo (Serie B) - SKY CALCIO 1\\n20.30 Frosinone-Venezia (Serie B) - SKY CALCIO.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-il-33a-turno-di-serie-b-1093509\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/4fb549934589afff804a6d3ab06cbfea-71859-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2018-03-29T03:30:00Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"UFFICIALE: Sassuolo, preso Cisco del Padova. Ma resterà in biancoscudato\",\"description\":\"Il Calcio Padova informa che questa mattina è stato depositato l’accordo per il trasferimento a titolo definitivo dell’attaccante Andrea Cisco dal Calcio Padova all’Unione Sportiva Sassuolo Calcio.\",\"url\":\"https://www.tuttomercatoweb.com/serie-a/ufficiale-sassuolo-preso-cisco-del-padova-ma-restera-in-biancoscudato-1071492\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/08a3141015e16c8d09fe833e0f8c29bd-04433-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2018-01-30T11:18:21Z\"},{\"source\":{\"id\":null,\"name\":\"Tuttomercatoweb.com\"},\"author\":null,\"title\":\"Oggi in TV, i recuperi di serie B\",\"description\":\"12.30 Foggia-Avellino (Serie B) SKY CALCIO 1\\n15.00 Avellino-Bari (Serie B) SKY CALCIO 1\\n17.30 Carpi-Venezia (Serie B) SKY CALCIO 1\\n20.30 Parma-Palermo.\",\"url\":\"https://www.tuttomercatoweb.com/altre-notizie/oggi-in-tv-i-recuperi-di-serie-b-1095029\",\"urlToImage\":\"https://tmw-storage.tccstatic.com/storage/tuttomercatoweb.com/img_notizie/thumb3/4fb549934589afff804a6d3ab06cbfea-71859-95723157c8ebcac4750156d2b21ac04e.jpeg\",\"publishedAt\":\"2018-04-02T03:30:28Z\"}]}";

/**-----------------------------------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

            //creating the drawer item
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            //creating the button to open/close the drawer
        mToggle = new ActionBarDrawerToggle(HomeActivity.this, mDrawerLayout, R.string.open, R.string.close);
            //attach the drawer and the button
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * populate the home activity trought an HTTP request
         *
         * ListView newsListView = (ListView) findViewById(R.id.list);
         * mNewsAdapter = new NewsAdapter(this, new ArrayList<NewsItem>() );
         * newsListView.setAdapter(mNewsAdapter);
         * NewsAsyncTask task = new NewsAsyncTask();
         * task.execute(REQUEST_URL);
         */

            //populate the home activity through the JSON response object
        Log.e("homeActivity", "jsonString before = " +jsonString);
        getHttp();
        Log.e("homeActivity", "jsonString after = " + jsonString);
        while (jsonString == null) {
            //waiting for the download to finish
        }
        ListView newsListView = findViewById(R.id.list);
        mNewsAdapter = new NewsAdapter(this, QueryUtils.extractFeatureFromJson(jsonString) );
        newsListView.setAdapter(mNewsAdapter);

    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case  R.id.go_to_favourite: {
                goToFavouriteActivity();
                break;
            }
            case  R.id.go_to_options: {
                goToOptionsActivity();
                break;
            }
            case  R.id.go_to_refresh: {
                //TODO: add the refresh method
                break;
            }
        }
        return true;
    }

/**-----------------------------------------------------------------------------------------------*/

    //TODO: need to update to populate the view like the onCreate
    @Override
    protected void onRestart() {
        super.onRestart();
        //populate the home activity through the JSON response object
        ListView newsListView = findViewById(R.id.list);
        mNewsAdapter = new NewsAdapter(this, QueryUtils.extractFeatureFromJson(jsonString) );
        newsListView.setAdapter(mNewsAdapter);
    }

/**-----------------------------------------------------------------------------------------------*/

    //not used for the moment
    private class NewsAsyncTask extends AsyncTask<String, Void, List<NewsItem> > {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected List<NewsItem> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<NewsItem> result = QueryUtils.fetchNewsData(REQUEST_URL);
            return result;
        }

        @Override
        protected void onPostExecute(List<NewsItem> data) {
            // Clear the adapter of previous earthquake data
            mNewsAdapter.clear();

            // If there is a valid list of News, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mNewsAdapter.addAll(data);
            }
        }
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    private void goToOptionsActivity() {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    /**-----------------------------------------*/

    //DONE
    private void goToFavouriteActivity() {
        Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
        startActivity(intent);
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    private String createUrl() {
        String url = "https://newsapi.org/v2/everything?";
        url = url.concat("apiKey=");
        url = url.concat("a6e8f938771548de8127b3cd1ab6eaf9");
            url = url.concat("&");
        url = url.concat("language=");
        url = url.concat("it");
            url = url.concat("&");
        url = url.concat("q=");
        url = url.concat("calcio");

        Log.e("CreateUrl", ""+url);
        return url;
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    //start an OkHttpAsync AsyncTask
    public void getHttp() {
        //instantiate async task which call service using OkHttp in the background
        // and execute it passing required parameter to it
        //get http request using okhttp

        //new HomeActivity.OkHttpAync().execute();
        OkHttpAsync task = new OkHttpAsync();
        task.execute();
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    //doInBackground: call getHttpResponse (sync WS call)
    private class OkHttpAsync extends AsyncTask<Object, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("onPreExecute", "");
        }

        @Override
        protected Object doInBackground(Object... params) {
            Log.e("doInBackground", "processing http request in async task");

            getHttpResponse();
            //getAsyncCall();

            Log.e("doInBackground", "jsonString = " +jsonString);

            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);

            Log.e("onPostExecute", "jsonString = " +jsonString);
        }
    }

/**-----------------------------------------------------------------------------------------------*/

    //DONE
    //Sync WS call
    public Object getHttpResponse() {
    /**-------V2----------------------------------------------------------------------------------*/

        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            /**
             * create the HTTP client and attach:
             *      the custom Trust Manager
             *      and the custom SSL Socket Factory
             */
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sc.getSocketFactory())
                    .build();
            /**
             * create and start the connection to the WS and try to get a response
             * that will be converted in a String
             */
            String url = createUrl();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response;
            response = httpClient.newCall(request).execute();
            jsonString = response.body().string();
            return response.body().string();
        } catch (Exception e) {
            Log.e("getHttpResponse", "error in getting response with error = " +e);
        }
        return null;




    /**-------V1----------------------------------------------------------------------------------*/

//        /**
//         * create custom TrustManager and SSLSocketFactory
//         */
//        X509TrustManager mTrustManager = null;
//        SSLSocketFactory mSocketFactory = null;
//        try {
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
//                    TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init((KeyStore) null);
//            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
//                throw new IllegalStateException("Unexpected default trust managers:"
//                        + Arrays.toString(trustManagers));
//            }
//            mTrustManager = (X509TrustManager) trustManagers[0];
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[] { mTrustManager }, null);
//            mSocketFactory = sslContext.getSocketFactory();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//        /**
//         * create the HTTP client and attach:
//         *      the custom Trust Manager
//         *      and the custom SSL Socket Factory
//         */
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .sslSocketFactory(mSocketFactory, mTrustManager)
//                .build();
//        /**
//         * create and start the connection to the WS and try to get a response
//         * that will be converted in a String
//         */
//        String url = createUrl();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Response response;
//        try {
//            response = httpClient.newCall(request).execute();
//            jsonString = response.body().string();
//            return response.body().string();
//        } catch (IOException e) {
//            Log.e("getHttpResponse", "error in getting response with error = " +e);
//        }
//        return null;



    }

    /**-----------------------------------------*/

    //NOT USED
    //Async WS call
    public void getAsyncCall(){
        OkHttpClient httpClient = new OkHttpClient();
        String url = createUrl();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.e("AsyncCall", "in the call");

        //okhttp asynchronous call
        httpClient.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                Log.e("onFailure", "error in getting response using async okhttp call");
                Log.e("onFailure", "error: " +e);
                call.cancel();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {

                Log.e("onResponse", "just entered");

                ResponseBody responseBody = response.body();
                /*
                if (!response.isSuccessful()) {
                    throw new IOException("Error response " + response);
                }*/

                Log.e("onResponse",responseBody.string());

                jsonString = responseBody.string();
            }
        });

        Log.e("AsyncCall", " exiting the call");

    }

/**-----------------------------------------------------------------------------------------------*/

    //NOT USED
    private static SSLContext setSslSocketFactory() {
        SSLContext context = null;

        try {
            /**
             * Load CAs from an InputStream
             * (could be from a resource or ByteArrayInputStream or ...)
             */
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // From https://www.washington.edu/itconnect/security/ca/load-der.crt
            InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
            Certificate ca;
            try {
                ca = cf.generateCertificate(caInput);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                caInput.close();
            }
            /**
             * Create a KeyStore containing our trusted CAs
             */
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            /**
             * Create a TrustManager that trusts the CAs in our KeyStore
             */
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            /**
             * Create an SSLContext that uses our TrustManager
             */
            context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            /**
             * catch all the errors
             */
        } catch (CertificateException e) {
            Log.e("setSslSocketFactory", "CertificateException = " +e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("setSslSocketFactory", "IOException = " +e);
            e.printStackTrace();
        } catch (KeyStoreException e) {
            Log.e("setSslSocketFactory", "KeyStoreException = " +e);
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            Log.e("setSslSocketFactory", "NoSuchAlgorithmException = " +e);
            e.printStackTrace();
        } catch (KeyManagementException e) {
            Log.e("setSslSocketFactory", "KeyManagementException = " +e);
            e.printStackTrace();
        }
        /**---------------------------------------------------------------------------------------*/
        return context;
    }

/**-----------------------------------------------------------------------------------------------*/
}
