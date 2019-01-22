package com.sust.fdfs.pool;


import com.sust.fdfs.exception.FastDFSException;
import com.sust.fdfs.fastdfs.ClientGlobal;
import com.sust.fdfs.fastdfs.TrackerGroup;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description:  init fastDfs
 * <p>Version:v1.0
 * <p>Copyright ©2015sust All Rights Reserved. </p>
 * <p>Company:sust</p>
 * <p>Author:sust
 * <p>Date: 2017/1/9  16:24
 */
public class FastDFSTemplateFactory {

    private int g_connect_timeout=2;
    private int g_network_timeout=30;
    private String g_charset="utf-8";
    private int g_tracker_http_port=7109;
    private boolean g_anti_steal_token=true;
    private String g_secret_key="1234567890";
    private List<String> tracker_servers ;;
    private TrackerGroup g_tracker_group;
    private PoolConfig poolConfig = new PoolConfig();
    public void init() throws Exception {

        if (g_connect_timeout <= 0) {
            g_connect_timeout = ClientGlobal.DEFAULT_CONNECT_TIMEOUT;
        }

        if (g_network_timeout <= 0) {
            g_network_timeout = ClientGlobal.DEFAULT_NETWORK_TIMEOUT;
        }
        g_connect_timeout *= 1000; //millisecond
        g_network_timeout *= 1000; //millisecond

        if (g_charset == null || g_charset.length() == 0) {
            g_charset = "UTF-8";
        }

        if (g_tracker_http_port <=0 ){
            g_tracker_http_port = 80;
        }

        tracker_servers=new ArrayList<String>();
        tracker_servers.add("10.10.8.10:22122");
        if (tracker_servers == null || tracker_servers.isEmpty()) {
            throw new FastDFSException("item \"tracker_server\"  not found", -1);
        }

        InetSocketAddress[] tracker_servers_socket = new InetSocketAddress[tracker_servers.size()];
        for (int i = 0; i < tracker_servers.size(); i++) {
            String str = tracker_servers.get(i);
            String[] parts = str.split("\\:", 2);
            if (parts.length != 2) {
                throw new FastDFSException("the value of item \"tracker_server\" is invalid, the correct format is host:port", -2);
            }

            tracker_servers_socket[i] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        g_tracker_group = new TrackerGroup(tracker_servers_socket);

        if (g_anti_steal_token) {
            if (g_secret_key == null || "".equals(g_secret_key)) {
                throw new FastDFSException("item \"secret_key\"  not found", -2);
            }
        }
        setToGlobal();
    }

    private void setToGlobal() {
        ClientGlobal.setG_connect_timeout(this.g_connect_timeout);
        ClientGlobal.setG_network_timeout(this.g_network_timeout);
        ClientGlobal.setG_charset(this.g_charset);
        ClientGlobal.setG_tracker_http_port(this.g_tracker_http_port);
        ClientGlobal.setG_anti_steal_token(this.g_anti_steal_token);
        ClientGlobal.setG_secret_key(this.g_secret_key);
        ClientGlobal.setG_tracker_group(this.g_tracker_group);
    }

    public PoolConfig getPoolConfig() {
        if (poolConfig == null){
            return new PoolConfig();
        }
        return poolConfig;
    }

    public void setPoolConfig(PoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public void setG_connect_timeout(int g_connect_timeout) {
        this.g_connect_timeout = g_connect_timeout;
    }

    public void setG_network_timeout(int g_network_timeout) {
        this.g_network_timeout = g_network_timeout;
    }

    public void setG_charset(String g_charset) {
        this.g_charset = g_charset;
    }

    public void setG_tracker_http_port(int g_tracker_http_port) {
        this.g_tracker_http_port = g_tracker_http_port;
    }

    public void setG_anti_steal_token(boolean g_anti_steal_token) {
        this.g_anti_steal_token = g_anti_steal_token;
    }

    public void setG_secret_key(String g_secret_key) {
        this.g_secret_key = g_secret_key;
    }

    public void setTracker_servers(List<String> tracker_servers) {
        this.tracker_servers = tracker_servers;
    }

    public void setG_tracker_group(TrackerGroup g_tracker_group) {
        this.g_tracker_group = g_tracker_group;
    }

    public TrackerGroup getG_tracker_group() {
        return g_tracker_group;
    }
}
