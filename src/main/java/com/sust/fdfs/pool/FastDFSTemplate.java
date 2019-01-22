package com.sust.fdfs.pool;


import com.sust.fdfs.common.NameValuePair;
import com.sust.fdfs.exception.FastDFSException;
import com.sust.fdfs.fastdfs.StorageClient;

import java.util.Map;

/**
 * <p>Description:  com.sustcoder.fdfs template
 * <p>Version:v1.0
 * <p>Copyright ©2015sust All Rights Reserved. </p>
 * <p>Company:sust</p>
 * <p>Author:sust
 * <p>Date: 2017/1/9  16:25
 */
public class FastDFSTemplate {

    private ConnectionPoolFactory connPoolFactory;

    public FastDFSTemplate(FastDFSTemplateFactory factory) {
        this.connPoolFactory = new ConnectionPoolFactory(factory);
    }


    public FastDfsRv upload(byte[] data, String ext) throws FastDFSException {
        return this.upload(data, ext, null);
    }

    public FastDfsRv upload(byte[] data, String ext, Map<String, String> values) throws FastDFSException {
        NameValuePair[] valuePairs = null;
        if (values != null && !values.isEmpty()){
            valuePairs = new NameValuePair[values.size()];
            int index = 0;
            for(Map.Entry<String, String> entry : values.entrySet()){
                valuePairs[index] = new NameValuePair(entry.getKey(), entry.getValue());
                index ++;
            }
        }
        StorageClient client = getClient();
        try {
            String[] uploadResults = client.upload_file(data, ext, valuePairs);
            String groupName = uploadResults[0];
            String remoteFileName = uploadResults[1];
            return new FastDfsRv(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
    }


    public byte[] loadFile(FastDfsRv dfs) throws FastDFSException {
        return this.loadFile(dfs.getGroupName(), dfs.getAccessName());
    }

    public byte[] loadFile(String groupName, String remoteFileName) throws FastDFSException {
        StorageClient client = getClient();
        try {
            return client.download_file(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
    }

    public void deleteFile(FastDfsRv dfs) throws FastDFSException {
        this.deleteFile(dfs.getGroupName(), dfs.getAccessName());
    }

    public void deleteFile(String groupName, String remoteFileName) throws FastDFSException {
        int code;
        StorageClient client = getClient();
        try {
            code = client.delete_file(groupName, remoteFileName);
        } catch (Exception e) {
            throw new FastDFSException(e.getMessage(), e, 0);
        } finally {
            releaseClient(client);
        }
        if (code != 0){
            throw new FastDFSException(code);
        }
    }

    protected StorageClient getClient() {
        StorageClient client = null;
        while (client == null){
            try {
                client = connPoolFactory.getClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    protected void releaseClient(StorageClient client) {
        connPoolFactory.releaseConnection(client);
    }

}
