package eu.ase.chirita_andrei.proiect.zocdocclone.network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.ase.chirita_andrei.proiect.zocdocclone.activities.MainActivity;

public class FetchData extends AsyncTask<Void, Void, Void> {

    String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line!=null){
                line = bufferedReader.readLine();
                data = data+line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
