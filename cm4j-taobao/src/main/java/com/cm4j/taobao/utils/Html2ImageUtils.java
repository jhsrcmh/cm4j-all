package com.cm4j.taobao.utils;

import gui.ava.html.image.generator.HtmlImageGenerator;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 将html生成为图片<br />
 * 引用项目地址：http://code.google.com/p/java-html2image/
 * 
 * <pre>
 * HtmlImageGenerator Methods
 * 
 * loadUrl(url) - Loads HTML from URL object or URL string.
 * loadHtml(html) - Loads HTML source.
 * saveAsImage(file) - Save loaded HTML as image.
 * saveAsHtmlWithMap(file, imageUrl) - Creates an HTML file containing client-side image-map <map> generated from HTML's links.
 * getLinks() - List all links in the HTML document and their corresponding href, target, title, position and dimension.
 * getBufferedImage() - Get AWT buffered image of the HTML.
 * getLinksMapMarkup(mapName) - Get HTML snippet of the client-side image-map <map> generated from the links.
 * get/setOrientation(orientation) - Get/Set document orientation (left-to-right or right-to-left).
 * get/setSize(dimension) - Get/Set size of the generated image.
 * </pre>
 * 
 * @author yang.hao
 * @since 2011-9-4 下午09:04:36
 */
public class Html2ImageUtils {

	/**
	 * 将html生成为图片<br />
	 * 
	 * @param url
	 * @param imagePath
	 */
	public static void html2image(URL url, String imagePath) {
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		imageGenerator.loadUrl(url);
		imageGenerator.saveAsImage(imagePath);
	}
	
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("file:///D:/SOFT/workspace/cm4j-all/cm4j-taobao/src/main/webapp/generation/sample/generation.htm");
		html2image(url, "result.png");
	}
}
