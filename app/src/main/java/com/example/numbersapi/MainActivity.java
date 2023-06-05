package com.example.numbersapi;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.numbersapi.db.AppDatabase;
import com.example.numbersapi.db.Numbers;
import com.example.numbersapi.db.NumbersDao;
import com.example.numbersapi.pojo.NumbersPojo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity{
    private EditText enterNumberET;
    private Button enteredNumberBTN, randomNumberBTN;
    private TextView showFactTV;
    private AppDatabase db;
    private NumbersDao numbersDao;
    private ProgressDialog progressDialog;
    private RecyclerView customRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        db = App.getInstance().getDatabase();
        numbersDao = db.numbersDao();

        enteredNumberBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = enterNumberET.getText().toString();
                if (!number.matches("[0-9]*")) {
                    makeText(MainActivity.this, "Format not supported, please, enter an " +
                            "integer", LENGTH_LONG).show();
                } else if (number != null && !number.equals("")) {
                    getFactFromNumber();
                } else if (number.equals("")) {
                    makeText(MainActivity.this, "Field is empty, enter number, please",
                            LENGTH_LONG).show();
                } else {
                    makeText(MainActivity.this, "Error", LENGTH_LONG).show();
                }
            }
        });
        randomNumberBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFactFromNumberRandom();
            }
        });
        showHistory();
    }

    public void getFactFromNumber() {
        setProgressDialog();
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance()
                .create(GetDataService.class);
        Call<NumbersPojo> call = service.getFactEnteredNumber(String.valueOf(enterNumberET.getText()));
        call.enqueue(new Callback<NumbersPojo>() {
            @Override
            public void onResponse(@NonNull Call<NumbersPojo> call, @NonNull Response<NumbersPojo> response) {
                if (response.body() != null) {
                    showFactTV.setText(response.body().getText());
                    progressDialog.dismiss();
                    addPreviewInHistory(response.body().getNumber(), response.body()
                            .getText());
                }
            }
            @Override
            public void onFailure(@NonNull Call<NumbersPojo> call, @NonNull Throwable t) {
                showFactTV.setText(t.getMessage());
            }
        });
    }
    public void getFactFromNumberRandom() {
        setProgressDialog();
        /*Create handle for the RetrofitInstance interface*/
        GetDataService service1 = RetrofitClientInstance.getRetrofitInstance()
                .create(GetDataService.class);
        Call<NumbersPojo> call = service1.getFactRandomNumber();
        call.enqueue(new Callback<NumbersPojo>() {
            @Override
            public void onResponse(Call<NumbersPojo> call, Response<NumbersPojo> response) {
                if (response.body() != null) {
                    showFactTV.setText(response.body().getText());
                    progressDialog.dismiss();
                    addPreviewInHistory(response.body().getNumber(), response.body()
                            .getText());
                }
            }
            @Override
            public void onFailure(Call<NumbersPojo> call, Throwable t) {
                showFactTV.setText(t.getMessage());
            }
        });
    }
    private void addPreviewInHistory (int number, String fact) {
        Numbers numbers = new Numbers();
        numbers.facts = fact;
        numbers.number = number;
        numbers.time = System.currentTimeMillis();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                numbersDao.insert(numbers);
                showHistory();
            }
        });
    }
    private void showHistory() {
        Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                   List<Numbers> listFromDb = numbersDao.getAllFactsSortByTime();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                showDataList(listFromDb);
                        }
                    });
                }
            });
    }
    private void initViews() {
        enterNumberET = findViewById(R.id.enterNumberET);
        enteredNumberBTN = findViewById(R.id.enteredNumberBTN);
        randomNumberBTN = findViewById(R.id.randomNumberBTN);
        showFactTV = findViewById(R.id.showFactTV);
    }
    private void showDataList(List<Numbers> numbers) {
        customRecyclerView = findViewById(R.id.customRecyclerView);
        customAdapter = new CustomAdapter(numbers,this );
        layoutManager = new LinearLayoutManager(MainActivity.this);
        customRecyclerView.setLayoutManager(layoutManager);
        customRecyclerView.setAdapter(customAdapter);
    }
    private void setProgressDialog () {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }
}