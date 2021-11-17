package ru.rbster;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface XMLDataInterface {
    public <T> void saveAll(List<T> elements);
    public void transform(Path sourcePath, Path transformationPath, Path targetPath) throws JAXBException,
            TransformerConfigurationException, TransformerException, IOException;
}
