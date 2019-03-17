package com.example.genjeh.mydictionary.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.genjeh.mydictionary.ModelData.Dictionary;
import com.example.genjeh.mydictionary.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_DICTIONARY="extra_dictionary";
    @BindViews({R.id.tv_word,R.id.tv_description})
    List<TextView> dictProperties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Dictionary dictionary = getIntent().getParcelableExtra(EXTRA_DICTIONARY);
        dictProperties.get(0).setText(dictionary.getDictWord());
        dictProperties.get(1).setText(dictionary.getDictDesc());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
