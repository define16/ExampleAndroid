package com.example.define16.dbconnecttest;



import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 2017-05-13.
 *  데이터베이스에서 데이터를 가져와 리스트에 넣는 코드
 */

public class GetDataActivity extends Activity {
    private static final String SERVER_URL = "";
    private static final String TAG_RESULTS="webnautes";
    private static final String TAG_ID = "id";
    private static final String TAG_DATE = "date";
    private static final String TAG_NUM = "num1";
    private static final String TAG_FLOAT ="float1";

    String myJSON = null;
    JSONArray testData = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getdata);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        getData(SERVER_URL); //http://접속할 주소/php파일
    }



    protected void showList(){
        try {
            Log.d("Test11", myJSON);
            JSONObject jsonObj = new JSONObject(myJSON);
            testData = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<testData.length();i++){
                JSONObject c = testData.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String password = c.getString(TAG_DATE);
                String name = c.getString(TAG_NUM);
                String year = c.getString(TAG_FLOAT);


                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_DATE,password);
                persons.put(TAG_NUM,name);
                persons.put(TAG_FLOAT,year);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    GetDataActivity.this, personList, R.layout.itme,
                    new String[]{TAG_ID,TAG_DATE,TAG_NUM,TAG_FLOAT},
                    new int[]{R.id.id, R.id.date, R.id.num1, R.id.float1}
            );

            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];
                Log.e("Test", uri);
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }



            }

            @Override
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
