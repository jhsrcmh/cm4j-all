package com.cm4j.taobao.utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 截图方法
 * 
 * @author yang.hao
 * @since 2011-9-3 下午7:53:00
 */
public class Dimensioner {

	public static void main(String[] args) {
		try {
			// 获取屏幕大小
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			// 截图
			BufferedImage bim = new Robot().createScreenCapture(new Rectangle(0, 0, dim.width, dim.height));
			ImageIO.write(bim, "jpg", new File("C:\\test.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
