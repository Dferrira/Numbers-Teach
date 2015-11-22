package com.dferreira.numbers_teach.ui_model;


import android.content.Context;
import android.util.Log;

import com.dferreira.commons.wavefront.WfObject;
import com.dferreira.commons.wavefront.WfParser;
import com.dferreira.numbers_teach.R;

import java.io.Serializable;

/**
 * Data that is going to support the Europe map in the UI
 */
public class Europe3DData implements Serializable {


    @SuppressWarnings("unused")
    private static final long serialVersionUID = 3166643603945566734L;

    private final int NUMBER_OF_COUNTRIES = 5;

    private Obj3DData continent;
    private Obj3DData[] countries;
    private final Context context;

    /**
     * Context the Europe3DData will be used
     *
     * @param context context where the European map will be called
     */
    public Europe3DData(Context context) {
        this.context = context;
    }

    /**
     * Load the data of the countries and other parts of the continent
     */
    public void loadModel() {
        WfParser wfParser = new WfParser(context);

        WfObject portugalObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.portugal), R.mipmap.pt_flag);
        WfObject franceObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.france), R.mipmap.fr_flag);
        WfObject ukObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.uk), R.mipmap.en_flag);
        WfObject irelandObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.ireland), R.mipmap.ie_flag);
        WfObject germanObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.german), R.mipmap.ge_flag);
        WfObject europeObj = wfParser.loadObj(context.getResources().openRawResource(R.raw.europe), R.mipmap.grass);


        Obj3DData portugal = new Obj3DData();
        portugal.setWfObject(portugalObj);

        portugal.setPosition(0.0f, 0.0f, 0.0f);
        portugal.setTag("pt");


        Obj3DData france = new Obj3DData();
        france.setWfObject(franceObj);
        france.setPosition(0.0f, 0.0f, 0.0f);
        france.setTag("fr");


        Obj3DData uk = new Obj3DData();
        uk.setWfObject(ukObj);
        uk.setPosition(0.0f, 0.0f, 0.0f);
        uk.setTag("uk");


        Obj3DData ireland = new Obj3DData();
        ireland.setWfObject(irelandObj);
        ireland.setPosition(0.0f, 0.0f, 0.0f);
        ireland.setTag("ie");

        Obj3DData german = new Obj3DData();
        german.setWfObject(germanObj);
        german.setPosition(0.0f, 0.0f, 0.0f);
        german.setTag("ge");

        this.countries = new Obj3DData[NUMBER_OF_COUNTRIES];
        countries[0] = portugal;
        countries[1] = france;
        countries[2] = uk;
        countries[3] = ireland;
        countries[4] = german;

        continent = new Obj3DData();
        continent.setWfObject(europeObj);
        continent.setPosition(0.0f, 0.0f, 0.0f);
        continent.setTag("continent");
    }


    /**
     * @return An array with countries that are going to be selectable
     */
    public Obj3DData[] getCountries() {
        return countries;
    }

    /**
     * @return A reference to the object that represents the European continent
     */
    public Obj3DData getContinent() {
        return continent;
    }

    /**
     * @param picked Value that was selected in the screen
     *
     * @return The tag of the of the country selected
     */
    public Object detectCountrySelected(int picked) {
        for (int i = 0; i < NUMBER_OF_COUNTRIES; i++) {
            Obj3DData country = countries[i];
            if (country == null) {
                continue;
            }

            if (picked == country.getWfObject().getObjCode()) {
                country.setSelected(true);
                return country.getTag();
            }
        }
        //No country was selected
        return null;
    }
}
