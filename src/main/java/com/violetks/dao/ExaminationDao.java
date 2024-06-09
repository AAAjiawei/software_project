package com.violetks.dao;

import com.violetks.entity.Examination;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExaminationDao extends BaseDao {

    // 向试卷添加题目
    public boolean addExamination(Examination examination) {
        try {
            String sql = "insert into tb_examination(e_id,e_name,q_list,add_time,t_phone) values(?,?,?,?,?)";
            this.pstm = this.con.prepareStatement(sql);
            this.pstm.setInt(1, examination.getEid());
            this.pstm.setString(2, examination.geteName());
            this.pstm.setString(3, examination.getqList());
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            this.pstm.setDate(4, currentDate);
            this.pstm.setString(5, examination.gettPhone());
            this.pstm.executeUpdate();

            sql = "insert into tb_exam_class(e_id,s_class) values(?,?)";
            this.pstm = this.con.prepareStatement(sql);
            this.pstm.setInt(1, examination.getEid());
            this.pstm.setString(2, examination.getsClass());
            this.pstm.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.pstm != null) {
                    this.pstm.close();
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    // 根据班级号获取作业列表
    public List<Examination> getAllExamination(String sClass) {
        List<Examination> list = new ArrayList<>();
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        try{
            String sql1 = "select e_id from tb_exam_class where s_class = ?";
            this.pstm = this.con.prepareStatement(sql1);
            this.pstm.setString(1,sClass);
            rs1 = this.pstm.executeQuery();
            while (rs1.next()){
                int eid = rs1.getInt("e_id");
                String sql2 = "select * from tb_examination where e_id = ?";
                this.pstm = this.con.prepareStatement(sql2);
                this.pstm.setInt(1, eid);
                rs2 = this.pstm.executeQuery();
                if(rs2.next()) {
                    Examination examination = new Examination();
                    examination.setEid(rs2.getInt(1));
                    examination.seteName(rs2.getString(2));
                    examination.setqList(rs2.getString(3));
                    examination.setAddTime(rs2.getDate(4));
                    examination.settPhone(rs2.getString(5));
                    list.add(examination);
                    System.out.println(examination);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(this.pstm,null);
        }
        return list;
    }


    // 获取作业试题总数
    public int getTotalCount(Examination examination){
        String str = examination.getqList();
        String[] qList = str.split(",");
        return qList.length;
    }

}
