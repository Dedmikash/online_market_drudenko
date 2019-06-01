package com.gmail.dedmikash.market.service.parser;

import com.gmail.dedmikash.market.service.model.ItemDTO;

import java.io.InputStream;
import java.util.List;

public interface JaxbParser {
    List<ItemDTO> parse(InputStream inputStream);
}
