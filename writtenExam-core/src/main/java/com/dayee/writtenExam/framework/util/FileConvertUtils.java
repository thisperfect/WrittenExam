package com.dayee.writtenExam.framework.util;

import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.constant.MimeTypeConstants;
import com.dayee.writtenExam.model.DefaultAuthenticator;

import com.dayee.writtenExam.model.attachment.UploadFileInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tang on 2017/1/17.
 */
public class FileConvertUtils {

    private static Logger logger = LoggerFactory.getLogger(FileConvertUtils.class);

    private static Pattern CONTENT_ID_PATTERN = Pattern.compile("<(.+)>");

    public static File findOutFileType(UploadFileInfo file) {

        String type = file.getContentType();
        byte[] content = file.getContent();

        if (content == null) {
            return null;
        }

        File resultFile = new File("");
        if (StringUtils.isEmpty(type)) {

            if (FileUtils.isWordML(content)) {

                return resultFile;
            }
        }

        return resultFile;
    }

    public static String wordMLToHtml(byte[] content) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(
                    new FileInputStream("/Users/tang/Documents/Idea/work/writtenExam/writtenExam-core/src/main/java/com/dayee/writtenExam/framework/util/word2html.xsl")));

        InputStream is = new ByteArrayInputStream(content);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        transformer.transform(new StreamSource(is), new StreamResult(out));
        FileUtils.writeFile(out.toString(Constants.DEFAULT_ENCODING), "/Users/tang/Desktop/testWordML2HTML.html");
        return null;
    }

    public static String word03ToHtml(byte[] content) throws Exception {
        //ByteArrayInputStream is = new ByteArrayInputStream(content);

        HWPFDocument extractor = new HWPFDocument(new FileInputStream("/Users/tang/Downloads/importExample/智联.doc"));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.processDocument(extractor);
        Document doc = wordToHtmlConverter.getDocument();
        // new File(filePath);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();

        serializer.setOutputProperty(OutputKeys.ENCODING,
                                     Constants.DEFAULT_ENCODING);
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        String htmlContent = out.toString(Constants.DEFAULT_ENCODING);
        FileUtils.writeFile(htmlContent, "/Users/tang/Desktop/testWordML2HTML.html");

        return null;
    }

    public static void main(String[] args) throws Exception {

//        String[] fileNames = {
//        "boss直聘.docx", "boss直聘2.doc"
//        };
//        for (String fileName : fileNames) {
//            java.io.File file = new java.io.File(
//                    "/Users/tang/Downloads/importExample/" + fileName);
//
//            InputStream inputStream = new FileInputStream(file);
//
//            java.io.File toFileFolder = new java.io.File("/Users/tang/Desktop");
//
//            System.out.println(doc2Html(inputStream, toFileFolder, file.getName()));
//        }
        long t1 = System.currentTimeMillis();
        byte[] bytes = word2PdfByLibreOffice(new File("/Users/tang/Downloads/importExample/boss直聘.docx"));
        long t2 = System.currentTimeMillis();

//        System.out.println(new String(org.apache.commons.io.IOUtils.toString(bytes,"GBK").getBytes("UTF-8")));

        File file = new File("/Users/tang/Desktop/test0121.pdf");

        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        IOUtils.write(bytes, fileOutputStream);

        fileOutputStream.flush();

//        doc2Pdf(new java.io.File("/Users/tang/Downloads/importExample/智联.doc"));

//        System.out.println(t2 - t1);
    }

//    public static String doc2Pdf(java.io.File file) throws Exception {
//
//        String soffice_host = "127.0.0.1";
//        String soffice_port = "8100";
//        logger.debug("soffice_host:"+soffice_host+",soffice_port:"+soffice_port);
//
//        Date date = new Date();
//        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
//        String timesuffix = sdf.format(date);
//        String htmFileName = "htmlfile"+timesuffix+".pdf";
//        String docFileName = file.getName();
//        DocumentFormatRegistry documentFormatRegistry = new DocumentFormatRegistry();
//        java.io.File pdfOutputFile = java.io.File.createTempFile("tempPdf-" + UUID.randomUUID().toString().replace("-",""), MimeTypeConstants.PDF_FILE_SUFFIX);
//
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection();
//        try {
//            connection.connect();
//        } catch (ConnectException e) {
//            System.err.println("文件转换出错，请检查OpenOffice服务是否启动。");
//            logger.error(e.getMessage(), e);
//        }
//        // convert
//        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//        converter.convert(file, pdfOutputFile);
//        connection.disconnect();
//            /*      File  htmlOutputFile_rn = new File
//                    (htmlOutputFile.getAbsolutePath().substring(0,htmlOutputFile.getAbsolutePath().lastIndexOf("."))+".htm");
//                    htmlOutputFile.renameTo(htmlOutputFile_rn);
//                    return htmlOutputFile_rn.getName();*/
//
//        return htmFileName;
//    }

    public static byte[] word2PdfByLibreOffice(File file) throws Exception {
        String url = PropertiesEnum.RESUME_CONFIG.getProperty(Constants.FILE.ATTACHMENT_PREVIEW_URL);

        String result = null;
        ContentBody fbody = new FileBody(file);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        MultipartEntity mpEntity = new MultipartEntity(
                HttpMultipartMode.BROWSER_COMPATIBLE, null,
                Charset.forName("ISO-8859-1"));
        mpEntity.addPart("inputDocument", fbody);
        post.setEntity(mpEntity);

        try {
            HttpResponse response = httpclient.execute(post);

            logger.debug("get response from http server..");
            HttpEntity entity = response.getEntity();

            logger.info("response status: {}", response.getStatusLine());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            entity.writeTo(outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    public static String textToHtml(byte[] bytes) throws Exception {

        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        String encoding = CharsetEncodingUtils
                .getEncoding(is, bytes.length);
        if (StringUtils.isEmpty(encoding)) {
            encoding = Constants.DEFAULT_ENCODING;
        }
        String content = IOUtils.toString(is, encoding);

        StringBuffer buffer = new StringBuffer();
        content = content.replace(StringUtils.ENTER,
                                  StringUtils.ADD_CONTENT_SEPARATOR);
        content = content.replace(StringUtils.EMPTY_SPACE,
                                  StringUtils.NBSP_SEMICOLON);
        buffer.append("<html><head><title>");
        buffer.append("</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body><div style=\"text-align: left;\">");
        buffer.append(content);
        buffer.append("</div></body></html>");

        return buffer.toString();
    }

    /*public static Map<String, File> mhtToHtml(byte[] bytes) throws Exception {

        Map<String, File> fileMap = new HashMap<>();

        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Session mailSession;
        mailSession = Session.getDefaultInstance(System.getProperties(), new DefaultAuthenticator());

        MimeMessage msg = new MimeMessage(mailSession, is);
        Object content = msg.getContent();

        if (content instanceof Multipart) {

            MimeMultipart mp = (MimeMultipart) content;

            File html = new File();
            for (int i = 0; i < mp.getCount(); ++i) {

                MimeBodyPart bp = (MimeBodyPart) mp.getBodyPart(i);
                InputStream bpInputStream = bp.getInputStream();
                String bpContentType = bp.getContentType();
                String contentId = bp.getContentID();

                Pattern pattern = Pattern.compile("charset=\"(.*)\"");
                Matcher matcher = pattern.matcher(bpContentType);
                String charset = "";
                if (matcher.find()) {
                    charset = matcher.group(1);
                }

                if (bp.isMimeType(MimeTypeConstants.MIMETYPE_HTML)) {

                    html.setContent(IOUtils.toByteArray(bpInputStream));
                    html.setEncoding(charset);
                    html.setFileType(MimeTypeConstants.MIMETYPE_HTML);
                    html.setFileName(
                            UUID.randomUUID().toString().replace("-", "") + MimeTypeConstants.HTML_FILE_SUFFIX);

                    if (!fileMap.containsKey("keyHtml")) {
                        fileMap.put("keyHtml", html);
                    }
                } else if (bp.isMimeType(MimeTypeConstants.MIMETYPE_BMP)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_GIF)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_GIF_1)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_JPEG)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_JPG)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_PNG)
                        || bp.isMimeType(MimeTypeConstants.MIMETYPE_PJPEG)) {
                    File file = new File();

                    file.setContent(IOUtils.toByteArray(bpInputStream));
                    String type = bpContentType.contains(";") ? bpContentType
                            .substring(0, bpContentType.indexOf(";")) :
                            bpContentType;
                    file.setFileType(type);

                    contentId = StringUtils.trim(contentId);
                    Matcher m = CONTENT_ID_PATTERN.matcher(contentId);
                    if (m.matches()) {
                        contentId = m.group(1);
                    }
                    file.setFileName(
                            contentId + MimeTypeConstants.MIMETYPE_FILE_SUFFIX.get(type));

                    fileMap.put(contentId, file);
                }
            }

        }

        return fileMap;

    }*/

}
