package com.sust.fdfs.pool;


import com.sust.fdfs.fastdfs.StorageClient;
import com.sust.fdfs.fastdfs.TrackerClient;
import com.sust.fdfs.fastdfs.TrackerServer;
import org.apache.commons.pool.BasePoolableObjectFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * <p>Description:  create pool
 * <p>Version:v1.0
 * <p>Copyright ©2015sust All Rights Reserved. </p>
 * <p>Company:sust</p>
 * <p>Author:sust
 * <p>Date: 2017/1/9  16:28
 */
class ConnectionFactory extends BasePoolableObjectFactory<StorageClient> {

    private FastDFSTemplateFactory factory;
    public ConnectionFactory(FastDFSTemplateFactory templateFactory) {
        this.factory = templateFactory;
    }

    public StorageClient makeObject() throws Exception {
        TrackerClient trackerClient = new TrackerClient(factory.getG_tracker_group());
        TrackerServer trackerServer = trackerClient.getConnection();
        return new StorageClient(trackerServer, null);
    }

    public void destroyObject(StorageClient obj) throws Exception {
        close(obj.getTrackerServer());
    }

    public boolean validateObject(StorageClient obj) {
        try {
            Socket socket = obj.getTrackerServer().getSocket();
            if (!socket.isConnected()) {
                return false;
            }
            if (socket.isClosed()) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}