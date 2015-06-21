#Maven XML Merge Plugin

Plugin maven that allow to merge a XML Document with another one annotated by XDT processing instructions

By example, merging this XML DOcument

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
    <Logging>
        <Logger name="second" level="WARN" xdt:Transform="Insert" />
    </Logging>
</Root>
```

with this one :

```xml
<Root xmlns:xdt="http://schemas.microsoft.com/XML-Document-Transform">
    <Logging>
        <Logger name="second" level="WARN" xdt:Transform="Insert" />
    </Logging>
</Root>
```

generate this last :

```xml
<Root>
    <Logging>
        <Logger name="myFirst" level="INFO"/>
            <Logger name="second" level="WARN" />
    </Logging>
</Root>
```


For more information of merging possibility, read the documentation of https://github.com/hikage/xdt4j

##Building

    mvn clean install



Usage
=====



##Available Goal

### xmlmerger:mergexml

Here is the available parameters for this goal :

**baseDirectory** :

* **Type**:          java.lang.File
* **Description**:    The input directory in which the XML Document base can be found.
*  **Default value** :  ${project.build.outputDirectory}

**inputDirectory** :

* **Type**:          java.lang.File
* **Description**:    The input directory in which the XML Merge Data can be found.
*  **Default value** :  ${basedir}/src/main/xmlmerge


**outputDirectory** :

* **Type**:          java.lang.File
* **Description**:    The output directory into which to copy the genereated XML.
*  **Default value** :  ${project.build.outputDirectory}

**mergeFilenamePattern** :

* **Type**:          String (regex)
* **Description**:    The mergeFilenamePattern used to find XML Document to merge. It have to return two groups, including the second is the name of the file in which it must be merged. The default pattern assume that the XML Document to be merged has the same name as the base XML Document
*  **Default value** :  ()(.*)
