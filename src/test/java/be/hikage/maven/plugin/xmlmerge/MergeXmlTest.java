/*
 * Copyright © 2011  The original author or authors
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package be.hikage.maven.plugin.xmlmerge;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test classe for @links(be.hikage.maven.plugin.xmlmerge.MergeXml)
 */
public class MergeXmlTest {

    private MergeXml merger = new MergeXml();

    private Document baseDocument;


    @Before
    public void setUp() throws DocumentException {

        baseDocument = loadXml("BaseDocument.xml");
        XMLUnit.setNormalize(true);
        XMLUnit.setIgnoreWhitespace(true);


    }

    @Test
    public void testAddInExistingContainer() throws DocumentException, SAXException, IOException {

        Document mergeData = loadXml("TestAdd-AddInExistingContainer.xml");

        merger.mergeXml(baseDocument, mergeData);

        Document expected = loadXml("TestAdd-AddInExistingContainerExpectedResult.xml");

        XMLAssert.assertXMLEqual(expected.asXML(), baseDocument.asXML());
    }

    @Test
    public void testAddInNonExistingContainer() throws DocumentException, SAXException, IOException {

        Document mergeData = loadXml("TestAdd-AddInNonExistingContainer.xml");

        merger.mergeXml(baseDocument, mergeData);

        Document expected = loadXml("TestAdd-AddInNonExistingContainerExpectedResult.xml");

        XMLAssert.assertXMLEqual(expected.asXML(), baseDocument.asXML());
    }


    private Document loadXml(String filename) throws DocumentException {
        SAXReader reader = new SAXReader();
        InputStream inputStream = this.getClass().getResourceAsStream(filename);
        return reader.read(inputStream);
    }

    /*private assertXmlEquats(String expected, String actual) throws IOException, SAXException {

        XMLUnit.setNormalize(true);
        XMLUnit.setIgnoreWhitespace(true);

        DetailedDiff detailedDiff = new DetailedDiff(new Diff(expected, actual));

        for (Difference dif : detailedDiff.getAllDifferences()) {

        }

    }*/
}
