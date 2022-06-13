package com.anyarusova.server.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;

public interface Table<T> {
    void init() throws SQLException;

    PriorityQueue<T> getCollection() throws SQLException;

    T mapRowToObject(ResultSet resultSet) throws SQLException;

    int add(T element) throws SQLException;
}
