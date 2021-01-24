package com.xiangyang.httpclient.utils;

import com.xiangyang.httpclient.CollectionUtils;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URLEncodedUtils;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.*;

public class URL implements Serializable {
    private static final long serialVersionUID = -6705730762684673366L;

    private final String protocol;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    private Charset paramCharset;

    public String getProtocol() {
        return this.protocol;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getPath() {
        return this.path;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public Charset getParamCharset() {
        return this.paramCharset;
    }

    protected URL() {
        this.protocol = null;
        this.username = null;
        this.password = null;
        this.host = null;
        this.port = 0;
        this.path = null;
        this.parameters = null;
    }

    public URL(String protocol, String host, int port) {
        this(protocol, (String)null, (String)null, host, port, (String)null, (Map<String, String>)null);
    }

    public URL(String protocol, String host, int port, String[] pairs) {
        this(protocol, (String)null, (String)null, host, port, (String)null, CollectionUtils.toStringMap(pairs));
    }

    public URL(String protocol, String host, int port, Map<String, String> parameters) {
        this(protocol, (String)null, (String)null, host, port, (String)null, parameters);
    }

    public URL(String protocol, String host, int port, String path) {
        this(protocol, (String)null, (String)null, host, port, path, (Map<String, String>)null);
    }

    public URL(String protocol, String host, int port, String path, String... pairs) {
        this(protocol, (String)null, (String)null, host, port, path, CollectionUtils.toStringMap(pairs));
    }

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this(protocol, (String)null, (String)null, host, port, path, parameters);
    }

    public URL(String protocol, String username, String password, String host, int port, String path) {
        this(protocol, username, password, host, port, path, (Map<String, String>)null);
    }

    public URL(String protocol, String username, String password, String host, int port, String path, String... pairs) {
        this(protocol, username, password, host, port, path, CollectionUtils.toStringMap(pairs));
    }

    public URL(String protocol, String username, String password, String host, int port, String path, Map<String, String> parameters) {
        if ((username == null || username.length() == 0) && password != null && password
                .length() > 0)
            throw new IllegalArgumentException("Invalid url, password without username!");
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = (port < 0) ? 80 : port;
        while (path != null && path.startsWith("/"))
            path = path.substring(1);
        this.path = path;
        if (parameters == null) {
            this.parameters = new HashMap<>();
        } else {
            this.parameters = new HashMap<>(parameters);
        }
    }

    public static URL valueOf(String url) {
        if (url == null || (url = url.trim()).length() == 0)
            throw new IllegalArgumentException("url == null");
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = 0;
        String path = null;
        Map<String, String> parameters = null;
        int i = url.indexOf("?");
        if (i >= 0) {
            String[] parts = url.substring(i + 1).split("\\&");
            parameters = new HashMap<>();
            for (String part : parts) {
                part = part.trim();
                if (part.length() > 0) {
                    int j = part.indexOf('=');
                    if (j >= 0) {
                        parameters.put(part.substring(0, j), part.substring(j + 1));
                    } else {
                        parameters.put(part, part);
                    }
                }
            }
            url = url.substring(0, i);
        }
        i = url.indexOf("://");
        if (i >= 0) {
            if (i == 0)
                throw new IllegalStateException("url missing protocol: \"" + url + "\"");
            protocol = url.substring(0, i);
            url = url.substring(i + 3);
        } else {
            i = url.indexOf(":/");
            if (i >= 0) {
                if (i == 0)
                    throw new IllegalStateException("url missing protocol: \"" + url + "\"");
                protocol = url.substring(0, i);
                url = url.substring(i + 1);
            }
        }
        i = url.indexOf("/");
        if (i >= 0) {
            path = url.substring(i + 1);
            url = url.substring(0, i);
        }
        i = url.indexOf("@");
        if (i >= 0) {
            username = url.substring(0, i);
            int j = username.indexOf(":");
            if (j >= 0) {
                password = username.substring(j + 1);
                username = username.substring(0, j);
            }
            url = url.substring(i + 1);
        }
        i = url.indexOf(":");
        if (i >= 0 && i < url.length() - 1) {
            port = Integer.parseInt(url.substring(i + 1));
            url = url.substring(0, i);
        }
        if (url.length() > 0)
            host = url;
        return new URL(protocol, username, password, host, port, path, parameters);
    }

    public boolean hasParameter(String key) {
        String value = getParameter(key);
        return (value != null && value.length() > 0);
    }

    public String getParameter(String key) {
        return this.parameters.get(key);
    }

    public URL addParameter(String key, boolean value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, char value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, byte value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, short value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, int value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, long value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, float value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, double value) {
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, Enum<?> value) {
        if (value == null)
            return this;
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, Number value) {
        if (value == null)
            return this;
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, CharSequence value) {
        if (value == null || value.length() == 0)
            return this;
        return addParameter(key, String.valueOf(value));
    }

    public URL addParameter(String key, String value) {
        if (key == null || key.length() == 0 || value == null || value
                .length() == 0)
            return this;
        if (value.equals(getParameters().get(key)))
            return this;
        getParameters().put(key, value);
        return this;
    }

    public static String encode(String value) {
        if (value == null || value.length() == 0)
            return "";
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public URL addParameterAndEncoded(String key, String value) {
        if (value == null || value.length() == 0)
            return this;
        return addParameter(key, encode(value));
    }

    public URL addParameterIfAbsent(String key, String value) {
        if (key == null || key.length() == 0 || value == null || value
                .length() == 0)
            return this;
        if (hasParameter(key))
            return this;
        getParameters().put(key, value);
        return this;
    }

    public URL removeParameter(String key) {
        if (key == null || key.length() == 0)
            return this;
        return removeParameters(new String[] { key });
    }

    public URL removeParameters(Collection<String> keys) {
        if (keys == null || keys.size() == 0)
            return this;
        return removeParameters(keys.<String>toArray(new String[0]));
    }

    public URL removeParameters(String... keys) {
        if (keys == null || keys.length == 0)
            return this;
        Map<String, String> map = getParameters();
        for (String key : keys)
            map.remove(key);
        return this;
    }

    public URL clearParameters() {
        getParameters().clear();
        return this;
    }

    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }

    public String toString() {
        return buildString(false, true, false, new String[0]);
    }

    public String toFullString() {
        return buildString(true, true, false, new String[0]);
    }

    private String buildString(boolean appendUser, boolean appendParameter, boolean useIP, String... parameters) {
        String host;
        StringBuilder buf = new StringBuilder();
        if (this.protocol != null && this.protocol.length() > 0) {
            buf.append(this.protocol);
            buf.append("://");
        }
        if (appendUser && this.username != null && this.username.length() > 0) {
            buf.append(this.username);
            if (this.password != null && this.password.length() > 0) {
                buf.append(":");
                buf.append(this.password);
            }
            buf.append("@");
        }
        if (useIP) {
            host = getIpByHost(getHost());
        } else {
            host = getHost();
        }
        if (host != null && host.length() > 0) {
            buf.append(host);
            if (this.port > 0) {
                buf.append(":");
                buf.append(this.port);
            }
        }
        String path = getPath();
        if (path != null && path.length() > 0) {
            buf.append("/");
            buf.append(path);
        }
        if (appendParameter)
            buildParameters(buf, true, parameters);
        return buf.toString();
    }

    public URL setParamCharset(Charset paramCharset) {
        this.paramCharset = paramCharset;
        return this;
    }

    private void buildParameters(StringBuilder buf, boolean concat, String[] parameters) {
        if (getParameters() != null && getParameters().size() > 0)
            if (this.paramCharset == null) {
                List<String> includes = (parameters == null || parameters.length == 0) ? null : Arrays.<String>asList(parameters);
                boolean first = true;
                for (Map.Entry<String, String> entry : (new TreeMap<>(getParameters())).entrySet()) {
                    if (entry.getKey() != null && ((String)entry.getKey()).length() > 0 && (includes == null || includes
                            .contains(entry.getKey()))) {
                        if (first) {
                            if (concat)
                                buf.append("?");
                            first = false;
                        } else {
                            buf.append("&");
                        }
                        buf.append(entry.getKey());
                        buf.append("=");
                        buf.append((entry.getValue() == null) ? "" : ((String)entry.getValue()).trim());
                    }
                }
            } else {
                List<NameValuePair> formParams = new ArrayList<>(getParameters().size());
                for (Map.Entry<String, String> entry : (new TreeMap<>(getParameters())).entrySet())
                    formParams.add(new BasicNameValuePair(entry.getKey(), (entry.getValue() == null) ? "" : ((String)entry.getValue()).trim()));
                buf.append("?").append(URLEncodedUtils.format(formParams, this.paramCharset));
            }
    }

    public java.net.URL toJavaURL() {
        try {
            return new java.net.URL(toString());
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
