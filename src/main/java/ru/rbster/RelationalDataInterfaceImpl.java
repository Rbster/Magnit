package ru.rbster;

import java.sql.SQLException;
import java.util.List;

public class RelationalDataInterfaceImpl implements RelationalDataInterface {
    private String url = "jdbc:postgresql://localhost:5432/magnit";
    private String user = "postgres";
    private String password = "postgres";


    RelationalDataInterfaceImpl(String url, String user, String password) {
        this.url = url;
        this.user = password;
        this.password = password;
    }


    @Override
    public <T> void createAll(T element) throws SQLException {

    }

    @Override
    public <T> List<T> findAll() throws SQLException {
        return null;
    }

    @Override
    public <T> void deleteAll() throws SQLException {

    }
}
