package org.jbit.news.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.jbit.news.entity.News;

import com.alibaba.fastjson.JSON;

/**
 * 文件Controller的API 专门用于接口调用，返回JSON格式
 * 
 * @author Administrator
 *
 */
@WebServlet("/api/fileServlet")
public class FileApiServlet extends HttpServlet {

	private static final long serialVersionUID = 6232572446375205093L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		String action = req.getParameter("action");
		if (action != null && "uploadUserHeadpic".equals(action.trim())) {
			try {
				uploadUserHeadpic(req, resp);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} 
		else {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("success", "0");
			resultMap.put("msg", "请上传action参数或核对action的参数值!");
			String result = JSON.toJSONString(resultMap);
			System.out.println("result=" + result);
			resp.getWriter().print(result);
		}
	}
	
	/**
	 * 文件用户头像上传
	 * @param req
	 * @param resp
	 * @throws FileUploadException 
	 * @throws IOException 
	 */
	private void uploadUserHeadpic(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		List<FileItem> fileList = fileUpload.parseRequest(req);
		String fileName = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", "0");
		for(FileItem fileItem :fileList){
			if (!fileItem.isFormField() && fileItem.getName() != null && !"".equals(fileItem.getName().trim())) {
				System.out.println(fileItem.getFieldName()+","+fileItem.getName());
				String realPath = req.getServletContext().getRealPath("/");
				String extendName = fileItem.getName().substring(fileItem.getName().lastIndexOf(".")+1);
				fileName = System.currentTimeMillis()+"."+extendName;
				OutputStream os = new FileOutputStream(new File(realPath+"/"+fileName));
				IOUtils.copy(fileItem.getInputStream(), os);
			}
		}
		if (fileName!=null && !"".equals(fileName)) {
			resultMap.put("success", "1");
			resultMap.put("fileName", fileName);
		}
		else {
			resultMap.put("msg", "请上传文件!");
		}
		String result = JSON.toJSONString(resultMap);
		System.out.println("result=" + result);
		resp.getWriter().print(result);
	}
	
}
