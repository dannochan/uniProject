package de.uniba.wiai.dsg.ajp.assignment2.literature.logic;

import de.uniba.wiai.dsg.ajp.assignment2.literature.logic.model.Database;

public abstract class DatabaseProvider {

    private static volatile Database DB_INSTANCE;

    public static void setDbInstance(Database dbInstance){
        if(DB_INSTANCE == null) DB_INSTANCE = dbInstance;
    }

    public static Database getDbInstance(){
        return DB_INSTANCE;
    }
}
