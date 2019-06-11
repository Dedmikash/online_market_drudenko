package com.gmail.dedmikash.market.service.parser.impl;

import com.gmail.dedmikash.market.service.model.ItemCatalogXML;
import com.gmail.dedmikash.market.service.model.ItemDTO;
import com.gmail.dedmikash.market.service.parser.JaxbParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.List;

@Service
public class JaxbParserImpl implements JaxbParser {
    private static final Logger logger = LoggerFactory.getLogger(JaxbParserImpl.class);

    @Override
    public List<ItemDTO> parse(InputStream inputStream) {
        try {
            XMLStreamReader xmlStreamReader = XMLInputFactory.newFactory().createXMLStreamReader(inputStream);
            XMLReaderWithoutNamespace xmlReaderWithoutNamespace = new XMLReaderWithoutNamespace(xmlStreamReader);
            JAXBContext jaxbContext = JAXBContext.newInstance(ItemCatalogXML.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ItemCatalogXML itemCatalogXML = (ItemCatalogXML) unmarshaller.unmarshal(xmlReaderWithoutNamespace);
            return itemCatalogXML.getCatalog();
        } catch (JAXBException | XMLStreamException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
