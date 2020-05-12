package me.accessoreaze.scraper.database;

import me.accessoreaze.scraper.database.mysql.listener.SQLListener;

import java.sql.Connection;

public abstract class Database {

    public abstract Connection openConnection();

    public abstract Connection getConnection();

    public abstract boolean isConnected();

    public abstract void closeConnection();

    public abstract void addListener(SQLListener listener);
}
