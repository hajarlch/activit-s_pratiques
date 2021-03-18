package com.example.meteo;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.meteo.model.MeteoItem;
import com.example.meteo.model.MeteoListModel;

import org.json.JSONArray;
import org.json.JSONException; import org.json.JSONObject;
import java.text.SimpleDateFormat; import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.utils.Easing;

public class MainActivity extends AppCompatActivity {
    private EditText editTextVille; private ListView listViewMeteo;

    List<MeteoItem> data=new ArrayList<>();
    
    private MeteoListModel model; private ImageButton buttonOK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextVille=findViewById(R.id.editTextVille);
        listViewMeteo=findViewById(R.id.listViewMeteo);
        buttonOK=findViewById(R.id.buttonOK);
        model=new MeteoListModel(getApplicationContext(),R.layout.list_item_layout,data);
        listViewMeteo.setAdapter(model);

        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("MyLog","......");
                data.clear(); model.notifyDataSetChanged();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String ville=editTextVille.getText().toString();
                Log.i("MyLog",ville);
                String url="https://samples.openweathermap.org/data/2.5/forecast?q="+ville+"&appid=a4578e39643716894ec78b28a71c7110";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("MyLog", "----------------------------");
                            Log.i("MyLog", response);
                            List<MeteoItem> meteoItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            JSONObject main = null;
                            MeteoItem meteoItem = null;
                            int tempMax = 0;
                            int tempMin = 0;
                            String dateString = null;
                            JSONArray weather = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                meteoItem = new MeteoItem();
                                JSONObject d = jsonArray.getJSONObject(i);
                                Date date = new Date(d.getLong("dt") * 1000);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm");
                                dateString = sdf.format(date);
                                main = d.getJSONObject("main");
                                weather = d.getJSONArray("weather");
                                tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                tempMax = (int) (main.getDouble("temp_max") - 273.15);
                            }
                            int pression = main.getInt("pressure");
                            int humidity = main.getInt("humidity");

                            meteoItem.tempMax = tempMax;
                            meteoItem.tempMin = tempMin;
                            meteoItem.pression = pression;
                            meteoItem.humidite = humidity;
                            meteoItem.date = dateString;
                            meteoItem.image = weather.getJSONObject(0).getString("main");
                            meteoItems.add(meteoItem);
                            data.add(meteoItem);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        model.notifyDataSetChanged();
                    }

                }, new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError Error) {
                        Log.i("MyLog","Connection problem!");
                    }
                });

// Add the request to the RequestQueue.
queue.add(stringRequest);
        }
    });
}
}