package com.kangyonggan.server.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;

/**
 * @author kangyonggan
 * @since 7/19/18
 */
public final class XmlUtil {

    private XmlUtil() {
    }

    private static final SAXReader READER = new SAXReader();

    public static Document read(String filename) throws DocumentException {
        return READER.read(new File(filename));
    }

    public static Document read(File file) throws DocumentException {
        return READER.read(file);
    }

    public static Document read(URL url) throws DocumentException {
        return READER.read(url);
    }

    public static Document parseText(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }


}
