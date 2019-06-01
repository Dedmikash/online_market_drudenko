package com.gmail.dedmikash.market.service.parser.impl;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

public class XMLReaderWithoutNamespace extends StreamReaderDelegate {
    public XMLReaderWithoutNamespace(XMLStreamReader reader) {
        super(reader);
    }

    @Override
    public String getAttributeNamespace(int arg0) {
        return "";
    }

    @Override
    public String getNamespaceURI() {
        return "";
    }
}