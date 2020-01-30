package io.github.drxaos.browser;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class WebAppURLHandler extends URLStreamHandler {
    Class target;
    DynamicResourceHandler dynamicResourceHandler;

    public WebAppURLHandler(Class target, DynamicResourceHandler dynamicResourceHandler) {
        this.target = target;
        this.dynamicResourceHandler = dynamicResourceHandler;
    }

    @Override
    protected URLConnection openConnection(URL url) {
        DynamicResourceURLConnection r = dynamicResourceHandler.get(url);
        if (r != null) {
            return r;
        } else {
            return new WebAppURLConnection(url, target);
        }
    }

}