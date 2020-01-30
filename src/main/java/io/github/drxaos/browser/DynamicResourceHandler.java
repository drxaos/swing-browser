package io.github.drxaos.browser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicResourceHandler {

    Map<String, Function<URL, DynamicResourceURLConnection>> resources = new HashMap<>();

    public DynamicResourceURLConnection get(URL url) {
        String filePath = url.toExternalForm();
        filePath = filePath.startsWith("webapp://") ? filePath.substring("webapp://".length()) : filePath.substring("webapp:".length()); // attention: triple '/' is reduced to a single '/'
        var function = resources.get(filePath);
        return function != null ? function.apply(url) : null;
    }

    public void addResource(String path, Function<URL, InputStream> resource) {
        resources.put(path, url -> new DynamicResourceURLConnection(url) {
            @Override
            public InputStream getInputStream() throws IOException {
                return resource.apply(url);
            }
        });
    }

}
