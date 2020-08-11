package cn.stylefeng.guns.util;

import cn.afterturn.easypoi.word.WordExportUtil;
import cn.hutool.core.lang.Assert;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

public class WordUtil {
	/**
	 * 导出word方法
	 * @param request
	 * @param response
	 * @param html
	 * @param title
	 */
	public static void exportWords(HttpServletRequest request, HttpServletResponse response, String html, String title) {
		ServletOutputStream ostream = null;
		POIFSFileSystem poifs = null;
		ByteArrayInputStream bais = null;
		try {
			String content = html;
			//设置编码
			byte b[] = content.getBytes("utf-8");
			bais = new ByteArrayInputStream(b);
			poifs = new POIFSFileSystem();
			DirectoryEntry directory = poifs.getRoot();
			DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
			request.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			response.addHeader("Content-Disposition", "attachment;filename=" +
					new String(title.getBytes("GB2312"), "iso8859-1") + ".doc");
			ostream = response.getOutputStream();
			poifs.writeFilesystem(ostream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
				if (ostream != null) {
					ostream.close();
				}
				if (poifs != null) {
					poifs.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
