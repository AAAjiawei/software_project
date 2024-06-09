package com.violetks.servlet;

import com.violetks.entity.Examination;
import com.violetks.service.ExaminationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ReleaseExaminationServlet")
public class ReleaseExaminationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int eid = Integer.parseInt(request.getParameter("e_id"));
            String eName = request.getParameter("e_name");
            String tPhone = request.getParameter("t_phone");
            String sClass = request.getParameter("s_class");
            String qList = request.getParameter("q_list");


            if (qList == null) {
                response.getWriter().write("Invalid input");
                return;
            }

            Examination examination = new Examination();
            examination.setEid(eid);
            examination.seteName(eName);
            examination.settPhone(tPhone);
            examination.setqList(qList);
            examination.setsClass(sClass);


            ExaminationService examinationService = new ExaminationService();
            boolean success = examinationService.releaseExamination(examination);
            if (success) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("fail");
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error: " + e.getMessage());
        }
    }
}
