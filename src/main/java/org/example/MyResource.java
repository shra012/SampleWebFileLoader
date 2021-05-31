package org.example;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    @Inject
    ServletContext servletContext;

    @GET
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "Application is healthy and running";
    }

    @GET
    @Path("getFileContents/{fileName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String loadFileFromWebInf(@PathParam("fileName") String fileName) {
//        Example Using Class Loader
//        Note: To use class loader we should have the resource under resources folder or java folder.
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream someStream = classLoader.getResourceAsStream(fileName); Example : "somefile.txt"

//        Example Using Servlet Context
//        Note: Since this project is developed for tomcat 10 it requires Jakarta EE libs which was previously
//        included from javax.servlet.* as part of servlet-api
        InputStream input = servletContext.getResourceAsStream("/WEB-INF/" + fileName); // "/WEB-INF/web.xml"
        String result = new BufferedReader(new InputStreamReader(input))
                .lines().collect(Collectors.joining("\n"));
        return result;
    }

    @GET
    @Path("getAllFilesFromWebInf")
    @Produces(MediaType.TEXT_PLAIN)
    public String loadAllFilesFromWebInf() {
        Set<String> allPathsUnderWebInf = servletContext.getResourcePaths("/WEB-INF/");
        List<String> allFileContents = allPathsUnderWebInf.stream().filter(this::isFile).map(path -> {
            InputStream input = servletContext.getResourceAsStream(path);
            return new BufferedReader(new InputStreamReader(input))
                    .lines().collect(Collectors.joining("\n"));
        }).collect(Collectors.toList());
        return String.join("\n", allFileContents);
//        Same Code in Java 7.
//        for (String path: allPathsUnderWebInf) {
//            if(isFile(path)) {
//                InputStream input = servletContext.getResourceAsStream(path);
//                String result = new BufferedReader(new InputStreamReader(input))
//                        .lines().collect(Collectors.joining("\n"));
//                sb.append(result);
//                sb.append("\n");
//            }
//        }
//        return sb.toString();
    }

    private boolean isFile(String path) {
        return path.indexOf('.') != -1;
    }
}
