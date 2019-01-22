package com.sust.fdfs;

import com.sust.fdfs.common.ImageUtils;
import com.sust.fdfs.pool.FastDFSTemplate;
import com.sust.fdfs.pool.FastDfsRv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Name: 测试
 * @Author: sust
 * @Date: 16:08 2017/8/30
 */
public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Autowired
    public FastDFSTemplate dfsTemplate;

    public static void main(String[] args) {
        Main test=new Main();
        test.upload();
    }

    /**
     * @Name: 上传
     * @Author: sust
     * @Date: 16:25 2017/8/30
     */
    public void upload() {
        try {
            String suffix = "jpg";
            String path = "D:\\pic\\prodtest.jpg";
            byte[] buff = ImageUtils.getBytes(path, suffix);
            FastDfsRv rv = dfsTemplate.upload(buff, suffix);
            String fileId=rv.getGroupName()+"/"+rv.getAccessName();
            logger.info("upload to fdfs success. file id is: " + fileId+" idLen: "+fileId.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
