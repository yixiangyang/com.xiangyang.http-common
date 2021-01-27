package com.xiangyang;

import com.xiangyang.http.client.model.HttpResponseResult;
import com.xiangyang.http.client.util.HttpToolsClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class imageTest {
    public static void main(String[] args) throws IOException {
        HttpResponseResult<InputStream> str= HttpToolsClient.getObjectByGetUrl("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20180830%2F22%2F1535637647-sSIBadeLyg.jpeg&refer=http%3A%2F%2Fimage.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614327228&t=b201b1149139c1ce4ba491d9a8cd446a",InputStream.class);
        System.out.println(str);
        FileUtils.copyToFile(str.getResponseVo(), new File("D://logo1.jpeg"));
    }
}
