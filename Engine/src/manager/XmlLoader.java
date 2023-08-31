package manager;

import exception.FileNotFoundException;
import exception.XmlNameException;
import schema.generated.PRDWorld;
import simulation.definition.SimulationDefinition;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static factory.definition.FactoryDefinition.createSimulationDefinition;

public class XmlLoader {
    private final static String JAXB_PACKAGE = "schema.generated";
    private String filePath;
    private PRDWorld prdWorld;

    public SimulationDefinition loadXmlData(String filePath) throws IOException, JAXBException {
        setFilePath(filePath);

        InputStream inputStream = null;
        try{
            inputStream = Files.newInputStream(new File(this.filePath).toPath());   
        }
        catch (Exception exception) {}
        this.prdWorld = deserializeFrom(inputStream);

        return createSimulationDefinition(this.prdWorld);
    }

    private PRDWorld deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(JAXB_PACKAGE);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (PRDWorld) unmarshaller.unmarshal(inputStream);
    }

    private void setFilePath(String filePath) {
        if(!filePath.endsWith(".xml")) {
            throw new XmlNameException("XmlNameException: the xml file path: '" + filePath + "' is not valid\n" +
                    "       Note that xml file path has to ends with '.xml.! Error occurred in class LoadXml.");
        }
        else {
            File file = new File(filePath);
            if(!file.exists()){
                throw new FileNotFoundException("FileNotFoundException: the xml file path: '" + filePath + "' was not found");
            }

            this.filePath = filePath;
        }
    }
}
