package com.example.jpedretti.chapter4recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    static View.OnClickListener mainOnClickListener;
    public static final String SELECTED_ITEM_ID = "selectedItemId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainOnClickListener = new MainOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<MainDataDef> mainData = new ArrayList<>();

        for (int i = 0; i < MainData.nameArray.length; i++){
            mainData.add(new MainDataDef(
               MainData.imageArray[i],
               MainData.nameArray[i],
               MainData.infoArray[i]
            ));
        }

        RecyclerView.Adapter adapter = new MainAdapter(mainData);
        recyclerView.setAdapter(adapter);
    }

    private class MainOnClickListener implements View.OnClickListener{

        private final Context context;

        private MainOnClickListener(Context context){
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int currentItem = recyclerView.getChildAdapterPosition(v);
            //Toast.makeText(context,String.valueOf(currentItem),Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
            intent.putExtra(SELECTED_ITEM_ID,currentItem);
            startActivity(intent);
        }
    }
}
