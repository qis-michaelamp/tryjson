package com.example.lenovo.tryjson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.progressBarId)
    ProgressBar progressBarId;
    @BindView(R.id.listView)
    ListView listView;

    /*@BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_quantity)
    TextView tvQuantity;
    @BindView(R.id.et_quantity)
    EditText etQuantity;
    @BindView(R.id.btnPost)
    Button btnPost;*/

    private String TAG = MainActivity.class.getSimpleName();

    private static String url = "https://api.myjson.com/bins/gh72t";

    ArrayList<HashMap<String, String>> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        patientList = new ArrayList<>();

        listView = (ListView) findViewById(R.id.listView);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarId.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            //make a request to url and get response
            String jsonstr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from URL : " + jsonstr);

            if (jsonstr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonstr);

                    //get json array node
                    JSONArray patient = jsonObject.getJSONArray("patient");

                    //loop through all contacts
                    for (int i = 0; i < patient.length(); i++) {
                        JSONObject c = patient.getJSONObject(i);

                        int id = c.getInt("id");
                        String name = c.getString("name");
                        String mrn = c.getString("mrn");
                        String gender = c.getString("gender");
                        String birthday = c.getString("birthday");

                        HashMap<String, String> patients = new HashMap<>();

                        patients.put("name", name);
                        patients.put("mrn", mrn);
                        patients.put("gender", gender);

                        patientList.add(patients);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json Parsing Error : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Json Parsing Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get JSON from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Couldn't get JSON from server. CHECK LOGCAT!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, patientList, R.layout.list_item, new String[]{"name", "mrn", "gender"}, new int[]{R.id.nameId, R.id.mrnId, R.id.genderId}
            );

            listView.setAdapter(adapter);

            progressBarId.setVisibility(View.GONE);
        }
    }
}
