package ca.skipthedishes.skipthedishesfooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//import com.google.code.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

import bean.NetworkUtils;
import bean.OrderItem;
import bean.Store;

public class MainActivity extends Activity {

    private Intent intent;
    private EditText editTextSearching;
    private Button settingButton;
    private Button orderButton;
    private ListView listViewStore;
    private ArrayList restaurantList = new ArrayList();
    private ArrayList temp;
    private static final String SETTINGS_FILE =  "SettingsFile";
    private ArrayAdapter<String> adapter;

    private MyAdapterStore myAdapter;
    public static ArrayList<OrderItem> orderItemList;
    public static String restaurantId = "1";
    public static String url;
    public String storeUrl = "";
    public String storeName = "";

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "192.168.0.105";
        init();

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, OrderActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("activity", "main");
                intent.putExtra("storeUrl", storeUrl);
                intent.putExtra("storeName", storeName);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });


//        editTextSearching.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String result;
//                try {
//                    result = NetworkUtils.getJSONFromAPI("GET", "http://"+url+":9090/store/name/"+editTextSearching.getText());
//                    Gson gson = new Gson();
//                    Type storeListType = new TypeToken<ArrayList<Store>>(){}.getType();
//                    System.out.println(result);
//                    ArrayList<Store> storeList = gson.fromJson(result, storeListType);
//                    myAdapter.setStoreList(storeList);
//                    myAdapter.notifyDataSetChanged();
//                   } catch (Throwable t) {
//                    t.printStackTrace();
//                }
//            }
//        });

        listViewStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Store store = (Store) listViewStore.getAdapter().getItem(position);
                restaurantId = (position+1) + "";
                intent = new Intent(MainActivity.this, StoreActivity.class);
                intent.putExtra("orderItemList", orderItemList);
                intent.putExtra("restaurantId", restaurantId);
                intent.putExtra("storeUrl", store.getLogo());
                intent.putExtra("storeName", store.getName());
                intent.putExtra("url", url);
                try{
                    startActivity(intent);
                }
                catch(Throwable t){
                    System.out.println(t.getMessage());
                }
            }
        });

    }

    public void init() {
        settingButton = findViewById(R.id.buttonSettingId);
        orderButton =  findViewById(R.id.buttonOrderMainId);
        listViewStore =  findViewById(R.id.listViewStoreId);
        //EditText editTextSearchingRestaurant =  findViewById(R.id.editTextSearchingId);

        restaurantId = "0";
        Bundle extra = getIntent().getExtras();
        String s = "";
        if (extra != null) {
            s = extra.getString("restaurantId");
        }
        if (s.length() > 0) {
            restaurantId    = extra.getString("restaurantId");
            orderItemList   = (ArrayList<OrderItem>) getIntent().getSerializableExtra("orderItemList");
            url             = extra.getString("url");
            storeUrl        = extra.getString("storeUrl");
            storeName       = extra.getString("storeName");
            storeUrl        = extra.getString("url").toString();
            if (orderItemList == null) {
                orderItemList = new ArrayList<>();
            }
        }

        //DESCOMENTADO
        adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                restaurantList);

        //Chamando apenas para verificar a carga do ArrayList
        listViewStore.setAdapter(adapter);

        myAdapter = new MyAdapterStore(new ArrayList<Store>(), MainActivity.this, handler);
        listViewStore.setAdapter(myAdapter);


        if (orderItemList == null || orderItemList.size() == 0){
            orderButton.setEnabled(false);
        }else{
            orderButton.setEnabled(true);
        }


       new Http().execute();

    }

    private class Http extends AsyncTask<Void, Void, Boolean> {

        private String result;


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (Boolean.TRUE.equals(aBoolean)) {
                Gson gson = new Gson();
                Type storeListType = new TypeToken<ArrayList<Store>>(){}.getType();
                System.out.println(result);
                ArrayList<Store> storeList = gson.fromJson(result, storeListType);
                myAdapter.setStoreList(storeList);
                myAdapter.notifyDataSetChanged();
            } else {
                myAdapter.setDataList(new ArrayList<Store>());
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                result = NetworkUtils.getJSONFromAPI("GET", "http://"+url+":9090/store");
                return Boolean.TRUE;
            } catch (Throwable t) {
                t.printStackTrace();
                return Boolean.FALSE;
            }
        }
    }


}


