package com.behrouz.server.base.configuration;


import com.behrouz.server.base.model.ApiActionMethod;
import com.behrouz.server.api.BaseApi;
import com.behrouz.server.base.annotation.ApiAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

;

/**
 * created by: Hapi
 **/


@Configuration
public class MethodLoaderConfiguration {


    /**
     * method fetch all method
     * with api action annotation
     * @return the hash map :
     * key : action
     * getValue : the method from java reflection
     * @throws URISyntaxException exception handle for reading package directory
     * @throws IOException exception handel when read the class methods
     * @throws ClassNotFoundException not found this class
     */
    @Bean
    public HashMap<String , List<ApiActionMethod> > getApiActionMethods() throws URISyntaxException, IOException, ClassNotFoundException {

        Iterable<Class> classes =
                getClasses(BaseApi.class.getPackage().getName());

        return
                fetchMethodByAnnotation(
                        classes ,
                        ApiAction.class
                );
    }





    /**
     * fetch  all method from class by special annotation
     * @param classes the class
     * @param annotation the special annotation
     * @return the Hash map of method :
     * key : action of apiAction annotation
     * getValue : the method by annotation apiAction
     */
    private HashMap < String , List<ApiActionMethod> > fetchMethodByAnnotation(Iterable < Class > classes, Class < ApiAction > annotation) {

        final HashMap<String , List<ApiActionMethod>> methods = new HashMap<>();

        for (Class aClass : classes) {
            final List<Method> allMethods = new ArrayList <>( Arrays.asList( aClass.getDeclaredMethods() ) );
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {

                    String apiAction = method.getAnnotation(annotation).value();

                    List<ApiActionMethod> newMethods =
                            methods.getOrDefault( apiAction ,  new ArrayList <>(  ));

                    newMethods.add( new ApiActionMethod( method ) );

                    methods.put(apiAction , newMethods);
                }
            }
        }
        return methods;
    }




    /**
     * find class in special directory and package name
     * @param directory the directory name
     * @param packageName the package name
     * @return the list of class in directory and package
     * @throws ClassNotFoundException not found a class in package
     */
    private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList <>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if ( files != null ) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(findClasses(file, packageName + "." + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }

    /**
     * get class by special package name
     * @param packageName the package name
     * @return the list of class in package
     * @throws ClassNotFoundException not found this class
     * @throws IOException can't read method feature
     * @throws URISyntaxException the uri not found
     * @apiNote exception passed to get ApiActionMethods
     */
    private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL > resources = classLoader.getResources(path);
        List<File > dirs = new ArrayList <>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
//            dirs.add(new File(uri + ""));
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList <>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }


}
