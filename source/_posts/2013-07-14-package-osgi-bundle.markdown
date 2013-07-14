---
layout: post
title: "Package an external library as an OSGi bundle"
date: 2013-07-14 16:18
comments: false
categories:
 - OSGi
 - RCP
 - Eclipse
---

In an OSGi environment each plug-in has its own class-loader. If you use in your project an external dependency first thought is to embed the dependency in your bundle and that's all. This can lead to hard to debug ClassCastExceptions. 

<!-- more -->

Scenario:

 1. You use an external library is called **ZAB**
 2. You make a library called **FOO** which embeds the **ZAB** library
 3. Six months later somebody else create a second library **BAR** also embedding **ZAB**
 4. Then when you want to assembly the product and **FOO** provides some objects of class Zab.class to **BAR** library but the code will throw ClassCastException because the object of Zab.class was created in a different class-loader

The correct approach is to package **ZAB** as a separate OSGi Bundle
![Bundle external libraries](/resources/trouble-osgi-library-inclusion.png)

Fortunately maven bundle plug-in exists and packaging as OSGi bundle is piece of cake. Here you can see a simple configuration for packaging opencsv version 2.3 as an OSGi bundle. 

If you want to try it out, install latest maven and copy the following code content in a pom.xml, then run **mvn clean install**. You will end up having a target folder and inside an OSGi bundle called **dev.anproca.example.opencsv-2.3.0-SNAPSHOT.jar**

    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
	    <modelVersion>4.0.0</modelVersion>

	    <groupId>dev.anproca.example</groupId>
	    <artifactId>dev.anproca.example.opencsv</artifactId>
	    <version>2.3.0-SNAPSHOT</version>
	    <packaging>bundle</packaging>

	    <build>
		    <plugins>
			    <plugin>
				    <groupId>org.apache.felix</groupId>
				    <artifactId>maven-bundle-plugin</artifactId>
				    <extensions>true</extensions>
				    <configuration>
					    <instructions>
						    <_exportcontents>*</_exportcontents>
						    <Embed-Dependency>*</Embed-Dependency>
						    <Import-Package></Import-Package> 
						    <Bundle-ClassPath>{maven-dependencies}</Bundle-ClassPath>
						    <Embed-Transitive>true</Embed-Transitive>
						    <Embed-Directory>jars</Embed-Directory>
						    <Bundle-SymbolicName>${project.artifactId}</Bundle-SymbolicName>
						    <_failok>true</_failok>
						    <_nouses>true</_nouses>
					    </instructions>
				    </configuration>
			    </plugin>
		    </plugins>
	    </build>

	    <dependencies>
		    <dependency>
			    <groupId>net.sf.opencsv</groupId>
			    <artifactId>opencsv</artifactId>
			    <version>2.3</version>
		    </dependency>            
	    </dependencies>

    </project>

For more advanced configurations please visit [maven bundle plug-in website][maven-bnd]

[maven-bnd]: http://felix.apache.org/site/apache-felix-maven-bundle-plugin-bnd.html "Maven Bundle Plugin"
