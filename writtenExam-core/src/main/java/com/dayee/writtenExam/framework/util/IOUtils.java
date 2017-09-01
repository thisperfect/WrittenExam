
package com.dayee.writtenExam.framework.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class IOUtils extends org.apache.commons.io.IOUtils {

    public static String toString(InputStream input) throws IOException {

        byte[] b = IOUtils.toByteArray(input);
        return toString(b);
    }

    public static String toString(byte[] b) throws IOException {

        if (b == null || b.length == 0) {
            return StringUtils.EMPTY;
        }
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(b);
            String encoding = CharsetEncodingUtils.getEncoding(is, 1024);
            String xmlResume = IOUtils.toString(is, encoding);
            return xmlResume;
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            closeQuietly(is);
        }
    }

    public static byte[] toByteArray(Object obj) throws IOException {

        ByteArrayOutputStream output = null;
        ObjectOutputStream oos = null;
        try {
            output = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(output);
            oos.writeObject(obj);
            return output.toByteArray();
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(oos);
        }
    }

    public static Object toObject(InputStream is) throws Exception {

        ByteArrayOutputStream output = null;
        ObjectInputStream oos = null;
        try {
            oos = new ObjectInputStream(is);
            return oos.readObject();

        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(oos);
        }
    }

 

    public static Object byteToObject(byte[] bytes) throws Exception {

        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } finally {
            IOUtils.closeQuietly(oi);
            IOUtils.closeQuietly(bi);
        }
        return obj;
    }

 
}
