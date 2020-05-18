package cn.stylefeng.guns.sys.core.util;

import cn.stylefeng.roses.core.util.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 下载文件
 *
 * @author fengshuonan
 * @Date 2019/8/7 23:14
 */
public class FileDownload {

    /**
     * @param response
     * @param filePath 文件完整路径(包括文件名和扩展名)
     * @param fileName 下载后看到的文件名
     * @return 文件名
     */
    public static HttpServletResponse fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception {
        /*byte[] bytes = FileUtil.toByteArray(filePath);*/
        InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + buffer.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(buffer);
        outputStream.flush();
        outputStream.close();

        return response;
    }

}
