package com.fub.fifaultimatebravery.Repositories;

import android.content.Context;

public class Repository {
    private static Repository instance;

    public static Repository getRepository(final Context context){
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository(context);
                }
            }
        }
        return instance;
    }

    private Repository(Context context){

    }

}
