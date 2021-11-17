package ru.rbster;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.*;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Main implements Serializable {
    final static long serialVersionUID = 1L;

    private Long N;
//    private DriverManager driver;
    private String url = "jdbc:postgresql://localhost:5432/magnit";
    private String user = "postgres";
    private String password = "postgres";

    public void setN(Long N) {
        this.N = N;
    }
//    public void setDriver(Driver driver) {
//        this.driver = driver;
//    }
    public void setUrl(String url) { this.url = url; }
    public void setUser(String user) { this.user = user; }
    public void setPassword(String password) { this.password = password; }
    public Long getN() { return N; }
    public String getUrl() { return url; }
    public String getUser() { return user; }
    public String getPassword() { return password; }

    Main(){}

    public static void main(String[] args) {
        Long N = 100L;
        String url = "jdbc:postgresql://localhost:5432/magnit";
        String user = "postgres";
        String password = "postgres";


//        long[] numArray = new long[(int) (long) N];
        List<Long> numArray = new LinkedList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            final PreparedStatement deleteStatement = conn.prepareStatement("DELETE FROM TEST");
            deleteStatement.execute();
            deleteStatement.close();

//            try (final PreparedStatement st = conn.prepareStatement("INSERT INTO TEST VALUES (?)")) {
//                for (long i = 1; i != N + 1; i++) {
//                    st.setLong(1, i);
//                    st.execute();
//                }
//            }
            final PreparedStatement insertSt = conn.prepareStatement("INSERT INTO test SELECT i FROM generate_series(1, ?) as t(i)");
            insertSt.setLong(1, N);
            insertSt.execute();

            final PreparedStatement findAllSt = conn.prepareStatement("SELECT field FROM test");
            try (ResultSet resultSet = findAllSt.executeQuery()) {
                int i = 0;
                while (resultSet.next()) {
                    numArray.add(resultSet.getLong(1));
                    i++;
                }
            }
//////
            Entries entries = new Entries();

            for (Long n : numArray) {
                Entry entry = new Entry();
                entry.field = n;
                entries.entries.add(entry);
            }

            JAXBContext context = JAXBContext.newInstance(Entries.class, Entry.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(entries, Paths.get("1.xml").toFile());
            //// xml modification
// source

            Object entries2;
            JAXBContext context2 = JAXBContext.newInstance(Entries.class, Entry.class);
            Unmarshaller unmarshaller = context2.createUnmarshaller();
            entries2 = unmarshaller.unmarshal(Paths.get("1.xml").toFile());

            JAXBSource source = new JAXBSource(context2, entries2);

// result
            try (final OutputStream out = Files.newOutputStream(Paths.get("2.xml"))) {
                StreamResult result = new StreamResult(out);
// transformation
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer t = tf.newTransformer(new StreamSource("transform.xslt"));
//            Entries entries2 = (Entries) unmarshaller.unmarshal(Paths.get("1.xml").toFile());
                t.transform(source, result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
