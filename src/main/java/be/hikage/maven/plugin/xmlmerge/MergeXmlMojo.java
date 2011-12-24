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

import be.hikage.maven.plugin.xmlmerge.utils.PathUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * The input directory in which the XML Merge Data can be found.
     *
     * @parameter default-value="${basedir}/src/main/xmlmerge"
     * @required
     */
    private File inputDirectory;

    /**
     * The input directory in which the XML Document base can be found.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File baseDirectory;

    /**
     * The output directory into which to copy the resources.
     *
     * @parameter default-value="${project.build.outputDirectory}"
     * @required
     */
    private File outputDirectory;


    /**
     * The Xml Merge component instance that will be injected
     * by the Plexus runtime.
     *
     * @component
     */
    private XmlMerger xmlMerger;

    private String pattern = "(MERGE\\.)(.*)";

    public void execute() throws MojoExecutionException {
        getLog().info("EXECUTE on " + outputDirectory.getAbsolutePath());
        List<File> xmlFiles = new ArrayList<File>();

        Pattern regex = Pattern.compile(pattern);

        findXmlToMerge(inputDirectory, xmlFiles);

        getLog().info("Number of file found to merge :" + xmlFiles.size());


        try {
            for (File fileToMerge : xmlFiles) {
                Matcher matcher = regex.matcher(fileToMerge.getName());
                if (matcher.matches() && matcher.groupCount() == 2) {
                    String baseFileName = matcher.group(2);

                    getLog().info("Group 1 : " + matcher.group(1));
                    getLog().info("Group 2 : " + matcher.group(2));

                    File basefile = getBaseFile(fileToMerge, baseFileName);

                    File outputFile = getOutputFile(fileToMerge, baseFileName);

                    getLog().info("Merge Base :" + basefile.getAbsolutePath());
                    getLog().info("Merge Transform :" + fileToMerge.getAbsolutePath());
                    getLog().info("Merge Output :" + outputFile.getAbsolutePath());

                    if (basefile.exists()) {
                        getLog().info("Merge file " + fileToMerge.getName());
                        //File baseFile = new File(fileToMerge.getParentFile(), baseFileName);


                        Document result = xmlMerger.mergeXml(loadXml(basefile), loadXml(fileToMerge));

                        writeMergedXml(outputFile, result);

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

    private File getBaseFile(File fileToMerge, String baseFileName) {

        String relativePath = PathUtils.getRelativePath(fileToMerge, inputDirectory).replace(fileToMerge.getName(), "");

        System.out.println("RELATIVE :" + relativePath);

        File baseFile = new File(baseDirectory, relativePath + baseFileName);


        return baseFile;
    }

    private File getOutputFile(File fileToMerge, String baseFileName) {

        String relativePath = PathUtils.getRelativePath(fileToMerge, inputDirectory).replace(fileToMerge.getName(), "");

        File outputFile = new File(outputDirectory, relativePath + baseFileName);


        return outputFile;
    }


    private void findXmlToMerge(File fileToProcess, List<File> xmlFiles) {

        RegexFileFilter filter2 = new RegexFileFilter(pattern);

        Collection<File> filesFound = FileUtils.listFiles(fileToProcess, filter2, DirectoryFileFilter.DIRECTORY);

        xmlFiles.addAll(filesFound);

    }

    private Document loadXml(File baseFile) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(baseFile);
    }

    private void deleteMergeFile(File fileToMerge) {
        fileToMerge.delete();

    }

    private void writeMergedXml(File baseFile, Document base) throws IOException {
        FileOutputStream fos = new FileOutputStream(baseFile);
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(fos, format);
        writer.write(base);
        writer.flush();
        writer.close();

    }
}
