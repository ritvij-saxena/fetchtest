package com.example.fetchtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MyAdapter myAdapter;
    private ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expandableListView = findViewById(R.id.expandListView);
        myAdapter = new MyAdapter(getApplicationContext());
        expandableListView.setAdapter(myAdapter);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fetch-hiring.s3.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Items>> call = service.getItems();
        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if(!response.isSuccessful()){
                    System.out.println("Code: "+response.code());
                    return;
                }
                List<Items> items = response.body();
//                items.removeIf(x -> x.getName().isEmpty());
                List<Items> filteredItems = new ArrayList<>();
                //filtering out null or "" values
                if (items!=null && items.size()>0) {
                    for(Items item: items){
                        if(item.getName()!=null && !item.getName().isEmpty()){
                            filteredItems.add(item);
                        }
                    }
                }
                // grouping by listIds
                /**
                 *  format of grouping:
                 *  {
                 *      listId : [{Items},{Items}]
                 *  }
                 *
                 *  eg:
                 *  {
                 *      3: [ {id:1,listId:3,name:"name"}]
                 *  }
                 *
                 * */
                // TreeMap used for storing sorted keys;
                Map<Integer,List<Items>> groupedData = new TreeMap<>();
                for(Items item: filteredItems){
                    if(groupedData.containsKey(item.getListId())){
                        groupedData.get(item.getListId()).add(item);
                    }else{
                        List<Items> newList = new ArrayList<>();
                        newList.add(item);
                        groupedData.put(item.getListId(),newList);
                    }
                }
                // sorting grouped items with respect to name;
                for(Map.Entry<Integer,List<Items>> item : groupedData.entrySet()){
                    Collections.sort(item.getValue(), new Comparator<Items>() {
                        @Override
                        public int compare(Items t1, Items t2) {
//                            return  t1.getId() - t2.getId();
                            return Integer.parseInt(t1.getName().split(" ")[1].trim()) - Integer.parseInt(t2.getName().split(" ")[1].trim());
                        }
                    });
                }
                Log.d(TAG, "onResponse: "+ groupedData.keySet());
                myAdapter.setData(groupedData,groupedData.keySet().toArray());
//                System.out.println(items);
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println("Message: "+ t.getMessage());
            }
        });
    }
}