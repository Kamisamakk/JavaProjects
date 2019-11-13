package org.jbit.news.servlet;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jbit.news.mapper.OrganizationMapper;
import org.jbit.news.entity.Organization;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/organizationServlet")
public class OrganizationServlet extends HttpServlet {
    InputStream inputStream;
    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;
    OrganizationMapper organizationMapper=null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        String configFileName ="mybatis.xml";
        //读取配置文件
        inputStream= Resources.getResourceAsStream(configFileName);
        //会话工厂
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        //创建会话
        sqlSession=sqlSessionFactory.openSession();
        if(action!=null&&"list".equals(action))
        {
            GetList(req,resp);
        }
    }

    private void GetList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        organizationMapper=sqlSession.getMapper(OrganizationMapper.class);
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String orgName=req.getParameter("orgName");
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        Map<String,Object> resultmap=new HashMap<String,Object>();
        resultmap.put("success","0");
        if (currentPage == null || "".equals(currentPage.trim())) {
            currentPage = "0";
        }
        if (pageSize == null || "".equals(pageSize.trim())) {
            pageSize = "5";
        }
        if(userId!=null&&!"".equals(userId)&&token!=null&&!"".equals(token))
        {
            Map<String,Object>searchMap=new HashMap<String, Object>();
            searchMap.put("currentPage",currentPage);
            searchMap.put("pageSize",pageSize);
            searchMap.put("orgName",orgName);
            List<Organization> organizationList= organizationMapper.FindOrganizationAllList(searchMap);
            sqlSession.close();
            resultmap.put("success","1");
            resultmap.put("currentPage",currentPage);
            resultmap.put("organizationList",organizationList);
        }
        String result= JSON.toJSONString(resultmap);
        resp.getWriter().print(result);
    }


}
