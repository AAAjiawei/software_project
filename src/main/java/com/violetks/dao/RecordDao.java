package com.violetks.dao;

import com.violetks.entity.Examination;
import com.violetks.entity.Question;
import com.violetks.entity.Record;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecordDao extends BaseDao {
    // 添加或更新记录
    public boolean addExaminationRecord(Record record) {
        try {
            String sql = "insert into tb_record(s_id,e_id,q_id,submit_time,s_point,s_answer) values(?,?,?,?,?,?) " +
                    "on duplicate key update submit_time=VALUES(submit_time),s_point=VALUES(s_point),s_answer=VALUES(s_answer)";
            this.pstm = this.con.prepareStatement(sql);
            this.pstm.setInt(1, record.getSid());
            this.pstm.setInt(2, record.getEid());
            this.pstm.setInt(3, record.getQid());
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
            this.pstm.setDate(4, currentDate);
            this.pstm.setInt(5,record.getsPoint());
            this.pstm.setString(6, record.getsAnswer());
            int i = this.pstm.executeUpdate();
            if (i > 0) {
                return true;
            }
            return false;
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

    /**
     * 获取特定学生特定作业号已解决题数
     * questionSet.jsp
     *
     * @param eid : 作业号
     * @param sid  : 学生学号
     */
    public int getResolvedCount(int eid, int sid) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "select count(DISTINCT q_id) as sum from tb_record where s_id=" + sid + " and e_id=" + eid;
            stmt = this.con.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("sum");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(stmt, null);
        }
        return 0;
    }

    // 获取每道题完成次数
    // questionLevelSet.jsp
    public int getExResult(int sid, int qid) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = this.con.createStatement();
            rs = stmt.executeQuery("select count(*) as sum from tb_record where s_id=" + sid + " and q_id=" + qid );
            if (rs.next()) {
                return rs.getInt("sum");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(stmt, null);
        }
        return 0;
    }

    // 提交作业后计算分数
    public int getTotalPoint(int eid, int sid) {
        Statement stmt = null;
        ResultSet rs = null;
        int sScore = 0;
        try{
            String sql = "select r.s_answer,q.q_answer,q.t_score,r.q_id from tb_record r INNER JOIN tb_question q ON r.q_id = q.q_id WHERE s_id="+sid+" and e_id="+eid;
            stmt = this.con.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String sAnswer = rs.getString(1).replaceAll(" ", "");
                String tAnswer = rs.getString(2).replaceAll(" ", "");
                int tScore = rs.getInt(3);
                int qid = rs.getInt(4);
                if (sAnswer.equals(tAnswer)){
                    sql = "update tb_record set s_point="+tScore+" WHERE s_id="+sid+" and e_id="+eid+" and q_id="+qid;
                    sScore += tScore;
                    stmt.executeUpdate(sql);
                } else {
                    sql = "update tb_record set s_point="+0+" WHERE s_id="+sid+" and e_id="+eid+" and q_id="+qid;
                    stmt.executeUpdate(sql);
                }
            }
            sql = "insert into tb_stu_exam(s_id,e_id,totalpoint) values(?,?,?)";
            this.pstm = this.con.prepareStatement(sql);
            this.pstm.setInt(1, sid);
            this.pstm.setInt(2, eid);
            this.pstm.setInt(3, sScore);
            this.pstm.executeUpdate();
            return sScore;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeAll(stmt, rs);
            closeAll(pstm, null);
        }
        return sScore;
    }

    /**
     * 查询个人作业记录
     * exerciseSet.jsp
     *
     * @param sid
     * @return q_id, q_name, q_type, q_level, submit_time
     * 根据学号查询多个学生练习记录
     * studentTest.jsp
     */
    public List<Record> getExaminationResult(int sid,int eid) {
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList list = new ArrayList();

        ArrayList exResultList;
        try {
            String sql = "select r.s_id,r.e_id,r.q_id,r.submit_time,r.s_answer,r.s_point,q.q_name,q.q_type,q.q_level,q.q_content,q.q_keyword,q.t_score,q.q_answer,q.add_time " +
                    "from (select * from tb_record where s_id=" + sid + " and e_id=" + eid + " order by submit_time DESC) r " +
                    "INNER JOIN tb_question q ON r.q_id = q.q_id " +
                    "GROUP BY r.q_id";
            stmt = this.con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Record record = new Record();
                Question question = new Question();
                record.setSid(rs.getInt(1));
                record.setEid(rs.getInt(2));
                record.setQid(rs.getInt(3));
                record.setSubmitTime(rs.getDate(4));
                record.setsAnswer(rs.getString(5));
                record.setsPoint(rs.getInt(6));
                question.setQid(rs.getInt(3));
                question.setqName(rs.getString(7));
                question.setqType(rs.getInt(8));
                question.setqLevel(rs.getInt(9));
                question.setqContent(rs.getString(10));
                question.setqKeyword(rs.getString(11));
                question.settScore(rs.getInt(12));
                question.setqAnswer(rs.getString(13));
                question.setAddTime(rs.getDate(14));
                record.setQuestion(question);
                list.add(record);
            }
            exResultList = list;
            return exResultList;
        } catch (Exception e) {
            e.printStackTrace();
            exResultList = list;
        } finally {
            closeAll(stmt, null);
        }
        return exResultList;
    }
}
