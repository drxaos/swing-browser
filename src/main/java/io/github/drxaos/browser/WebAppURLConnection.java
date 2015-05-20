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

    @Override
    public void connect() throws IOException {
        if (connected) {
            return;
        }
        loadData();
        connected = true;
    }

    public InputStream getInputStream() throws IOException {
        connect();
        return new ByteArrayInputStream(data);
    }

    private void loadData() throws IOException {
        if (data != null) {
            return;
        }
        URL url = getURL();
        String filePath = url.toExternalForm();
        filePath = filePath.startsWith("webapp://") ? filePath.substring("webapp://".length()) : filePath.substring("webapp:".length()); // attention: triple '/' is reduced to a single '/'
        try {
            URL resource = target.getResource(filePath);
            if (resource != null) {
                data = IOUtils.toByteArray(resource.openConnection());
            } else {
                data = new byte[0];
            }
        } catch (IOException e) {
            data = new byte[0];
        }
    }

    public OutputStream getOutputStream() throws IOException {
        return new ByteArrayOutputStream();
    }

    public java.security.Permission getPermission() throws IOException {
        return null;
    }

}