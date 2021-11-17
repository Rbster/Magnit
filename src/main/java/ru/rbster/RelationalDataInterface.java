package ru.rbster;

import java.sql.SQLException;
import java.util.List;
public interface RelationalDataInterface {
    public void createAll(Long N) throws SQLException;
    public List<Long> findAll() throws SQLException;
    public void deleteAll() throws SQLException;
}