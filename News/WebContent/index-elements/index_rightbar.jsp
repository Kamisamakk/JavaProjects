<%@ page import="org.jbit.news.dao.AdvertisementDao" %>
<%@ page import="org.jbit.news.dao.impl.AdvertisementDaoImpl" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.jbit.news.entity.Advertisement" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    AdvertisementDao advertisementDao = new AdvertisementDaoImpl();
    Map<String, String> searchMap = new HashMap<String, String>();
    String str = "1";
    searchMap.put("check_states", str);
    Advertisement advertisement = advertisementDao.getAdvertisements(searchMap);
    String path = null;
    String adName = null;
    String adId = null;
    if (advertisement.getNpicpath() != null) {
        path = advertisement.getNpicpath();
        adName = advertisement.getNtitle();
        adId = String.valueOf(advertisement.getNid());
    } else {
        path = "images/Picture1.jpg";
        adName = "广告招租";
    }
    /*Date time=new Date();
    String nowTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time);
    System.out.println("nowTime = " + nowTime );
    String beginTime=advertisement.getNbegindate();
    String endTime=advertisement.getNenddate();
    String[] beginTimetime=beginTime.split(" ");
    String[] endTimetime=endTime.split(" ");
    */
%>
<div class="picnews">
    <ul>
        <li>
            <a
                    <%
                        if(advertisement.getNpicpath() != null){
                    %>
                    href="newspages/advertisement_Info.jsp?adId=<%=adId%>"
                    <%
                        }
                        else {
                    %>
                    href="#"
                    <%
                        }
                    %>
            ><img src="<%=path%>" width="249" alt="adName"/>
            </a>
            <a href="#"><%=adName%>
            </a>
        </li>
        <%-- <li> <a href="#"><img src="images/Picture1.jpg" width="249" alt="" /> </a><a href="#">幻想中穿越时空2</a> </li> --%>
        <li><a href="#"><img src="images/Picture2.jpg" width="249" alt=""/> </a><a href="#">国庆多变的发型</a></li>
        <li><a href="#"><img src="images/Picture3.jpg" width="249" alt=""/> </a><a href="#">新技术照亮都市</a></li>
        <li><a href="#"><img src="images/Picture4.jpg" width="249" alt=""/> </a><a href="#">群星闪耀红地毯</a></li>
    </ul>
</div>