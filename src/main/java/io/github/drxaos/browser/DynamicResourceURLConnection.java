package io.github.drxaos.browser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


abstract public class DynamicResourceURLConnection extends URLConnection {

    protected DynamicResourceURLConnection(URL url) {
        super(url);
    }

    @Override
    abstract public InputStream getInputStream() throws IOException;

    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }

    @Override
    public void connect() {
    }

    public java.security.Permission getPermission() {
        return null;
    }

}
