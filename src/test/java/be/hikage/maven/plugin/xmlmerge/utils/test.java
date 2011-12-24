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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    private String basedir;

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("()(.*)");

        String main = "MERGED.pom.xml";

        Matcher matcher = pattern.matcher(main);

        if (matcher.matches()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                System.out.println(i + ":" + matcher.group(i + 1));
            }
        }

    }

    public void test() throws IOException {


        File file = new File(basedir, "target/classes/pom.xml");

        String pomContent = FileUtils.readFileToString(file);

        if (!pomContent.contains("<groupId>junit</groupId>") || !pomContent.contains("<artifactId>xdt4j</artifactId>"))
            ;

    }
}
