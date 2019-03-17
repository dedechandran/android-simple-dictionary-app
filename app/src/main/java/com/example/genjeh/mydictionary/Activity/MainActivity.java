package com.example.genjeh.mydictionary.Activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.genjeh.mydictionary.Database.DictionaryHelper;
import com.example.genjeh.mydictionary.Database.dbContractIndonesia;
import com.example.genjeh.mydictionary.Database.dbContractInggris;
import com.example.genjeh.mydictionary.ModelData.Dictionary;
import com.example.genjeh.mydictionary.Adapter.DictionaryAdapter;
import com.example.genjeh.mydictionary.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.tv_result)
    TextView tvResult;

    private DictionaryAdapter adapter;
    private DictionaryHelper dictionaryHelper;
    private ArrayList<Dictionary> result = new ArrayList<>();
    private boolean indonesiaToInggris=true;
    private String EXTRA_STATE="extra_state";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_STATE,indonesiaToInggris);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        adapter = new DictionaryAdapter(this);
        adapter.setData(result);
        adapter.notifyDataSetChanged();
        dictionaryHelper = new DictionaryHelper(this);

        tvResult.setText(String.valueOf(adapter.getItemCount()));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dictionaryHelper.open();
                if(indonesiaToInggris){
                    result = dictionaryHelper.query(query, dbContractIndonesia.TABLE_NAME,dbContractIndonesia.dictColumns._ID,dbContractIndonesia.dictColumns.WORD,dbContractIndonesia.dictColumns.DESCRIPTION);
                }else{
                    result = dictionaryHelper.query(query, dbContractInggris.TABLE_NAME,dbContractInggris.dictColumns._ID,dbContractInggris.dictColumns.WORD,dbContractInggris.dictColumns.DESCRIPTION);
                }
                adapter.setData(result);
                adapter.notifyDataSetChanged();
                tvResult.setText(String.valueOf(adapter.getItemCount()));
                dictionaryHelper.close();
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter.clearData();
                    adapter.notifyDataSetChanged();
                    tvResult.setText(String.valueOf(adapter.getItemCount()));
                }else{
                    dictionaryHelper.open();
                    if(indonesiaToInggris){
                        result = dictionaryHelper.query(newText, dbContractIndonesia.TABLE_NAME,dbContractIndonesia.dictColumns._ID,dbContractIndonesia.dictColumns.WORD,dbContractIndonesia.dictColumns.DESCRIPTION);
                    }else{
                        result = dictionaryHelper.query(newText, dbContractInggris.TABLE_NAME,dbContractInggris.dictColumns._ID,dbContractInggris.dictColumns.WORD,dbContractInggris.dictColumns.DESCRIPTION);
                    }
                    adapter.setData(result);
                    adapter.notifyDataSetChanged();
                    tvResult.setText(String.valueOf(adapter.getItemCount()));
                    dictionaryHelper.close();
                }
                return true;
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState!=null){
            indonesiaToInggris=savedInstanceState.getBoolean(EXTRA_STATE);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_indonesia_inggris) {
            indonesiaToInggris = true;
        } else if (id == R.id.nav_inggris_indonesia) {
            indonesiaToInggris = false;
        }
        if(!TextUtils.isEmpty(searchView.getQuery())){
            searchView.setQuery(null,false);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
