package ru.rbster.xmlAccess;

import ru.rbster.xmlAccess.XMLDataInterface;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class XMLDataInterfaceImpl implements XMLDataInterface {

    @Override
    public <T> void saveAll(T elementsContainingObject, Path targetPath, Class... classes) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(classes);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.marshal(elementsContainingObject, targetPath.toFile());
    }

    @Override
    public void transform(Path sourcePath, Path transformationPath, Path targetPath, Class... classes) throws JAXBException, TransformerException, IOException {
        Object entries;
        JAXBContext context = JAXBContext.newInstance(classes);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        entries = unmarshaller.unmarshal(sourcePath.toFile());

        JAXBSource source = new JAXBSource(context, entries);

// result
        try (final OutputStream out = Files.newOutputStream(targetPath)) {
            StreamResult result = new StreamResult(out);
// transformation
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer(new StreamSource(transformationPath.toFile()));
            t.transform(source, result);
        }
    }
}
