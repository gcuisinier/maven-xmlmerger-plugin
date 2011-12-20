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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Goal which merge XML file
 *
 * @goal mergexml
 * @phase prepare-package
 */
public class MergeXmlMojo
        extends AbstractMojo {

    /**
     * The output directory into which to copy the resources.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File outputDirectory;

    private String pattern = "(MERGE\\.)(.*)";

    public void execute() throws MojoExecutionException {
        getLog().info("EXECUTE on " + outputDirectory.getAbsolutePath());
        List<File> xmlFiles = new ArrayList<File>();

        Pattern regex = Pattern.compile(pattern);

        findXmlToMerge(outputDirectory, xmlFiles);

        getLog().info("FILE FOUND :" + xmlFiles.size());


        try {
            for (File fileToMerge : xmlFiles) {
                Matcher matcher = regex.matcher(fileToMerge.getName());
                if (matcher.matches() && matcher.groupCount() == 2) {
                    if (checkFilebaseExist(fileToMerge.getParentFile(), matcher.group(2))) {
                        getLog().info("Merge file " + fileToMerge.getName());
                        File baseFile = new File(fileToMerge.getParentFile(), matcher.group(2));

                        MergeXml.mergeXml(baseFile, fileToMerge);
                    } else {
                        getLog().warn("No filebase found for " + fileToMerge.getAbsolutePath());
                    }


                } else {
                    getLog().warn("The file do not matches regex");
                }
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Unable to merge xml", e);
        }

    }

    private boolean checkFilebaseExist(File baseFolder, String group) {
        File baseFile = new File(baseFolder, group);
        return baseFile.exists();

    }

    private void findXmlToMerge(File fileToProcess, List<File> xmlFiles) {

        RegexFileFilter filter2 = new RegexFileFilter(pattern);

        Collection<File> filesFound = FileUtils.listFiles(fileToProcess, filter2, DirectoryFileFilter.DIRECTORY);

        xmlFiles.addAll(filesFound);

    }
}
