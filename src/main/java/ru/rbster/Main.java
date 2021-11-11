package ru.rbster;

import java.sql.*;

public class Main {
    private Long N;
    private DriverManager driver;
    private String url = "jdbc:postgresql://localhost:5432/magnit";
    private String user = "postgres";
    private String password = "postgres";

    public void setN(Long N) {
        this.N = N;
    }
//    public void setDriver(Driver driver) {
//        this.driver = driver;
//    }

    Main(){}

    public static void main(String[] args) {
        Long N = 1000000L;
        String url = "jdbc:postgresql://localhost:5432/magnit";
        String user = "postgres";
        String password = "postgres";
	// write your code here
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            final PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM TEST");
            deleteStatement.execute();
            deleteStatement.close();

            try (final PreparedStatement st = conn.prepareStatement("INSERT INTO TEST VALUES (?)")) {
                for (long i = 1; i != N + 1; i++) {
                    st.setLong(1, i);
                    st.execute();
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
