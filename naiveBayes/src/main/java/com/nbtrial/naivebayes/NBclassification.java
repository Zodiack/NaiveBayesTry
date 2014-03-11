package com.nbtrial.naivebayes;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class NBclassification extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nbclassification);

        // load data
        ArffLoader loader = new ArffLoader();
        try {
            loader.setFile(new File("/storage/sdcard/features.arff"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Instances structure = null;
        try {
            structure = loader.getStructure();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        assert structure != null;
        structure.setClassIndex(structure.numAttributes() - 1);

        // train NaiveBayes
        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        try {
            nb.buildClassifier(structure);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Instance current;
        try {
            while ((current = loader.getNextInstance(structure)) != null)
                nb.updateClassifier(current);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nbclassification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

}
