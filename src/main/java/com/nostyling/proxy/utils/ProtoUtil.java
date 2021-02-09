package com.nostyling.proxy.utils;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author shiliang
 * @Classname ProtoUtil
 * @Date 2021/1/7 15:44
 * @Description TODO
 */
public class ProtoUtil {
    private static Pattern pattern1 = Pattern.compile("^(?:https?://)?(?<host>[^/]*)/?.*$");
    private static Pattern pattern2 = Pattern.compile("^(?:https?://)?(?<host>[^:]*)(?::(?<port>\\d+))?(/.*)?$");

    public static RequestProto getRequestProto(HttpRequest httpRequest) {
        RequestProto requestProto = new RequestProto();
        int port = -1;
        String hostStr = httpRequest.headers().get(HttpHeaderNames.HOST);
        if (hostStr == null) {
            Matcher matcher = pattern1.matcher(httpRequest.uri());
            if (matcher.find()) {
                hostStr = matcher.group("host");
            } else {
                return null;
            }
        }
        String uriStr = httpRequest.uri();
        Matcher matcher = pattern2.matcher(hostStr);
        //先从host上取端口号没取到再从uri上取端口号 issues#4
        String portTemp = null;
        if (matcher.find()) {
            requestProto.setHost(matcher.group("host"));
            portTemp = matcher.group("port");
            if (portTemp == null) {
                matcher = pattern2.matcher(uriStr);
                if (matcher.find()) {
                    portTemp = matcher.group("port");
                }
            }
        }
        if (portTemp != null) {
            port = Integer.parseInt(portTemp);
        }
        boolean isSsl = uriStr.indexOf("https") == 0 || hostStr.indexOf("https") == 0;
        if (port == -1) {
            if (isSsl) {
                port = 443;
            } else {
                port = 80;
            }
        }
        requestProto.setPort(port);
        requestProto.setSsl(isSsl);
        return requestProto;
    }

    public static class RequestProto implements Serializable {

        private static final long serialVersionUID = -6471051659605127698L;
        private String host;
        private int port;
        private boolean ssl;

        public RequestProto() {
        }

        public RequestProto(String host, int port, boolean ssl) {
            this.host = host;
            this.port = port;
            this.ssl = ssl;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean getSsl() {
            return ssl;
        }

        public void setSsl(boolean ssl) {
            this.ssl = ssl;
        }
    }
}
