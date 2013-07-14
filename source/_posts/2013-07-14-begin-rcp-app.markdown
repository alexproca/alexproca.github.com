---
layout: post
title: "So you want RCP"
date: 2013-07-14 13:38
comments: true
categories:
 - OSGi
 - RCP
 - Eclipse
---

#Fact

RCP developing in serious projects can quickly became a nightmare if you do not use the appropriate tools.

#Developer Desires

 * to have a good IDE
 * to automate build and tests
 * to automate packaging of external dependencies
 * to automate deliver of further updates
 * to automate build of a product

<!-- more -->

##IDE

In this post I will talk about eclipse as both platform and IDE. Yes you can use [intelij as IDE if you want][intelij] but it's kind of sacrilege.

When i write this post [eclipse Kepler][Kepler] is available as latest eclipse IDE. By default "Target platform" will point to latest plug-ins and features from current IDE and you will develop an RCP product based on that version of eclipse. However you can develop products based on older eclipse releases. For this you have to set "Target Platform" in order to include all plug-ins and features from your chosen version of eclipse.

##Automated build and tests

You will want to automate build and tests because this can be a boring task and when humans are doing boring tasks they make mistakes. Also if you have an configuration file you can transfer build knowledge to other people as easy as sharing a file.

The tool suitable for build and test RCP is [tycho project][tycho] which is a set of plug-ins for [maven3][maven3]. Tycho can have 2 approaches pom first and manifest first ( you can see some [pros and cons on stack-overflow][manifest-first-vs-pom-first] ). My favorite approach for RCP is manifest-first because I know from the beginning how the manifest will look like.

Tycho will allow you to automate:

 - build of plug-ins
 - build of features
 - build of p2 sites
 - running of tests in an OSGi container

##Automate Packaging of External dependencies

The pom first approach is helpful in RCP development when you want to include some external libraries which are not OSGi. Yes you can put that dependencies in a plug-in class-path but this approach can lead to some hard to debug troubles.

The correct approach to this problem is to bundle your external jar of choice as an OSGi bundle. For this task you can use [maven bundle plug-in][maven-bundle] and create a p2 site with all needed libraries.

![Class Cast Trouble](/resources/trouble-osgi-library-inclusion.png)

##Automate build of a product and further updates 

You can automate the assembly of your p2 site and assembly of the p2 site. But here are some tricks. P2 support can be found in artifactory pro and nexus, but for artifactory pro you have to pay license and if you want nexus [here is "how to"][nexus-p2]. If you want something simple you can configure a webdav server, with tomcat for example and use maven wagon webdav to upload the p2 generated site.

In the product assembly phase you specify which are your bundles and tycho will build for you products for windows, linux and macos.

#Tycho tutorial

[Here you can see a more detailed tycho tutorial][tycho-tutorial]

[intelij]: http://www.jetbrains.com/idea/documentation/usingIDEAforEclipse.html "RCP with intelij"
[Kepler]: http://eclipse.org/kepler/ "Eclipse Kepler"
[tycho]: http://eclipse.org/tycho/ "Tycho Project"
[maven3]: http://maven.apache.org/ref/3.0/ "Maven"
[manifest-first-vs-pom-first]: http://stackoverflow.com/questions/11373009/should-i-use-pom-first-or-manifest-first-when-developing-osgi-application-with-m "Pom first vs Manifest First"
[maven-bundle]: http://felix.apache.org/site/apache-felix-maven-bundle-plugin-bnd.html "Maven Bundle Plugin"
[nexus-p2]: http://mrexception.blogspot.ro/2012/04/p2-support-for-nexus-oss.html "Nexus p2 support"
[tycho-tutorial]: http://codeandme.blogspot.ro/2012/12/tycho-build-1-building-plug-ins.html "Tycho tutorial"
