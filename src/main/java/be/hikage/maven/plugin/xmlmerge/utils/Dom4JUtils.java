package be.hikage.maven.plugin.xmlmerge.utils;


import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URL;

public abstract class Dom4JUtils {

    public static Document readDocument(URL file, StringBuilder prologBuffer) throws DocumentException, IOException {
        try {

            SAXReader reader = new SAXReader();
            return reader.read(file.openStream());
        } catch (DocumentException e) {
            if (e.getMessage().contains("Content is not allowed in prolog") && prologBuffer != null) {

                String xmlText = IOUtils.toString(file.openStream());
                int firstXmlCharIndex = xmlText.indexOf("<");
                if (firstXmlCharIndex == -1) throw e;
                prologBuffer.append(xmlText.substring(0, firstXmlCharIndex-1));
                SAXReader reader = new SAXReader();
                return reader.read(new StringReader(xmlText.substring(firstXmlCharIndex)));
            }
            else
                throw e;
        }

    }

    public static Document readDocument(URL stream) throws DocumentException, IOException {
        return readDocument(stream, null);
    }
}
