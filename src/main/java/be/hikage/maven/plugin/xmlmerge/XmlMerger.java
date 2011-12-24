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

import org.dom4j.Document;

/**
 * Component that Merge the XML Framgment contained in the mergeData into
 * the documentBase
 */
public interface XmlMerger {

    /**
     * Merge two XML Document
     *
     * @param documentBase The basic XML document that will be altered
     * @param mergeData    The XML document that contains instructions to be merged
     * @return The XML document resulting from the merger
     */
    Document mergeXml(Document documentBase, Document mergeData);
}
