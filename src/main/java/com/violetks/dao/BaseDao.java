package com.violetks.dao;

import java.sql.*;

public class BaseDao {
    public String driverClassName = "com.mysql.jdbc.Driver";
    //MARK 后续改成主机的IP地址
    public String databaseURL = "jdbc:mysql://127.0.0.1:3306/sysdb?user=sysdb&password=zhz030106&useUnicode=true&characterEncoding=utf8";
    public Connection con = null;
    public PreparedStatement pstm = null;

    public BaseDao() {
        try {
            Class.forName(this.driverClassName);
            this.con = DriverManager.getConnection(this.databaseURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 关闭所有连接
    public void closeAll(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
