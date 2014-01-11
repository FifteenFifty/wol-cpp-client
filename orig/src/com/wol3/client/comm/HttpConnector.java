// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   HttpConnector.java

package com.wol3.client.comm;

import com.wol3.client.Settings;
import com.wol3.util.ByteBufferUtils;
import java.io.IOException;
import java.net.ProxySelector;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

// Referenced classes of package com.wol3.client.comm:
//            ByteBufferProgressBody

public class HttpConnector
{

    public HttpConnector()
    {
        httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter("http.socket.buffer-size", 8192);
        httpClient.setRoutePlanner(new ProxySelectorRoutePlanner(httpClient.getConnectionManager().getSchemeRegistry(), java.net.ProxySelector.getDefault()));
    }

    public org.apache.http.HttpResponse login(java.lang.String username, java.lang.String password)
        throws java.io.IOException
    {
        org.apache.http.client.methods.HttpPost post = new HttpPost((new StringBuilder(java.lang.String.valueOf(serverBaseURL))).append("client/login/?version=").append(5421).toString());
        java.util.List params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", com.wol3.client.comm.HttpConnector.hashPassword(password)));
        post.setEntity(new UrlEncodedFormEntity(params));
        return httpClient.execute(post);
    }

    public org.apache.http.HttpResponse createLiveReport(java.lang.String username, java.lang.String password)
        throws java.io.IOException
    {
        org.apache.http.client.methods.HttpPost post = new HttpPost((new StringBuilder(java.lang.String.valueOf(serverBaseURL))).append("client/reports/live/create/?version=").append(5421).append("&format=xz").toString());
        java.util.List params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", com.wol3.client.comm.HttpConnector.hashPassword(password)));
        post.setEntity(new UrlEncodedFormEntity(params));
        return httpClient.execute(post);
    }

    public org.apache.http.HttpResponse updateLiveReport(java.lang.String username, java.lang.String password, java.lang.String id, byte data[])
        throws java.io.IOException
    {
        org.apache.http.client.methods.HttpPost postMethod = new HttpPost((new StringBuilder(java.lang.String.valueOf(serverBaseURL))).append("client/reports/live/update/?version=").append(5421).toString());
        org.apache.http.entity.mime.MultipartEntity mre = new MultipartEntity();
        mre.addPart("username", new StringBody(username));
        mre.addPart("password", new StringBody(com.wol3.client.comm.HttpConnector.hashPassword(password)));
        mre.addPart("id", new StringBody(id));
        mre.addPart("report", new ByteArrayBody(data, "report.w4z"));
        postMethod.setEntity(mre);
        return httpClient.execute(postMethod);
    }

    public org.apache.http.HttpResponse getOffsets(java.lang.String username, java.lang.String password, java.lang.String id)
        throws java.io.IOException
    {
        org.apache.http.client.methods.HttpPost post = new HttpPost((new StringBuilder(java.lang.String.valueOf(serverBaseURL))).append("client/reports/live/offsets/?version=").append(5421).toString());
        java.util.List params = new ArrayList();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", com.wol3.client.comm.HttpConnector.hashPassword(password)));
        params.add(new BasicNameValuePair("id", id));
        post.setEntity(new UrlEncodedFormEntity(params));
        return httpClient.execute(post);
    }

    public org.apache.http.HttpResponse createReport(java.lang.String username, java.lang.String password, java.nio.ByteBuffer data, java.lang.String comment, com.wol3.client.comm.ByteBufferProgressBody file)
        throws java.io.IOException
    {
        org.apache.http.client.methods.HttpPost postMethod = new HttpPost((new StringBuilder(java.lang.String.valueOf(serverBaseURL))).append("client/reports/create/?version=").append(5421).append("&format=xz").toString());
        org.apache.http.entity.mime.MultipartEntity mre = new MultipartEntity();
        mre.addPart("username", new StringBody(username));
        mre.addPart("password", new StringBody(com.wol3.client.comm.HttpConnector.hashPassword(password)));
        mre.addPart("hash", new StringBody((new StringBuilder()).append(com.wol3.util.ByteBufferUtils.getCRC32(data)).toString()));
        mre.addPart("size", new StringBody((new StringBuilder()).append(data.limit()).toString()));
        mre.addPart("comment", new StringBody(comment));
        mre.addPart("report", file);
        postMethod.setEntity(mre);
        return httpClient.execute(postMethod);
    }

    public com.wol3.client.comm.ByteBufferProgressBody createReportUploadData(java.nio.ByteBuffer bb)
    {
        return com.wol3.util.ByteBufferUtils.getPartSource(bb, "report.dat");
    }

    public static int[] parseOffsets(java.lang.String string)
    {
        int offsets[] = new int[3];
        java.lang.String tokens[] = string.split(" ");
        for (int i = 0; i < offsets.length; i++)
            offsets[i] = java.lang.Integer.parseInt(tokens[i]);

        return offsets;
    }

    public static java.lang.String hashPassword(java.lang.String password)
    {
        return com.wol3.client.comm.HttpConnector.sha1((new StringBuilder("WoL")).append(password).append("Mk III").toString());
    }

    public static java.lang.String sha1(java.lang.String data)
    {
        java.security.MessageDigest sha1;
        try
        {
            sha1 = java.security.MessageDigest.getInstance("SHA1");
        }
        catch (java.security.NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        byte digest[] = sha1.digest(data.getBytes(utf8));
        return com.wol3.client.comm.HttpConnector.toHexString(digest);
    }

    private static java.lang.String toHexString(byte data[])
    {
        java.lang.StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = data[i] >>> 4 & 0xf;
            int two_halfs = 0;
            do
            {
                if (halfbyte >= 0 && halfbyte <= 9)
                    buf.append((char)(48 + halfbyte));
                else
                    buf.append((char)(97 + (halfbyte - 10)));
                halfbyte = data[i] & 0xf;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }

    private org.apache.http.impl.client.DefaultHttpClient httpClient;
    private static final java.lang.String serverBaseURL;
    public static final java.nio.charset.Charset utf8 = java.nio.charset.Charset.forName("utf8");

    static 
    {
        serverBaseURL = com.wol3.client.Settings.instance.getServerURL();
    }
}
