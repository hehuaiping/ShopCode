package com.xh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 这是IO工具类
 * @author Administrator
 *
 */
public class IOUtil {
	private IOUtil(){}
	
	public static void IoToOut(InputStream in,OutputStream out) throws IOException {
		byte[] by = new byte[1024];
		int len = 0;
		while((len=in.read(by)) != -1 ) {
			out.write(by, 0, len);
		}
	}
	
	public static void close(InputStream in , OutputStream out) {
		if(in!=null) {
			try {
				in.close();
			} catch (IOException e) {
				in = null;
				e.printStackTrace();
			}
		} 
		if(out!=null) {
			try {
				out.close();
			} catch (IOException e) {
				out = null;
				e.printStackTrace();
			}
		} 
	}
}
