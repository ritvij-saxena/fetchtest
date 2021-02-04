package com.example.fetchtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

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
                if (items!=null && items.size()>0) {
                    for(Items item: items){
                        if(item.getName()!=null && !item.getName().isEmpty()){
                            filteredItems.add(item);
                        }
                    }
                }
                myAdapter.setListData(filteredItems);
                System.out.println(items);
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                System.out.println("Message: "+ t.getMessage());
            }
        });
    }
}