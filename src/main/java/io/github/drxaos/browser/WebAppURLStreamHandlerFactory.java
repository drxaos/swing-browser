package io.github.drxaos.browser;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class WebAppURLStreamHandlerFactory implements URLStreamHandlerFactory {
    Class target;
    private DynamicResourceHandler dynamicResourceResolver;

    public WebAppURLStreamHandlerFactory(Class target, DynamicResourceHandler dynamicResourceResolver) {
        this.target = target;
        this.dynamicResourceResolver = dynamicResourceResolver;
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("webapp")) {
            return new WebAppURLHandler(target, dynamicResourceResolver);
        }
        return null;
    }

}