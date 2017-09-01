
package com.dayee.writtenExam.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.dayee.writtenExam.framework.constant.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ZipUtils {

    private static final Log logger = LogFactory.getLog(ZipUtils.class);

    public static byte[] gzip(byte[] inputFile) throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        InputStream is = new ByteArrayInputStream(inputFile);

        GZIPOutputStream gos = new GZIPOutputStream(os);
        try {
            IOUtils.copy(is, gos);
        } finally {
            gos.finish();
            gos.flush();
            gos.close();
        }
        return os.toByteArray();
    }

    public static byte[] gzip(String inputFile) throws Exception {

        return gzip(inputFile.getBytes(Constants.DEFAULT_ENCODING));
    }

    public static byte[] ungzip(byte[] b) {

        GZIPInputStream gis = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            gis = new GZIPInputStream(new ByteArrayInputStream(b));

            IOUtils.copy(gis, os);

            return os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(gis);
        }
        return null;
    }

    public static String ungzipToString(byte[] b) {

        byte[] by = ungzip(b);
        if (by != null) {
            try {
                String content = new String(by, Constants.DEFAULT_ENCODING);
                return content;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String ungzip(InputStream is) {

        GZIPInputStream gis = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            gis = new GZIPInputStream(is);

            IOUtils.copy(gis, os);

            String content = new String(os.toByteArray(),
                    Constants.DEFAULT_ENCODING);
            return content;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(gis);
        }
        return null;
    }

}
