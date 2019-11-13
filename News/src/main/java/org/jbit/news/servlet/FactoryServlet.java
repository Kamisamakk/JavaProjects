package org.jbit.news.servlet;


import com.alibaba.fastjson.JSON;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jbit.news.mapper.FactoryMapper;
import org.jbit.news.entity.Factory;

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

@WebServlet("/api/factoryServlet")
public class FactoryServlet extends HttpServlet {
    InputStream inputStream;
    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;
    FactoryMapper factoryMapper=null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String configFileName ="mybatis.xml";
        inputStream= Resources.getResourceAsStream(configFileName);
        sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession=sqlSessionFactory.openSession();
        factoryMapper=sqlSession.getMapper(FactoryMapper.class);
        String action=req.getParameter("action");
        if(action!=null&&"list".equals(action))
        {
            GetList(req,resp);
        }else if(action!=null&&"del".equals(action))
        {
            Del(req,resp);
        }else if(action!=null&&"add".equals(action))
        {
            Add(req,resp);
        }else if(action!=null&&"validFactoryName".equals(action))
        {
            validFactoryName(req,resp);
        }else if(action!=null&&"update".equals(action))
        {
            Update(req,resp);
        }
    }

    private void Update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String factory_name=req.getParameter("factory_nameup");
        String factory_web=req.getParameter("factory_webup");
        String contacts=req.getParameter("contactsup");
        String factory_id=req.getParameter("factory_idup");
        String contact_number=req.getParameter("contact_numberup");
        String factory_address=req.getParameter("factory_addressup");
        String summary=req.getParameter("summaryup");
        String operator=userId;
        String sort_id=req.getParameter("sort_idup");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success","0");
        if(userId!=null&&!"".equals(userId)&&token!=null&&!"".equals(token))
        {
            Factory factory=new Factory();
            factory.setFactory_id(Integer.valueOf(factory_id));
            factory.setFactory_name(factory_name);
            factory.setContact_number(contact_number);
            factory.setContacts(contacts);
            factory.setFactory_address(factory_address);
            factory.setFactory_web(factory_web);
            factory.setSummary(summary);
            factory.setOperator(operator);
            if(sort_id!=null&&!"".equals(sort_id))
                factory.setSort_id(Integer.valueOf(sort_id));
            int count= factoryMapper.UpdateFactory(factory);
            sqlSession.commit();
            if(count>0)
            {
                resultMap.put("success","1");
            }
            sqlSession.close();
        }
        String result= JSON.toJSONString(resultMap);
        resp.getWriter().print(result);
    }

    private void validFactoryName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String factoryName=req.getParameter("factory_name");
        boolean result=false;
        if(userId!=null&&!"".equals(userId)&&token!=null&&!"".equals(token))
        {
            Map<String,Object>searchMap=new HashMap<String, Object>();
            if(factoryName!=null&&!"".equals(factoryName))
                searchMap.put("factory_name",factoryName);
            int count = factoryMapper.FindFactoryCount(searchMap);// 总记录数
            sqlSession.close();
            if(count==0)
            {
               result=true;
            }
        }
        resp.getWriter().print(result);
    }

    private void Add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String factory_name=req.getParameter("factory_name");
        String factory_web=req.getParameter("factory_web");
        String contacts=req.getParameter("contacts");
        String contact_number=req.getParameter("contact_number");
        String factory_address=req.getParameter("factory_address");
        String summary=req.getParameter("summary");
        String sort_id=req.getParameter("sort_id");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success","0");
        if(userId!=null&&!"".equals(userId)&&token!=null&&!"".equals(token))
        {
            Factory factory=new Factory();
            factory.setFactory_name(factory_name);
            factory.setContact_number(contact_number);
            factory.setContacts(contacts);
            factory.setFactory_address(factory_address);
            factory.setFactory_web(factory_web);
            factory.setSummary(summary);
            if(sort_id!=null&&!"".equals(sort_id))
                factory.setSort_id(Integer.valueOf(sort_id));
            int count= factoryMapper.AddFactory(factory);
            sqlSession.commit();
            if(count>0)
            {
                resultMap.put("success","1");
            }
            sqlSession.close();
        }
        String result= JSON.toJSONString(resultMap);
        resp.getWriter().print(result);
    }

    private void Del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String factoryId=req.getParameter("factoryId");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success","0");
        if(userId!=null&&!"".equals(userId)&&token!=null&&!"".equals(token)
        &&factoryId!=null&&!"".equals(factoryId))
        {
            Map<String,Object>searchMap=new HashMap<String, Object>();
            searchMap.put("factoryId",factoryId);
            int count= factoryMapper.DelFactory(Integer.valueOf(factoryId));
            sqlSession.commit();
            if(count>0)
            {
                resultMap.put("success","1");
            }
            sqlSession.close();
        }
        String result= JSON.toJSONString(resultMap);
        resp.getWriter().print(result);
    }

    private void GetList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId=req.getHeader("userId");
        String token=req.getHeader("token");
        String factoryName=req.getParameter("factoryName");
        String factoryId=req.getParameter("factory_id");
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");
        Map<String,Object> resultMap=new HashMap<String,Object>();
        resultMap.put("success","0");
        int totalCount = 0;
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
            if(factoryName!=null&&!"".equals(factoryName))
                searchMap.put("factory_name",factoryName);
            if(factoryId!=null&&!"".equals(factoryId))
                searchMap.put("factory_id",factoryId);
            totalCount = factoryMapper.FindFactoryCount(searchMap);// 总记录数
            List<Factory> factoryList= factoryMapper.FindFactoryAllList(searchMap);
            sqlSession.close();
            resultMap.put("success","1");
            resultMap.put("currentPage",currentPage);
            resultMap.put("pageTotal", totalCount);
            resultMap.put("factoryList",factoryList);
        }
        String result= JSON.toJSONString(resultMap);
        resp.getWriter().print(result);
    }
}
