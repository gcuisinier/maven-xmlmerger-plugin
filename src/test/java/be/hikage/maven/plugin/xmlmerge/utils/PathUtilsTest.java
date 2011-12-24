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

package be.hikage.maven.plugin.xmlmerge.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class PathUtilsTest {


    @Test
    public void testSimpleUnix() {

        File target = new File("/Users/hikage/Dev/opensource/maven-xmlmerge-plugin/pom.xml");
        File base = new File("/Users/hikage/");

        String relativePath = PathUtils.getRelativePath(target, base);

        Assert.assertEquals("/Dev/opensource/maven-xmlmerge-plugin/pom.xml", relativePath);
    }


}
