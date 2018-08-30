Summary
-------
Implements simple idiomatic example of contacts application as Java Springboot WebFlux application

#Implemntation and URL's
Application hosts two different web implementations: 
	- traditional Spring annotated (like MVC) Controller accessible on URL: /contacts
	- fully functional reactive WebFlux )-annotation routers accessible on URL: 
		/functional/contacts
Both implementations provide similar functionality, and using WebFlux with Netty

#Assumptions

Profile images are reasonably small files up to 50k, to be directly stored/retrieved  into/from relational database, and passed in json with base64 encoding without major overhead. If this assumption proves to be wrong, then application to be modified: a. to allow multipart image uploads; b. have separate image storage (e.g. AWS s3), with locations, referenced in RDB.

#Persistance and configuration
Relational database storage initially configured as in memory DB hsql to allow local startup.

This could (needs to) be changed at deployment time to any cloud based mysql variant (e.g. Aurora) with environmental, java, or external file properties; @See Springboot documentation. MariaDB driver to be provided on classpath fo this purpose. 

#Build
Implemented with gradle

## To build
Posix:		./gradlew build
Windows: 	gradlew build

## To run
Posix:		./gradlew run
Windows: 	gradlew run

# To test
Posix:		./gradlew test
Windows: 	gradlew test

# Shortcomings

The application is of the demo and not industrial grade. 

-Service input is not validated properly
-Naked domain model. The same objects are used for persistence and representation
-Primitive to none error handling
-Absent logging, built in instrumentation, security, swagger annotations, actuator endpoints, etc.
-Unused Spring profiles

