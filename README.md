# Gitgamesh
Web portal for sharing 3D printing models (STL files) focused in the open source community.

![Gitgamesh logo](https://github.com/a-perez/Gitgamesh/blob/master/gitgamesh-gui/src/logo/gitgamesh-logo.png)

# Introduction

Most of the existing webpages for the sharing of 3D models aren't real open source. We also want to allow users to keep track of their files and the changes that they make, providing the benefits of Git, but without needing technical knowledge.

This project has an idea developed in the Pi Week (8th edition 13-17 July, 2015 http://piweek.com/). The scope of this event is create an appliction in one week using open source tools. The initial application has been developed by BiiT Sourcing Solutions' development team (http://biit-solutions.com/)

# Objectives
The objectives of this project are: 
* Upload of binary and ascii STL files.
* Binaryfiles are converted to ascii before being pushed to Git
* Creation of new projects.
* Fork of other user projects.
* Upload and version control of new components.
* 3D rendering of STL files.

# Preview
Here are some screenshots of the two main pages of the project at version 0.1.0. 

This is the view of a gallery populated with some projects (obtained from Github):
![Gallery Preview](https://github.com/a-perez/Gitgamesh/blob/master/gitgamesh-release/screenshots/Gallery.png)

This is the view of a specific project with the render in action:
![Project Preview](https://github.com/a-perez/Gitgamesh/blob/master/gitgamesh-release/screenshots/Project.png)

It is not so bad for only 5 days of development!

# Technologies

The tools is developed using Java (JDK7), Vaadin, Git as files repository and other tools or frameworks. 

# Installation

The result of the project is a WAR file that can be installed in any application server (as Tomcat or Jetty). Copy the WAR file inside the server must be enought to deploy it. Check the file 'database.properties' and 'settings.conf' for tunning up the database and application configuration. 

# Compiling
If you wish to compile the project, it includes all dependencies in the maven file. Running a "mvn clean install" must download all dependencies and compile the entire project. More information about Maven can be found here: https://maven.apache.org/

# License

This project is released under the GNU GPL v3 License. 

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
Any included software has its own license. Please consult the third-party software section for more information about individual licenses.

# Third-party software
Icons obtained from Modern Icons: http://modernuiicons.com/

Vaadin user interface components for web apps: https://vaadin.com/

Vaadin Carousel Add-on (https://vaadin.com/directory#!addon/carousel)

Vaadin Plupload Add-on (https://vaadin.com/directory#!addon/plupload-wrapper-for-vaadin-7x)

ThreJS Rendering tool (https://github.com/mrdoob/three.js/)

Java SSH cliente JSch (http://www.jcraft.com/jsch/)

Hibernate (http://hibernate.org/)

Spring (https://spring.io/)



GiT server (https://git-scm.com/)


