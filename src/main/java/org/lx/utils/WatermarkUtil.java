package org.lx.utils;

import cn.hutool.core.img.ImgUtil;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title: WatermarkUtil
 * @Author: MrLu2
 * @Package: org.lx.utils
 * @Date: 2026/5/4 14:49
 * @Description: 水印工具类
 */

@Component
public class WatermarkUtil {

    /**
     * 给图片添加文字水印
     * @param inputStream  原图流
     * @param outputStream 输出流
     * @param text         水印文字
     */
    public void addTextWatermark(InputStream inputStream, OutputStream outputStream, String text) {
        ImgUtil.pressText(
                inputStream,
                outputStream,
                text,
                Color.WHITE,
                new Font("微软雅黑", Font.BOLD, 24),
                10,   // x 偏移
                10,   // y 偏移
                0.5f  // 透明度
        );
    }
}
