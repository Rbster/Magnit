package ru.rbster;

import java.sql.SQLException;
import java.util.List;
public interface RelationalDataInterface {
    public <T> void createAll(T element) throws SQLException;
    public <T> List<T> findAll() throws SQLException;
    public <T> void deleteAll() throws SQLException;
}