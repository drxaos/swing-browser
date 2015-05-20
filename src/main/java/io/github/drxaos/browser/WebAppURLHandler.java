package io.github.drxaos.browser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class WebAppURLHandler extends URLStreamHandler {
    Class target;

    public WebAppURLHandler(Class target) {
        this.target = target;
    }

    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        return new WebAppURLConnection(url, target);
    }

}