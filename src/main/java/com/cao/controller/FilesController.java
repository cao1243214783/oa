package com.cao.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cao.bean.Admin;
import com.cao.bean.Files;
import com.cao.bean.Payroll;
import com.cao.bean.User;
import com.cao.service.EmployeeService;
import com.cao.service.FilesService;
import com.cao.service.PayrollService;
import com.cao.utils.Utils;
import com.cao.utils.ZipUtils;

@Controller
@RequestMapping("files")
public class FilesController {

	@Autowired
	private FilesService filesService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PayrollService payrollService;

	@RequestMapping("uploadFile")
	public void uploadFile(
			@RequestParam("file") MultipartFile[] filess,
			@RequestParam(value = "permissionDepartments", required = false) String permissionDepartments,
			@RequestParam(value = "permissionEmployees", required = false) String permissionEmployees,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		Long employeeId = user.getEmployeeId();
		for (int m = 0; m < filess.length; m++) {
			MultipartFile file = filess[m];
			String filename = new String(file.getOriginalFilename().getBytes(
					"iso8859-1"), "UTF-8");// 解决乱码
			String target = Utils.proceseURL(filename);
			File f = new File(target);
			if (f.exists()) {
				response.sendRedirect("../html/error.jsp");
				return;
			}
			f.createNewFile();
			file.transferTo(f);
			int index = target.lastIndexOf("/");
			String path = target.substring(0, index + 1);
			String name = target.substring(index + 1);
			Long size = file.getSize();
			JSONObject permission = new JSONObject();
			if (permissionDepartments != null
					&& !"".equals(permissionDepartments)) {
				String[] departments = permissionDepartments.split(",");
				JSONArray departArray = new JSONArray();
				for (int i = 0; i < departments.length; i++) {
					departArray.add(departments[i]);
				}
				permission.put("departments", departArray);
			}
			if (permissionEmployees != null && !"".equals(permissionEmployees)) {
				String[] employees = permissionEmployees.split(",");
				JSONArray empArray = new JSONArray();
				for (int i = 0; i < employees.length; i++) {
					empArray.add(employees[i]);
				}
				permission.put("employees", empArray);
			}
			if ((permissionDepartments == null || ""
					.equals(permissionDepartments))
					&& (permissionEmployees == null || ""
							.equals(permissionEmployees))) {
				permission.put("ALL", "yes");
			}
			Files files = new Files(name, path, permission.toJSONString(),
					size, employeeId);
			filesService.save(files);
		}
		response.sendRedirect("../html/myseflInfo.jsp");
	}

	@RequestMapping("showFilesOfEmployee")
	@ResponseBody
	public void showFilesOfEmployee(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		List<Files> list = filesService.getMyUpload(user.getEmployeeId());
		JSONArray jsonArray = new JSONArray();
		for (Files files : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("filename", files.getFilename());
			jsonObject.put("uploadtime",
					Utils.DATE_FORMAT.format(files.getUploadTime()));
			double size = files.getSize();
			String sizeStr = "";
			int i = 0;
			while (size > 1024) {
				size = size / 1024;
				i++;
			}
			switch (i) {
			case 0:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "b";
				break;
			case 1:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "k";
				break;
			case 2:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "m";
				break;
			case 3:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "g";
				break;
			default:
				break;
			}
			jsonObject.put("size", sizeStr);
			String departments = "";
			String employees = "";
			JSONObject per = JSON.parseObject(files.getPermission());
			if ("yes".equals(per.get("ALL"))) {
				departments = "all";
				employees = "all";
			} else {
				JSONArray empArray = per.get("employees") == null
						|| "".equals(per.get("employees")) ? new JSONArray()
						: JSON.parseArray(per.get("employees").toString());
				JSONArray departArray = per.get("departments") == null
						|| "".equals(per.get("departments")) ? new JSONArray()
						: JSON.parseArray(per.get("departments").toString());
				for (int k = 0; k < empArray.size(); k++) {
					employees = employees + empArray.get(k);
				}
				for (int k = 0; k < departArray.size(); k++) {
					departments = departments + departArray.get(k);
				}
			}
			jsonObject.put("departments", departments);
			jsonObject.put("employees", employees);
			jsonObject.put("id", files.getId());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("delete")
	public void delete(@RequestParam("id") Long id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		Files files = filesService.getById(id);
		File file = new File(files.getPath() + files.getFilename());
		file.delete();
		filesService.delete(files);
		if (user != null) {
			response.sendRedirect("../html/myFiles.jsp");
		} else if (admin != null) {
			response.sendRedirect("../html/allFiles.jsp");
		}
	}

	@RequestMapping("showAvailableEmployees")
	@ResponseBody
	public void showAvailableEmployees(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		User user = (User) request.getSession().getAttribute("user");
		List<Files> list = filesService.getByEmployeeId(user.getEmployeeId());
		JSONArray jsonArray = new JSONArray();
		for (Files files : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("filename", files.getFilename());
			jsonObject.put("uploadtime",
					Utils.DATE_FORMAT.format(files.getUploadTime()));
			double size = files.getSize();
			String sizeStr = "";
			int i = 0;
			while (size > 1024) {
				size = size / 1024;
				i++;
			}
			switch (i) {
			case 0:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "b";
				break;
			case 1:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "k";
				break;
			case 2:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "m";
				break;
			case 3:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "g";
				break;
			default:
				break;
			}
			jsonObject.put("size", sizeStr);
			jsonObject.put("id", files.getId());
			System.out.println(files.getEmployeeId());
			jsonObject.put("employeeName",
					employeeService.getById(files.getEmployeeId()).getName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("downloadFile")
	public void downloadFile(@RequestParam("id") Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Files f = filesService.getById(id);
		File file = new File(f.getPath() + f.getFilename());
		if (!file.exists()) {
			response.sendRedirect("../html/error.jsp");
			return;
		}
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(f.getFilename(), "UTF-8"));
		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}

	@RequestMapping("downloadFiles")
	public void downloadFiles(@RequestParam("ids") String ids,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] str = ids.split("_");
		List<Long> idList = new ArrayList<Long>();
		for (int i = -0; i < str.length; i++) {
			if ("".equals(str[i].trim()))
				continue;
			Long id = Long.parseLong(str[i].trim());
			if (idList.contains(id)) {
				idList.remove(id);
			} else {
				idList.add(id);
			}
		}
		List<File> list = new ArrayList<File>();
		for (Long id : idList) {
			Files files = filesService.getById(id);
			File file = new File(files.getPath() + files.getFilename());
			if (file.exists())
				list.add(file);
		}
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition",
				"attachment; filename=files.zip");
		ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
		try {
			for (File f : list) {
				ZipUtils.doCompress(f, out);
				response.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	@RequestMapping("outToExcel")
	public void outToExcel(@RequestParam("year") Integer year,
			@RequestParam("month") Integer month, HttpServletResponse response) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(year + "-" + month + "工资表");
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("时间");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("工资");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("明细");
		cell.setCellStyle(style);

		Calendar calendar = Calendar.getInstance();
		List<Payroll> list;
		if (year != null && month != null) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month - 1);
			list = payrollService.getByEmployeeIdAndTime(null,
					Utils.YYYY_MM.format(calendar.getTime()));
		} else if (year == null && month != null) {
			list = payrollService.getByEmployeeIdAndTime(null, month < 10 ? "0"
					+ month.toString() : month.toString());
		} else if (year != null && month == null) {
			list = payrollService.getByEmployeeIdAndTime(null, year.toString());
		} else {
			list = payrollService.getByEmployeeIdAndTime(null, null);
		}

		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			Payroll payroll = (Payroll) list.get(i);
			row.createCell((short) 0).setCellValue(payroll.getEmployee_id());
			row.createCell((short) 1).setCellValue(payroll.getEmployeeName());
			row.createCell((short) 2).setCellValue(payroll.getTime());
			row.createCell((short) 3).setCellValue(payroll.getRealWages());
			row.createCell((short) 4).setCellValue(payroll.getDetail());
		}
		try {
			response.setHeader("content-disposition", "attachment;filename="
					+ URLEncoder.encode("工资表.xls", "UTF-8"));
			File file = new File(Utils.proceseURL("工资表.xls"));
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout);
			fout.close();
			FileInputStream in = new FileInputStream(file);
			OutputStream out = response.getOutputStream();
			byte buffer[] = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("getAllFiles")
	@ResponseBody
	public void getAllFiles(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		List<Files> list = filesService.getAll();
		JSONArray jsonArray = new JSONArray();
		for (Files files : list) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("filename", files.getFilename());
			jsonObject.put("uploadtime",
					Utils.DATE_FORMAT.format(files.getUploadTime()));
			double size = files.getSize();
			String sizeStr = "";
			int i = 0;
			while (size > 1024) {
				size = size / 1024;
				i++;
			}
			switch (i) {
			case 0:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "b";
				break;
			case 1:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "k";
				break;
			case 2:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "m";
				break;
			case 3:
				sizeStr = Utils.DECIMAL_FORMAT.format(size) + "g";
				break;
			default:
				break;
			}
			jsonObject.put("size", sizeStr);
			String departments = "";
			String employees = "";
			JSONObject per = JSON.parseObject(files.getPermission());
			if ("yes".equals(per.get("ALL"))) {
				departments = "all";
				employees = "all";
			} else {
				JSONArray empArray = per.get("employees") == null
						|| "".equals(per.get("employees")) ? new JSONArray()
						: JSON.parseArray(per.get("employees").toString());
				JSONArray departArray = per.get("departments") == null
						|| "".equals(per.get("departments")) ? new JSONArray()
						: JSON.parseArray(per.get("departments").toString());
				for (int k = 0; k < empArray.size(); k++) {
					employees = employees + empArray.get(k);
				}
				for (int k = 0; k < departArray.size(); k++) {
					departments = departments + departArray.get(k);
				}
			}
			jsonObject.put("departments", departments);
			jsonObject.put("employees", employees);
			jsonObject.put("id", files.getId());
			jsonObject.put("employee",
					employeeService.getById(files.getEmployeeId()).getName());
			jsonArray.add(jsonObject);
		}
		PrintWriter out = response.getWriter();
		out.write(jsonArray.toJSONString());
		out.flush();
		out.close();
	}

	@RequestMapping("alterFilesInfo")
	@ResponseBody
	public void alterFilesInfo(@RequestParam("id") Long id,
			@RequestParam("departments") String permissionDepartments,
			@RequestParam("employees") String permissionEmployees,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		User user = (User) request.getSession().getAttribute("user");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		Files files = filesService.getById(id);
		JSONObject permission = new JSONObject();
		if ("ALL".equals(permissionDepartments)
				&& "ALL".equals(permissionEmployees)) {
			permission.put("ALL", "yes");
		} else {
			if (permissionDepartments != null
					&& !"".equals(permissionDepartments)) {
				String[] departments = permissionDepartments.split(",");
				JSONArray departArray = new JSONArray();
				for (int i = 0; i < departments.length; i++) {
					departArray.add(departments[i]);
				}
				permission.put("departments", departArray);
			}
			if (permissionEmployees != null && !"".equals(permissionEmployees)) {
				String[] employees = permissionEmployees.split(",");
				JSONArray empArray = new JSONArray();
				for (int i = 0; i < employees.length; i++) {
					empArray.add(employees[i]);
				}
				permission.put("employees", empArray);
			}
			if ((permissionDepartments == null || ""
					.equals(permissionDepartments))
					&& (permissionEmployees == null || ""
							.equals(permissionEmployees))) {
				permission.put("ALL", "yes");
			}
		}
		files.setPermission(permission.toJSONString());
		filesService.update(files);
		if (user != null) {
			response.sendRedirect("../html/myFiles.jsp");
		} else if (admin != null) {
			response.sendRedirect("../html/allFiles.jsp");
		}
	}
}
