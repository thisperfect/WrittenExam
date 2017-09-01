package com.dayee.writtenExam.framework.util;

/**
 * Created by tang on 2016/10/6.
 */
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.stream.FileImageOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.dayee.writtenExam.framework.constant.Constants;
import com.dayee.writtenExam.framework.constant.MimeTypeConstants;
import com.dayee.writtenExam.framework.mail.MailException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.model.FileInformationBlock;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.common.POIFSConstants;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.poifs.storage.HeaderBlockConstants;
import org.apache.poi.util.LittleEndian;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Log   logger                = LogFactory
            .getLog(FileUtils.class);

    public static final byte[] WORD_2003_FILE_HEADER = new byte[] { -48, -49,
            17, -32                                 };

    public static final byte[] WORD_2007_FILE_HEADER = POIFSConstants.OOXML_FILE_HEADER;

    public static final byte[] PDF_FILE_HEADER       = new byte[] {(byte) 0x25, (byte) 0x50, (byte) 0x44, (byte) 0x46};

    public static final byte[] RTF_FILE_HEADER       = new byte[] { '{', '\\',
            'r', 't', 'f'                           };

    public static final byte[] MHT_FILE_HEADER       = new byte[] { 'M', 'I',
            'M', 'E', '-'                           };

    public static final String Slash                 = "\\";

    public static final String Slash_                = "/";

    private static int         counter               = -1;

    public static final String FILE_NAME_EXCLUDED    = "[/:*?<>|\"\\\\]";

    public static final String SHORT_FILE_NAME       = "___";

    public static final int    FILE_NAME_MAX_LEN     = 100;

    public static final int    FILE_NAME_MIN_LEN     = 3;

    public static final String CID_NAME = "cid:";

    private static Random random = new Random();

    public static int getCounter() {

        if (counter == -1) {
            counter = (int)(random.nextInt() & 0xffff);
            return counter;
        }
        return counter++;
    }

    private static final String FILE_NAME     = "file";

    private static final int    FILE_NAME_LEN = 80;

    public static String handleFileName(String name, String type) {

        if (StringUtils.hasLength(name)) {
            if (name.length() > FILE_NAME_LEN) {
                int index = name.lastIndexOf(StringUtils.DOT);
                String suffix = name.substring(index);
                String prefix = name.substring(0, index);
                int length = FILE_NAME_LEN - suffix.length();
                prefix = StringUtils.substring(prefix, 0, length);
                name = prefix + suffix;
                // file.setName(name);
            }
        } else {
            if (counter == -1) {
                counter = new Random().nextInt() & 0xffff;
            }
            counter++;

            name = FILE_NAME + counter
                    + MimeTypeConstants.MIMETYPE_FILE_SUFFIX.get(type);
            // file.setName(name);
        }
        int index = name.lastIndexOf(StringUtils.DOT);
        if (index < 0) {
            name = name + MimeTypeConstants.MIMETYPE_FILE_SUFFIX.get(type);
        }
        return name;
    }

    public static String getFileNameSuffix (String fileName) {

        if (StringUtils.isEmpty(fileName) || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String filteFilePath(String folderPath) {

        folderPath = folderPath.replace(FileUtils.Slash_, File.separator);
        folderPath = folderPath.replace(FileUtils.Slash, File.separator);
        return folderPath;
    }

    public static String filteFileName(String fileName) {

        if (StringUtils.hasLength(fileName)) {
            char[] charArray = fileName.toCharArray();
            StringBuffer buffer = new StringBuffer();
            for (char ch : charArray) {
                if (Character.isLetterOrDigit(ch) || Character.isSpaceChar(ch)
                        || CharacterUtils.isChineseLetter(ch)
                        || PatternUtils.isPunct(ch)) {
                    buffer.append(ch);
                }
            }

            fileName = buffer.toString();
        }

        if (StringUtils.hasLength(fileName)) {
            fileName = fileName.replaceAll(FILE_NAME_EXCLUDED,
                    StringUtils.EMPTY);
        }
        fileName = StringUtils.trimBlank(fileName);
        if (StringUtils.isEmpty(fileName)) {
            return SHORT_FILE_NAME + getCounter();
        }

        if (fileName.length() < FILE_NAME_MIN_LEN) {
            fileName = fileName + SHORT_FILE_NAME;
        }

        if (fileName.length() > FILE_NAME_MAX_LEN) {
            int index = fileName.lastIndexOf(StringUtils.DOT);
            if (index > 0) {
                String suffix = fileName.substring(index);
                String prefix = fileName.substring(0, index);

                int length = FILE_NAME_MAX_LEN - suffix.length();
                prefix = StringUtils.substring(prefix, 0, length);
                fileName = prefix + suffix;
            } else {
                fileName = StringUtils
                        .substring(fileName, 0, FILE_NAME_MAX_LEN);
            }
        }

        return fileName;
    }

    public static String getRealFilePath(String path) {

        path = path.replace(StringUtils.DIAGONAL, File.separator);
        path = path.replace("\\", File.separator);
        if (!path.contains(StringUtils.COLON) && !path
                .startsWith(File.separator)) {
            //TODO 替换找到绝对路径的方法
            String realPath = "/Users/tang/Desktop";
            if (!realPath.endsWith(File.separator)) {
                realPath = realPath + File.separator;
            }
            path = realPath + path;
        }
        return path;
    }

    public static void close(Writer device) {

        if (device != null) {
            try {
                device.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void close(Reader device) {

        if (device != null) {
            try {
                device.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void close(OutputStream device) {

        if (device != null) {
            try {
                device.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static void close(InputStream device) {

        if (device != null) {
            try {
                device.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public static File createFile(String fileName) {

        File file = new File(fileName);
        try {
            if (!file.exists() || !file.isFile()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            logger.error("Caused error fileName:"+fileName);
            logger.error(e.getMessage(), e);
        }
        return file;
    }

    public static File createTempDirectory(String directory) {

        directory = filteFileName(directory);
        //TODO 替换临时目录方法
        String sysTemp = "/Users/tang/Desktop";
        File tempDir = new File(sysTemp + File.separator
                + directory
                + getCounter());
        if (!tempDir.exists() || !tempDir.isDirectory()) {
            tempDir.mkdirs();
        }

        return tempDir;
    }

    public static File createTempFile(String prefix, String suffix)
            throws Exception {

        String newprefix = null;
        try {
            newprefix = filteFileName(prefix);
            return File.createTempFile(newprefix, suffix);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("prefix=" + prefix);
                logger.error("newprefix=" + newprefix);
                logger.error("suffix=" + suffix);
            }
            throw e;
        }
    }

    public static File createTempFile(String prefix,
                                      String suffix,
                                      File directory) throws Exception {

        String newprefix = null;
        try {
            newprefix = filteFileName(prefix);
            return File.createTempFile(newprefix, suffix, directory);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("prefix=" + prefix);
                logger.error("newprefix=" + newprefix);
                logger.error("suffix=" + suffix);
                logger.error("directory=" + directory);
            }
            throw e;
        }
    }

    public static File createFile(String prefix, String suffix, File directory)
            throws Exception {

        String newprefix = null;
        try {
            newprefix = filteFileName(prefix);
            File file = new File(directory, prefix + suffix);
            return file;
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("prefix=" + prefix);
                logger.error("newprefix=" + newprefix);
                logger.error("suffix=" + suffix);
                logger.error("directory=" + directory);
            }
            throw e;
        }
    }

    public static void createDirectory(String directory) {

        File file = new File(directory);
        try {
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void createDirectory(File file) {

        try {
            if (!file.exists() || !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void createDirectory(String directory, String subDirectory) {

        String[] dir;

        File file = new File(directory);
        try {
            if ((subDirectory.length() == 0) && (!file.exists() || !file
                    .isDirectory())) {
                file.mkdirs();
            } else if (subDirectory.length() != 0) {
                dir = subDirectory.replace(Slash, StringUtils.DIAGONAL)
                        .split(StringUtils.DIAGONAL);
                String temstr = directory;
                File temfile = null;
                for (int i = 0; i < dir.length; i++) {

                    temstr += StringUtils.DIAGONAL + dir[i];
                    temfile = new File(temstr);
                    if (!temfile.exists() || !temfile.isDirectory()) {
                        temfile.mkdir();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void delete(String srcPath) {

        if (StringUtils.isEmpty(srcPath)) {

            return;
        }
        File file = new File(srcPath);
        if (!file.exists()) {

            return;
        }

        delete(file);
    }

    public static void delete(File file) {

        try {
            if (file != null) {
                if (file.isDirectory()) {
                    File[] f = file.listFiles();
                    for (int i = 0; i < f.length; i++) {
                        delete(f[i]);
                    }
                    file.delete();

                } else {
                    file.delete();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static final Pattern MHT_FILE_PATTERN = Pattern
            .compile("content\\-type:\\s*multipart\\s*/\\s*related;|content\\-type:\\s*text\\s*/\\s*html;",
                    Pattern.CASE_INSENSITIVE);

    public static boolean isMht(byte[] content) {

        if (content.length < MHT_FILE_HEADER.length) {
            return false;
        }

        try {
            InputStream is = new ByteArrayInputStream(content);
            String encoding = CharsetEncodingUtils
                    .getEncodingNotGuess(is, content.length);

            String str = null;
            if (StringUtils.hasLength(encoding)) {
                str = new String(content, encoding);
            } else {
                str = new String(content);
            }
            return MHT_FILE_PATTERN.matcher(str).find();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static boolean isRtf(byte[] content) {

        if (content.length < RTF_FILE_HEADER.length) {
            return false;
        }
        for (int i = 0; i < RTF_FILE_HEADER.length; i++) {
            if (content[i] != RTF_FILE_HEADER[i]) {
                return false;
            }
        }

        return true;
    }

    protected static final String STREAM_WORD_DOCUMENT = "WordDocument";

    public static boolean isWord95(byte[] content) {

        InputStream is = new ByteArrayInputStream(content);
        try {
            POIFSFileSystem pfilesystem = HWPFDocument.verifyAndBuildPOIFS(is);
            DirectoryNode directory = pfilesystem.getRoot();
            DocumentEntry documentProps = (DocumentEntry) directory
                    .getEntry(STREAM_WORD_DOCUMENT);
            byte[] _mainStream = new byte[documentProps.getSize()];

            directory.createDocumentInputStream(STREAM_WORD_DOCUMENT)
                    .read(_mainStream);

            FileInformationBlock _fib = new FileInformationBlock(_mainStream);
            if (_fib.getNFib() < 106) {
                return true;
            }
        } catch (Exception e) {
            // logger.error(e.getMessage(), e);
            return false;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return false;
    }

    public static boolean isWord2003(byte[] content) {

        if (content.length < WORD_2003_FILE_HEADER.length) {
            return false;
        }

        for (int i = 0; i < WORD_2003_FILE_HEADER.length; i++) {

            if (content[i] != WORD_2003_FILE_HEADER[i]) {
                return false;
            }
        }

        return true;
    }

    public static boolean isWord2007(byte[] content) {

        if (content.length < WORD_2007_FILE_HEADER.length) {
            return false;
        }

        long signature = LittleEndian
                .getLong(content, HeaderBlockConstants._signature_offset);

        if (signature != HeaderBlockConstants._signature) {

            for (int i = 0; i < WORD_2007_FILE_HEADER.length; i++) {
                if (content[i] != WORD_2007_FILE_HEADER[i]) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    public static final Pattern HTML_PATTERN      = Pattern
            .compile("<html",
                    Pattern.CASE_INSENSITIVE);

    public static final Pattern HTML_HEAD_PATTERN = Pattern
            .compile("<head",
                    Pattern.CASE_INSENSITIVE);

    public static final Pattern HTML_BODY_PATTERN = Pattern
            .compile("<body",
                    Pattern.CASE_INSENSITIVE);

    public static final String  HTML_             = "<html";

    public static final String  HTML_HEAD         = "<head";

    public static final String  HTML_BODY         = "<body";

    public static boolean isHtml(byte[] content) {

        if (content.length < WORD_2007_FILE_HEADER.length) {
            return false;
        }

        try {
            InputStream is = new ByteArrayInputStream(content);
            String encoding = CharsetEncodingUtils
                    .getEncodingNotGuess(is, content.length);

            String str = null;
            if (StringUtils.hasLength(encoding)) {
                str = new String(content, encoding);
            } else {
                str = new String(content);
            }
            str = str.toLowerCase();
            return str.contains(HTML_) && str.contains(HTML_HEAD)
                    && str.contains(HTML_BODY);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static boolean isPdf(byte[] content) {
        if (content == null || content.length < PDF_FILE_HEADER.length) {
            return false;
        }

        for (int i = 0; i < PDF_FILE_HEADER.length; i++) {
            if (content[i] != PDF_FILE_HEADER[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHtml(String str) {

        if (str.length() < WORD_2007_FILE_HEADER.length) {
            return false;
        }

        try {
            str = str.toLowerCase();
            return str.contains(HTML_) && str.contains(HTML_HEAD)
                    && str.contains(HTML_BODY);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    private static final String XML_ = "<?xml";

    public static boolean isXml(byte[] content) {

        if (content.length < WORD_2007_FILE_HEADER.length) {
            return false;
        }

        try {
            String str = new String(content, Constants.DEFAULT_ENCODING);
            str = str.toLowerCase();
            return str.startsWith(XML_);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    private static final String WORDML_ = "<?mso-application progid=\"word.document\"?>";

    public static boolean isWordML(byte[] content) {

        if (content.length < WORD_2007_FILE_HEADER.length) {
            return false;
        }
        try {
            String str = new String(content, Constants.DEFAULT_ENCODING);
            str = str.toLowerCase();
            return str.contains(WORDML_);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

    }

    public static boolean isText(byte[] content) {

        if (content.length < WORD_2007_FILE_HEADER.length) {
            return false;
        }

        InputStream is = null;
        try {

            is = new ByteArrayInputStream(content);
            String encoding = CharsetEncodingUtils.getEncoding(is,
                    content.length);
            return encoding != null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    public static String getFileSuffix(String mimeType) {

        if (StringUtils.isEmpty(mimeType)) {
            return StringUtils.EMPTY;
        }

        try {
            String suffix = MimeTypeConstants.MIMETYPE_FILE_SUFFIX
                    .get(mimeType);
            if (suffix == null) {
                suffix = StringUtils.EMPTY;
            }
            return suffix;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new MailException(e.getMessage(), e);
        }
    }

    public static void writeObjectToFile(String file, Object obj)
            throws IOException {

        writeObjectToFile(new File(file), obj);
    }

    public static void writeObjectToFile(File file, Object obj)
            throws IOException {

        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {

            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(fos);
        }
    }

    public static void byteToImage(byte[] data, String path, String name) {

        if (data.length < 3 || path.equals(""))
            return;
        try {
            FileImageOutputStream imageOutput = new FileImageOutputStream(
                    new File(path, name));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out
                    .println("Make Picture success,Please find image in " + path
                            + name);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex);
            ex.printStackTrace();
        }
    }

    public static void convert2Html(String fileName, String outPutFile)
            throws TransformerException, IOException,
            ParserConfigurationException {

        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(fileName));//WordToHtmlUtils.loadDoc(new FileInputStream(inputFile));
        //兼容2007 以上版本
        //        XSSFWorkbook  xssfwork=new XSSFWorkbook(new FileInputStream(fileName));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());

        wordToHtmlConverter.setPicturesManager( new PicturesManager()
        {
            public String savePicture(byte[] content,
                                      PictureType pictureType, String suggestedName,
                                      float widthInches, float heightInches )
            {
                return "test/"+suggestedName;
            }
        } );
        wordToHtmlConverter.processDocument(wordDocument);
        //save pictures
        List pics=wordDocument.getPicturesTable().getAllPictures();
        if(pics!=null){
            for(int i=0;i<pics.size();i++){
                Picture pic = (Picture)pics.get(i);
                System.out.println();
                try {
                    pic.writeImageContent(new FileOutputStream("/Users/tang/Desktop/"
                                                               + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        Document htmlDocument = wordToHtmlConverter.getDocument();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);


        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "HTML");
        serializer.transform(domSource, streamResult);
        out.close();
        writeFile(new String(out.toByteArray()), outPutFile);
    }

    public static void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        org.jsoup.nodes.Document doc = Jsoup.parse(content);
        content=doc.html();
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(getFileNameSuffix("asdfasd.a.c"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
