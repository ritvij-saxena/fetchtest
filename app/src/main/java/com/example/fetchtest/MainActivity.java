package com.example.fetchtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fetch-hiring.s3.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Service service = retrofit.create(Service.class);
        Call<List<Items>> call = service.getItems();
        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if(!response.isSuccessful()){
                    textView.setText("Code: "+response.code());
                    return;
                }
                List<Items> items = response.body();
                for(Items item: items){
                    String content = "";
                    content += "id: " + item.getId() + "\n";
                    content += "listId: " + item.getId() + "\n";
                    content += "name: " + item.getId() + "\n";
                    textView.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                textView.setText("Message: "+ t.getMessage());
            }
        });
    }
}