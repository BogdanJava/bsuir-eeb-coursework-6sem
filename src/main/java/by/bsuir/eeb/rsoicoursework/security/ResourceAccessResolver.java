package by.bsuir.eeb.rsoicoursework.security;

import by.bsuir.eeb.rsoicoursework.annotation.FreeAccess;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:application.properties")
public class ResourceAccessResolver {

    @Value("${app.controller.package}")
    private String controllersPackage;

    private Set<String> freeAccessURLs = new HashSet<>();

    @PostConstruct
    private void initSet() {
        Reflections reflections = new Reflections(controllersPackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(RestController.class);
        controllers.addAll(reflections.getTypesAnnotatedWith(Controller.class));

        controllers.forEach(clazz -> {
            if (clazz.isAnnotationPresent(FreeAccess.class)) {
                Arrays
                        .stream(clazz.getMethods())
                        .map(method -> {
                            if (method.isAnnotationPresent(RequestMapping.class))
                                return method.getAnnotation(RequestMapping.class).value();
                            else return null;
                        })
                        .forEach(urls -> {
                            addUrlsToSet(clazz, urls);
                        });
            }
        });
    }

    private void addUrlsToSet(Class<?> clazz, String[] urls) {
        if (urls != null) {
            Arrays.stream(urls).forEach(url -> {
                String rootMapping = "";
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    rootMapping = clazz.getAnnotation(RequestMapping.class).value()[0];
                }
                freeAccessURLs.add(rootMapping + url);
            });
        }
    }

    public boolean isProtectedUrl(String url) {
        return freeAccessURLs.contains(url);
    }

}
