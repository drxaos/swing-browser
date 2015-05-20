package io.github.drxaos.browser;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class WebAppURLStreamHandlerFactory implements URLStreamHandlerFactory {
    Class target;

    public WebAppURLStreamHandlerFactory(Class target) {
        this.target = target;
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("webapp")) {
            return new WebAppURLHandler(target);
        }
        return null;
    }

}