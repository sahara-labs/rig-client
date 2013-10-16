2013-09-27 Michael Diponio <michael.diponio@uts.edu.au>

*********************************************************** 
**            SAHARA Labs r3.3-ANDS_Research             ** 
***********************************************************

Introduction
============
Sahara Labs is a software suite that helps to enable remote 
access to computer controlled laboratories. It is designed 
to be a scalable, stable platform that enables the use and 
sharing of a variety of types of remote laboratories and 
maximises remote lab usage by implementing queuing and 
booking (or reservations) for users over a group of 
identical laboratories. 

New Functionality
=================
The latest release of Sahara Labs provides the capability to 
capture and track experimental data logged from a research 
apparatus deployed as a Sahara Labs rig and metadata about 
the conditions of its capture such as which apparatus 
captured the data, at what time, as well as user entered 
metadata such as project details and classification codes.

Updates
=======
    => New icon theme for the Web Interface
    => Various bug fixes
    
Known Problems
==============
    => The Scheduling Server may fail to find the location of
       the Java runtime library on Windows using JRE version 7.
       A work around is to manually configure the location of 
       the Java runtime library in 'scheduling_service.properties'.
       A fixed version will be provided shortly.

Installation Packages
=====================
Installation packages are provided for Redhat Enterprise Linux 5 or 6 
and Windows with 32bit and 64 versions of each. They can be obtained
from:

    https://sourceforge.net/projects/labshare-sahara/files/Installers/

If you are using a Debian based Linux distribution, the RPMs can be
converted to Debian packages (.deb) using 'alien -d --scripts <name>.rpm'.
    
Documentation
=============
The documentation for Sahara is provided in the following documents:

    => Sahara Installation Guide - How to install Sahara Labs components
       including the Scheduling Server, Web Intercace and Rig Client to
       get Sahara up and running.
       
    => Sahara User Guide - How to use the common Sahara Labs functionality
       such as queuing or reserving a rig and creating research projects.
       
    => Sahara Admin Guide - How to administer Sahara such as adding
       permissions or taking rigs offline.
       
    => Sahara Development Handbook - How to implement new rigs and 
       customise their interface.

Documentation about Sahara Labs can be obtained from:

    https://sourceforge.net/projects/labshare-sahara/files/Documentation
    
NOTE: Some documents are slightly delayed but will be uploaded shortly.

Source Code
===========
Git is used as the version control system with the following repositories
for each of the Sahara Labs components:

    => Scheduling Server: https://github.com/mdiponio/sahara-labs-scheduling-server
    => Web Interface: https://github.com/mdiponio/sahara-labs-web-interface
    => Rig Client: https://github.com/mdiponio/sahara-labs-rig-client
    => Rig Client Commons: https://github.com/mdiponio/sahara-labs-rig-client-commons

Each of these repositories has the latest development code of Sahara Labs 
and tags of releases including r3.3-ANDS_Research.

Archives of the source code of each component can also be obtained from:
    
    https://sourceforge.net/projects/labshare-sahara/files/Source/
 
Acknowledgement
===============
Sahara Labs r3.3-ANDS_Research development was funded by the 
Australian National Data Service (http://www.ands.org.au/). 
