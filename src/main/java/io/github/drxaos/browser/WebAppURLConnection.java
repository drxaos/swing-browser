package io.github.drxaos.browser;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Register a protocol handler for URLs like this: <code>webapp:///pics/sland.gif</code><br>
 */
public class WebAppURLConnection extends URLConnection {

    Class target;
    private byte[] data;

    protected WebAppURLConnection(URL url, Class target) {
        super(url);
        this.target = target;
    }

    public InputStream getInputStream() throws IOException {
        if (data != null) {
            return new ByteArrayInputStream(data);
        }
        URL url = getURL();
        String filePath = url.toExternalForm();
        filePath = filePath.startsWith("webapp://") ? filePath.substring("webapp://".length()) : filePath.substring("webapp:".length()); // attention: triple '/' is reduced to a single '/'
        URL resource = target.getResource(filePath);
        if (resource != null) {
            data = IOUtils.toByteArray(resource.openConnection());
        } else {
            throw new FileNotFoundException(url.toString());
        }
        return new ByteArrayInputStream(data);
    }

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