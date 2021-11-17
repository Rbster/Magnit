package ru.rbster.xmlAccess;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface XMLDataInterface {
    public <T> void saveAll(T elementsContainingObject, Path targetPath, Class... classes) throws JAXBException;

    public void transform(Path sourcePath, Path transformationPath, Path targetPath, Class... classes) throws JAXBException,
            TransformerConfigurationException, TransformerException, IOException;
}
