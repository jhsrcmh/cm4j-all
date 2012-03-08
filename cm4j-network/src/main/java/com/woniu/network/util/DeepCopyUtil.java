package com.woniu.network.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 深拷贝对象
 * 
 * @author yang.hao
 * @since 2011-11-2 下午5:28:05
 */
public class DeepCopyUtil {

	@SuppressWarnings("unchecked")
	public static <T> T deepCopy(T oldObj){
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(oldObj);
			out.flush();
			out.close();
			// Retrieve an input stream from the byte array and read
			// a copy of the object back in.
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bis);
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return (T) obj;
	}

}
