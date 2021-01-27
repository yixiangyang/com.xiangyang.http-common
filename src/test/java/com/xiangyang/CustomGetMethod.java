package com.xiangyang;

import org.apache.hc.client5.http.classic.methods.HttpGet;

import java.io.IOException;
import java.net.URI;
import java.util.zip.GZIPInputStream;

public class CustomGetMethod extends HttpGet {
    public CustomGetMethod(URI uri) {
        super(uri);
    }

}
