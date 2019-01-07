package com.example.define16.dbconnecttest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class InsertDataActivity extends AppCompatActivity {
    private final String SERVER_URL = "";
    //layout에 있는 EditText의 변수 선언
    private EditText editTextID;
    private EditText editTextDate;
    private EditText editTextNum;
    private EditText editTextFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertdata);

        editTextID = (EditText) findViewById(R.id.id);
        editTextDate = (EditText) findViewById(R.id.date);
        editTextNum = (EditText) findViewById(R.id.num1);
        editTextFloat = (EditText) findViewById(R.id.float1);
    }


    //insert버튼 눌렀을때
    public void insert(View view){
        String TAGID = editTextID.getText().toString(); // 데이터를 텍스트필드로 부터 받아온다.
        String TAGDATE = editTextDate.getText().toString();
        String TAGNUM = editTextNum.getText().toString(); // 데이터를 텍스트필드로 부터 받아온다.
        String TAGFLOAT = editTextFloat.getText().toString();

        insertToDatabase(TAGID, TAGDATE,TAGNUM,TAGFLOAT); //데이터 베이스 관련 함수로 보낸다.

    }

    private void insertToDatabase(String id, String date, String num1, String float1){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InsertDataActivity.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try{
                    String id = (String)params[0];
                    String date = (String)params[1];
                    String num1 = (String)params[2];
                    String float1 = (String)params[3];

                    String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    data += "&" + URLEncoder.encode("num1", "UTF-8") + "=" + URLEncoder.encode(num1, "UTF-8");
                    data += "&" + URLEncoder.encode("float1", "UTF-8") + "=" + URLEncoder.encode(float1, "UTF-8");


                    URL url = new URL(SERVER_URL); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(id,date, num1, float1); // 데이터를 데이터 베이스에 넣겠다.
    }
}
