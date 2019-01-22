package com.sust.fdfs.common;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by lenovo on 2017/8/30.
 */
public class ImageUtils {

    /**
     *@description: 获取照片的流
     *@author: sust
     *@param: [url]
     *@return: java.io.InputStream
     *@date: 2017/2/17 14:35
     */
    public static byte[] getBytes(String path,String suffix) throws Exception {
        if(path==null) return  null;
        BufferedImage bi= ImageIO.read(new File(path));
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut;
        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bi,suffix, imOut);
        } catch ( IOException e ) {
            e.printStackTrace();
        }finally {
            if(bs!=null)bs.close();
        }
        return bs.toByteArray();
    }
}
