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

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class PathUtils {


    public static String getRelativePath(File children, File base) {

        String normalizedTargetPath = FilenameUtils.normalizeNoEndSeparator(children.getAbsolutePath());
        String normalizedBasePath = FilenameUtils.normalizeNoEndSeparator(base.getAbsolutePath());

        System.out.println(normalizedTargetPath);
        System.out.println(normalizedBasePath);


        return normalizedTargetPath.substring(normalizedBasePath.length());


    }
}
