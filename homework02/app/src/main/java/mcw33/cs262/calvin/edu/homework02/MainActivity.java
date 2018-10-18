package mcw33.cs262.calvin.edu.homework02;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private PlayerAdapter listAdapter;
    private ArrayList<PlayerItem> playerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit_text_query);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = findViewById(R.id.list_view);
        playerItems = new ArrayList<>();
        listAdapter = new PlayerAdapter(this, playerItems);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchPlayers(View view) {
        // Use Volley since it is a standard for making web requests
        String url = "https://calvincs262-monopoly.appspot.com/monopoly/v1";
        // Check if a number has been inputted
        if (mEditText.getText().toString().equals("")) {
            url += "/players";
        } else {
            try {
                // Try to parse the number that has been inputted
                url += "/player/" + Integer.parseInt(mEditText.getText().toString());
            } catch(NumberFormatException nfe) {
                return;
            }
        }
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        parseJsonFeed(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mTextView.setText("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            playerItems.clear();
            JSONArray playerArray;
            // If we left our search blank, we will get a list of players under "items"
            if (response.has("items")) {
                playerArray = response.getJSONArray("items");
            } else {
                playerArray = new JSONArray();
                playerArray.put(response);
            }

            for (int i = 0; i < playerArray.length(); i++) {
                JSONObject playerObj = (JSONObject) playerArray.get(i);
                String playerName = "no name";  
                if (playerObj.has("name")) {
                    playerName = playerObj.getString("name");
                }

                PlayerItem item = new PlayerItem(playerObj.getInt("id"), playerName, playerObj.getString("emailAddress"));
                Log.d("PLAYER", item.toString());
                playerItems.add(item);
            }

            // notify data changes to list adapter
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
