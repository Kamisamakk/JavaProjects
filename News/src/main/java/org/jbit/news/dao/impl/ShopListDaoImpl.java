package org.jbit.news.dao.impl;

import org.jbit.news.dao.BaseDao;
import org.jbit.news.dao.ShopListDao;
import org.jbit.news.entity.ShopList;
import org.jbit.news.entity.ShopProduct;
import org.jbit.news.entity.ShoppingCartProduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopListDaoImpl extends BaseDao implements ShopListDao {
    @Override
    public List<Map<String, Object>> findAllList(Map<String, Object> searchMap) {
        String sql = "select t.no,p.username,t.money,t.states,p.number,"
                + "DATE_FORMAT(p.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate"
                + " from shop_list t left join shop_userlist p "
                +"on t.no=p.no"
                + " where 1=1 ";
        //订单状态模糊查询
        if (searchMap != null && searchMap.get("listStates") != null) {
            sql += " and  t.states like '%" + searchMap.get("listStates") + "%'";
        }
        //订单号精确查询
        if(searchMap != null && searchMap.get("list_no") != null){
            sql += " and  t.no ='" + searchMap.get("list_no") + "'";
        }
        if (searchMap != null && searchMap.get("pageSize") != null
                && searchMap.get("currentPage") != null) {
            String currentPage = (String) searchMap.get("currentPage");//0,1*5
            String pageSize = (String) searchMap.get("pageSize");//5,
            int indexCount = Integer.parseInt(currentPage) * Integer.parseInt(pageSize);
            sql += " limit " + indexCount + "," + pageSize;
        }
        System.out.println("sql"+sql);
        ResultSet rs = querySql(sql);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            while(rs.next()) {
                Map<String, Object> map = new HashMap<String,Object>();
                map.put("listNo",rs.getInt("no"));
                map.put("userName",rs.getString("username"));
                map.put("listMoney",rs.getString("money"));
                map.put("listStates",rs.getString("states"));
                map.put("userNumber",rs.getString("number"));
                map.put("operateTime",rs.getString("oprDate"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int findListCount(Map<String, Object> searchMap) {
        String sql = "select count(no) from shop_list t where 1=1 ";
        if (searchMap != null && searchMap.get("nid") != null) {
            sql += " and  t.nid='" + searchMap.get("nid") + "'";
        }
        if(searchMap!=null&&searchMap.get("list_no")!=null)
        {
            sql += " and  t.no='" + searchMap.get("list_no") + "'";
        }
        ResultSet rs = querySql(sql);
        int count = 0;
        try {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int AddProductList(ShopProduct shopProduct) {
        String sql="insert into shop_product(no,name,count,type,brand,price,total,summary,picpath0,picpath1,picpath2,picpath3,picpath4,picpath5,operate.time)"
                    +"values(?,?,?,?,?,?,111,?,?,?,?,?,?,?,now())";
        Object[] params=new Object[]{shopProduct.getNo(),shopProduct.getName(),shopProduct.getCount(),
        shopProduct.getType(),shopProduct.getBrand(),shopProduct.getPrice(),shopProduct.getTotal(),
                shopProduct.getPicpath0(),shopProduct.getPicpath1(),shopProduct.getPicpath2(),shopProduct.getPicpath2(),shopProduct.getPicpath3(),
                shopProduct.getPicpath5()
        };
        int result=exceuteUpdate(sql,params);
        return result;
    }

    @Override
    public int AddMainList(ShopList shopList) {
        String sql="insert into shop_list(no,states,money,userid,createtime,operator)"
                +"values(?,0,?,?,now(),?)";
        Object[] params=new Object[]{shopList.getNo(),shopList.getMoney(),shopList.getUserid(),shopList.getOperator()};
        int result=exceuteUpdate(sql,params);
        return result;
    }

    @Override
    public int DeleteList(String nid) {
        return 0;
    }

    @Override
    public Map<String,Object> GetList(String nid) {
        String sql = "select t.nid,t.no,t.states,t.money,t.userid,t.comment,t.checkcomment,p.name,p.count,t.removemoney,"
                +"p.price,p.total,p.summary,p.type,p.brand,"
                +"p.picpath0,p.picpath1,p.picpath2,p.picpath3,p.picpath4,p.picpath5,"
                + "t.operator,DATE_FORMAT(p.operate_time,'%Y-%m-%d %h:%i:%s') as oprDate,"
                + "DATE_FORMAT(t.checktime,'%Y-%m-%d %h:%i:%s') as ckDate,"
                + "DATE_FORMAT(t.createtime,'%Y-%m-%d %h:%i:%s') as cDate,"
                + "DATE_FORMAT(t.mdftime,'%Y-%m-%d %h:%i:%s') as mDate,"
                + "DATE_FORMAT(t.removetime,'%Y-%m-%d %h:%i:%s') as rDate,"
                + "c.username,c.number,c.address"
                + " from shop_list t left join shop_product p "
                + "on t.no=p.no left join shop_userlist c "
                +"on t.no=c.no"
                + " where 1=1 ";
        if (nid != null) {
            sql += " and t.nid = '" +nid+"'";
        }
        ResultSet rs = querySql(sql);
        Map<String,Object> shopList = new HashMap<>();
        try {
            if (rs.next()) {
                //shopList.put("listNid",rs.getInt("nid"));
                shopList.put("listNo",rs.getInt("no"));
                shopList.put("listStates",rs.getInt("states"));
                shopList.put("listMoney",rs.getInt("money"));
                shopList.put("listUserId",rs.getInt("userid"));
                shopList.put("listCheckComment",rs.getString("checkcomment"));
                shopList.put("listName",rs.getString("name"));
                shopList.put("listCount",rs.getInt("count"));
                shopList.put("listPrice",rs.getInt("price"));
                shopList.put("listTotal",rs.getInt("total"));
                shopList.put("listSummary",rs.getString("summary"));
                shopList.put("listType",rs.getString("type"));
                shopList.put("listBrand",rs.getString("brand"));
                shopList.put("listPicPath0",rs.getString("picpath0"));
                shopList.put("listPicPath1",rs.getString("picpath1"));
                shopList.put("listPicPath2",rs.getString("picpath2"));
                shopList.put("listPicPath3",rs.getString("picpath3"));
                shopList.put("listPicPath4",rs.getString("picpath4"));
                shopList.put("listPicPath5",rs.getString("picpath5"));
                shopList.put("listOperator",rs.getString("operator"));
                shopList.put("listOprDate",rs.getString("oprDate"));
                shopList.put("listCDate",rs.getString("cDate"));
                shopList.put("listMDate",rs.getString("mDate"));
                shopList.put("listReDate",rs.getString("rDate"));
                shopList.put("listCkDate",rs.getString("ckDate"));
                shopList.put("listReMoney",rs.getInt("removemoney"));
                shopList.put("listUserName",rs.getString("username"));
                shopList.put("listNumber",rs.getInt("number"));
                shopList.put("listAddress",rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    @Override
    public int UpdateList(ShopList shopList) {
        String sql = "update shop_list set states = ?,"
                + "checkcomment=?,"
                + "operator=?,checktime=now()"
                + " where nid =?";
        Object[] params = new Object[]{shopList.getStates(),shopList.getCheckcomment(),shopList.getOperator(),shopList.getNid()};
        int result = exceuteUpdate(sql, params);
        return result;
    }


}
