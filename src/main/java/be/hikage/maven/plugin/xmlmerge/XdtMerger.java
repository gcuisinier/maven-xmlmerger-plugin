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

import be.hikage.xdt4j.XdtTransformer;
import org.codehaus.plexus.component.annotations.Component;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import java.io.IOException;

@Component(role = XmlMerger.class)
public class XdtMerger implements XmlMerger {
    @Override
    public Document mergeXml(Document documentBase, Document mergeData) throws DocumentException, IOException {

        XdtTransformer transformer = new XdtTransformer();

        return transformer.transform(documentBase, mergeData);

    }
}
