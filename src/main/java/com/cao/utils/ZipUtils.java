package com.cao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件工具类
 */
public class ZipUtils {

	public static void doCompress(File file, ZipOutputStream out)
			throws IOException {
		if (file.exists()) {
			byte[] buffer = new byte[1024];
			FileInputStream fis = new FileInputStream(file);
			out.putNextEntry(new ZipEntry(file.getName()));
			int len = 0;
			// 读取文件的内容,打包到zip文件
			while ((len = fis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			out.flush();
			out.closeEntry();
			fis.close();
		}
	}

}