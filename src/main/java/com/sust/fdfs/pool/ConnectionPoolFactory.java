package com.sust.fdfs.pool;


import com.sust.fdfs.fastdfs.StorageClient;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * <p>Description:  com.sustcoder.fdfs connect pool
 * <p>Version:v1.0
 * <p>Copyright ©2015sust All Rights Reserved. </p>
 * <p>Company:sust</p>
 * <p>Author:sust
 * <p>Date: 2017/1/9  16:27
 */
public class ConnectionPoolFactory {

    private GenericObjectPool<StorageClient> pool;

    public ConnectionPoolFactory(FastDFSTemplateFactory fastDFSTemplateFactory) {
        GenericObjectPool.Config config = toConfig(fastDFSTemplateFactory.getPoolConfig());
        this.pool = new GenericObjectPool<StorageClient>(new ConnectionFactory(fastDFSTemplateFactory), config);
    }

    public StorageClient getClient() throws Exception {
        return pool.borrowObject();
    }

    public void releaseConnection(StorageClient client) {
        try {
            pool.returnObject(client);
        } catch (Exception ignored) { }
    }

    private GenericObjectPool.Config toConfig(PoolConfig poolConfig) {
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxIdle = poolConfig.maxIdle;
        config.minIdle = poolConfig.minIdle;
        config.maxActive = poolConfig.maxActive;
        config.maxWait = poolConfig.maxWait;
        config.whenExhaustedAction = poolConfig.whenExhaustedAction;
        config.testOnBorrow = poolConfig.testOnBorrow;
        config.testOnReturn = poolConfig.testOnReturn;
        config.testWhileIdle = poolConfig.testWhileIdle;
        config.timeBetweenEvictionRunsMillis = poolConfig.timeBetweenEvictionRunsMillis;
        config.numTestsPerEvictionRun = poolConfig.numTestsPerEvictionRun;
        config.minEvictableIdleTimeMillis = poolConfig.minEvictableIdleTimeMillis;
        config.softMinEvictableIdleTimeMillis = poolConfig.softMinEvictableIdleTimeMillis;
        config.lifo = poolConfig.lifo;
        return config;
    }

}