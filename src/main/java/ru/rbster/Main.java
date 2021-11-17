package ru.rbster;

import ru.rbster.XMLEntity.Entries;
import ru.rbster.XMLEntity.Entry;
import ru.rbster.dbAccess.RelationalDataInterface;
import ru.rbster.dbAccess.RelationalDataInterfaceImpl;
import ru.rbster.xmlAccess.XMLDataInterface;
import ru.rbster.xmlAccess.XMLDataInterfaceImpl;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

import javax.xml.bind.*;
import javax.xml.transform.TransformerException;

public class Main implements Serializable {
    final static long serialVersionUID = 1L;

    private Long N;
    private String url = "jdbc:postgresql://localhost:5432/magnit";
    private String user = "postgres";
    private String password = "postgres";

    public void setN(Long N) {
        this.N = N;
    }
    public void setUrl(String url) { this.url = url; }
    public void setUser(String user) { this.user = user; }
    public void setPassword(String password) { this.password = password; }
    public Long getN() { return N; }
    public String getUrl() { return url; }
    public String getUser() { return user; }
    public String getPassword() { return password; }

    Main(){}

    public void main(String[] args) {
//        Long N = 4L;
//        String url = "jdbc:postgresql://localhost:5432/magnit";
//        String user = "postgres";
//        String password = "postgres";

        List<Long> numArray = null;

        RelationalDataInterface dBInterface = new RelationalDataInterfaceImpl(url, user, password);
        try {
            dBInterface.deleteAll();
            dBInterface.createAll(N);
            numArray = dBInterface.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Entries entries = new Entries();

        for (Long n : numArray) {
            Entry entry = new Entry();
            entry.field = n;
            entries.entries.add(entry);
        }

        XMLDataInterface xmlDataInterface = new XMLDataInterfaceImpl();

        try {
            xmlDataInterface.saveAll(entries, Paths.get("1.xml"), Entries.class, Entry.class);
            xmlDataInterface.transform(Paths.get("1.xml"),
                    Paths.get("transform.xslt"),
                    Paths.get("2.xml"),
                    Entries.class, Entry.class);
        } catch (JAXBException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }
}
