package com.pop.popularmovies.Utilities;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pop.popularmovies.models.MoviesModel.Result;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataConverter implements Serializable {

    @TypeConverter // note this annotation
    public String fromOptionValuesList(Result optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Result>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Result toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Result>() {
        }.getType();
        Result productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }

}
