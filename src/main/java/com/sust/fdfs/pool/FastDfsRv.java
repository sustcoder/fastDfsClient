package com.sust.fdfs.pool;

/**
 * <p>Description:  com.sustcoder.fdfs return object
 * <p>Version:v1.0
 * <p>Copyright ©2015sust All Rights Reserved. </p>
 * <p>Company:sust</p>
 * <p>Author:sust
 * <p>Date: 2017/1/9  16:27
 */
public class FastDfsRv implements java.io.Serializable {

    private String groupName;
    private String accessName;

    public FastDfsRv() {
    }

    public FastDfsRv(String groupName, String accessName) {
        this.groupName = groupName;
        this.accessName = accessName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    @Override
    public String toString() {
        return "{" +
                "groupName='" + groupName + '\'' +
                ", accessName='" + accessName + '\'' +
                '}';
    }
}
