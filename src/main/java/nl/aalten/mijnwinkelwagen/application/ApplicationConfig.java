package nl.aalten.mijnwinkelwagen.application;

import nl.aalten.mijnwinkelwagen.produkten.ProduktenResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/mijnwinkelwagen")
public class ApplicationConfig extends Application {

    public ApplicationConfig() {
    }

    public Set<Class<?>> getClasses() {
        return new HashSet(Arrays.asList(new Class[]{ProduktenResource.class}));
    }

}
