# Sample JEE File Reader Application

## To Start The Application
Deploy this application in [tomcat 10](https://tomcat.apache.org/download-10.cgi) as it requires jakarta EE libraries.
```bash
mvn clean compile war:war
mv ./target/SampleWebFileLoader-1.0-SNAPSHOT.war /tomcat/webapps/SampleWebFileLoader.war
sh /tomcat/bin/startup.sh
```
You should be able to access the app [here](http://localhost:8080/SampleWebFileLoader/sample/myresource/health) and it will return `Application is healthy and running`.
This Example was developed by referring the [stackoverflow](https://stackoverflow.com/questions/2161054/where-to-place-and-how-to-read-configuration-resource-files-in-servlet-based-app/2161583#2161583)

## Walk Through
This application exposes two routes under the servlet JerseyServlet configured in web.xml
- `<tomcat-context-root>/sample/myresource/getFileContents/{fileName}` - loads file requested from WEB-INF, replace the fineName with actual file name under the WEB-INF
- `<tomcat-context-root>/sample/myresource/getAllFilesFromWebInf` - loads all files from WEB-INF