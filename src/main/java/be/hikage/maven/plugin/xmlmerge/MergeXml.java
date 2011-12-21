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

import org.dom4j.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO
 */
public class MergeXml implements XmlMerger {
    @Override
    public void mergeXml(Document documentBase, Document mergeData) throws DocumentException, IOException {
        Map<String, String> namespaceUris = prepareNamespace(mergeData);

        processAdd(mergeData, documentBase, namespaceUris);
        processReplaceContent(mergeData, documentBase, namespaceUris);

    }

    private void processReplaceContent(Document mergeData, Document documentBase, Map<String, String> namespaceUris) {
        List remplaceNode = mergeData.selectNodes("/Merge/ReplaceContent");

        for (Object node : remplaceNode) {
            if (node instanceof Element) {
                Element remplaceElement = (Element) node;
                String xPathToFind = remplaceElement.attributeValue("xpath");

                XPath xPath = DocumentHelper.createXPath(xPathToFind);
                xPath.setNamespaceURIs(namespaceUris);

                Node targetNode = xPath.selectSingleNode(documentBase);

                if (targetNode != null) {
                    if (targetNode instanceof Element) {
                        Element targetElement = (Element) targetNode;
                        Element copy = remplaceElement.createCopy();
                        targetElement.setContent(copy.content());
                    }
                }

            }
        }
    }


    private static Map<String, String> prepareNamespace(Document mergeData) {
        Map<String, String> namespaceUris = new HashMap<String, String>();

        List<Element> namespaceElements = mergeData.selectNodes("/Merge/Namespaces/Namespace");

        for (Element nameSpace : namespaceElements) {
            namespaceUris.put(nameSpace.attributeValue("prefix"), nameSpace.attributeValue("uri"));
        }

        return namespaceUris;

    }


    private void processAdd(Document mergeData, Document base, Map<String, String> namespaceUris) {
        List addNodes = mergeData.selectNodes("/Merge/Add");

        for (Object node : addNodes) {
            if (node instanceof Element) {
                Element addElement = (Element) node;
                String xPathToFind = addElement.attributeValue("xpath");

                XPath xPath = DocumentHelper.createXPath(xPathToFind);
                xPath.setNamespaceURIs(namespaceUris);

                Node targetNode = xPath.selectSingleNode(base);

                if (targetNode != null) {
                    if (targetNode instanceof Element) {
                        Element targetElement = (Element) targetNode;
                        Element copy = addElement.createCopy();
                        targetElement.appendContent(copy);
                    }
                }

            }
        }

    }
}
