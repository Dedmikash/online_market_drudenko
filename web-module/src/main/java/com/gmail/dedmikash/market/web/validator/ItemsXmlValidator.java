package com.gmail.dedmikash.market.web.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ItemsXmlValidator {
    private static final Logger logger = LoggerFactory.getLogger(ItemsXmlValidator.class);

    public boolean validateXML(InputStream xmlInputStream) {
        Source schemaFile;
        try {
            schemaFile = new StreamSource(new ClassPathResource("schema.xsd").getFile());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        Source xmlFile = new StreamSource(xmlInputStream);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }
}
