package com.centerm.util;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbUtil
{
	/**
	 * 使用jaxb将xml转换为object，xml是以字节数组形式传入
	 * @param clazz
	 * @param xmlBytes
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @author  liuyu
	 * @since  2015-6-6
	 */
	public static Object convertToObj(Class<?> clazz, byte[] xmlBytes) throws UnsupportedEncodingException, JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller un = jaxbContext.createUnmarshaller();
//		InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(xmlBytes),"GBK");
		ByteArrayInputStream reader = new ByteArrayInputStream(new String(xmlBytes, "GBK").getBytes("utf-8"));
//		ByteArrayInputStream reader = new ByteArrayInputStream(xmlBytes);
		return un.unmarshal(reader);
	}
	
	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param t 泛型
	 * @param writer
	 * @return 
	 */
	public static <T> String convertToXml(T t) {
		return convertToXml(t, "UTF-8");
	}
	
	/**
	 * JavaBean转换成xml
	 * @param t 泛型
	 * @param encoding
	 * @return 
	 */
	public static <T> String convertToXml(T t, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(t, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	

	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}
}
