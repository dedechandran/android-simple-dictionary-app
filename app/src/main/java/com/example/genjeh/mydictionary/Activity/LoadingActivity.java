package com.example.genjeh.mydictionary.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.genjeh.mydictionary.AppPreference;
import com.example.genjeh.mydictionary.Database.DictionaryHelper;
import com.example.genjeh.mydictionary.Database.dbContractIndonesia;
import com.example.genjeh.mydictionary.Database.dbContractInggris;
import com.example.genjeh.mydictionary.ModelData.Dictionary;
import com.example.genjeh.mydictionary.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    DictionaryHelper dictionaryHelper;

    LoadDictionary loadDictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);
        dictionaryHelper = new DictionaryHelper(this);
        loadDictionary = new LoadDictionary();
        loadDictionary.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class LoadDictionary extends AsyncTask<Void,Integer,Void> {
        private AppPreference appPreference;
        private DictionaryHelper dictionaryHelper;
        private double progress;
        private double maxProgress=100;
        @Override
        protected Void doInBackground(Void... voids) {
            boolean firstRun = appPreference.getFirstRun();
            if(firstRun){
                ArrayList<Dictionary> indonesia_dictionaries = getDictionaries(R.raw.indonesia_english);
                ArrayList<Dictionary> english_dictionaries = getDictionaries(R.raw.english_indonesia);
                progress = 20;
                publishProgress((int) progress);
                double progressDiff=(maxProgress-progress)/(indonesia_dictionaries.size()+english_dictionaries.size());
                dictionaryHelper.open();
                dictionaryHelper.beginTransaction();
                try{
                    for(Dictionary dictionary:indonesia_dictionaries){
                        dictionaryHelper.insertTransaction(dictionary, dbContractIndonesia.TABLE_NAME,dbContractIndonesia.dictColumns.WORD,dbContractIndonesia.dictColumns.DESCRIPTION);
                        progress+=progressDiff;
                        publishProgress((int)progress);
                    }
                    for(Dictionary dictionary:english_dictionaries){
                        dictionaryHelper.insertTransaction(dictionary, dbContractInggris.TABLE_NAME,dbContractInggris.dictColumns.WORD,dbContractInggris.dictColumns.DESCRIPTION);
                        progress+=progressDiff;
                        publishProgress((int)progress);
                    }
                    dictionaryHelper.setTransactionSuccessfull();
                }catch (Exception e){
                    e.printStackTrace();
                }
                dictionaryHelper.endTransaction();
                dictionaryHelper.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxProgress);
            }else{
                synchronized (this){
                    try {
                        this.wait(1000);
                        progress=50;
                        publishProgress((int)progress);
                        this.wait(1000);
                        publishProgress((int) maxProgress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            appPreference = new AppPreference(LoadingActivity.this);
            dictionaryHelper = new DictionaryHelper(LoadingActivity.this);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        private ArrayList<Dictionary> getDictionaries(int raw_id) {
            InputStream inputStream = getResources().openRawResource(raw_id);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<Dictionary> dictionaries = new ArrayList<>();
            String line;
            try{
                Dictionary dictionary;
                while((line=reader.readLine())!=null){
                    dictionary = new Dictionary();
                    String[] splitstr = line.split("\t");
                    dictionary.setDictWord(splitstr[0]);
                    dictionary.setDictDesc(splitstr[1]);

                    dictionaries.add(dictionary);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return dictionaries;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadDictionary!=null){
            loadDictionary.cancel(true);
        }
    }
}
