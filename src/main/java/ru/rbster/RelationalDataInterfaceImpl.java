package ru.rbster;

import java.sql.*;
import java.util.LinkedList;
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
    public void createAll(Long N) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            final PreparedStatement insertSt = conn.prepareStatement("INSERT INTO test SELECT i FROM generate_series(1, ?) as t(i)");
            insertSt.setLong(1, N);
            insertSt.execute();
        }
    }

    @Override
    public List<Long> findAll() throws SQLException {
        List<Long> numArray = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            final PreparedStatement findAllSt = conn.prepareStatement("SELECT field FROM test");
            try (ResultSet resultSet = findAllSt.executeQuery()) {
                int i = 0;
                while (resultSet.next()) {
                    numArray.add(resultSet.getLong(1));
                    i++;
                }
            }
        }
        return numArray;
    }

    @Override
    public void deleteAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            final PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM TEST");
            deleteStatement.execute();
            deleteStatement.close();
        }
    }
}
