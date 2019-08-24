package com.example.a6tanvir;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView linearLayoutListView;
    String stringURL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=7.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        String numEq = intent.getStringExtra("num_eq");
        String startDate = intent.getStringExtra("start_date");
        String orderBy = intent.getStringExtra("order_by");

        StringBuffer makeURL = new StringBuffer();
        makeURL.append(stringURL);
        makeURL.append("&limit=" + numEq);
        makeURL.append("&starttime=" + startDate);
        makeURL.append("&orderby=" + orderBy);
        String makeURLString = makeURL.toString();

        QuakeAsyncTask task = new QuakeAsyncTask();
        task.execute(makeURLString);
    }

    class QuakeAsyncTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... stringurl) {
            return Utils.fetchEarthquakeData(stringurl[0]);
        }

        public void onPostExecute(List<String> postExecuteResult) {
            final List<String> item_ = postExecuteResult;

            CustomListAdapter arrayAdapter = new CustomListAdapter(ListActivity.this,
                    postExecuteResult);
            linearLayoutListView = findViewById(R.id.quake_list_id);
            linearLayoutListView.setAdapter(arrayAdapter);

            linearLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String eqInfo[] = item_.get(position).split("@@");

                    Intent intent = new Intent(ListActivity.this, WebViewActivity.class);
                    intent.putExtra("lon", eqInfo[3]);
                    intent.putExtra("lat", eqInfo[4]);
                    startActivity(intent);
                }
            });
        }
    }
}

class CustomListAdapter extends ArrayAdapter<String> {
    Activity context_;
    List<String> item_;

    public CustomListAdapter(Activity context, List<String> item) {
        super(context, R.layout.quake_list_item, item);
        context_ = context;
        item_ = item;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context_.getLayoutInflater();
        View vRow = inflater.inflate(R.layout.quake_list_item, null, true);
        String eqInfo[] = item_.get(position).split("@@");
        TextView tvDetails = vRow.findViewById(R.id.quake_item_details_id);
        TextView tvDate = vRow.findViewById(R.id.quake_item_date_id);
        tvDetails.setText(eqInfo[0]);
        tvDate.setText(eqInfo[1]);
        LinearLayout quakeLL = vRow.findViewById(R.id.quake_item_layout_id);
        if (Double.parseDouble(eqInfo[2]) < 7.5) {
            quakeLL.setBackgroundColor(Color.parseColor("#ffaa66cc"));
        } else {
            quakeLL.setBackgroundColor(Color.parseColor("#ffff4444"));
        }
        return vRow;
    }
}
