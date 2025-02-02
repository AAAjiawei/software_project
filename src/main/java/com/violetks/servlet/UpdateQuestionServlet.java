package com.violetks.servlet;

import com.violetks.entity.Question;
import com.violetks.fileUtil.FileInput;
import com.violetks.fileUtil.StrSplit;
import com.violetks.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/UpdateQuestionServlet")
public class UpdateQuestionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        FileInput fi = new FileInput();
        StrSplit sp = new StrSplit();

        Question question = new Question();
        int qid = Integer.parseInt(request.getParameter("qid"));
        int qType = Integer.parseInt(request.getParameter("qType"));
        int qLevel = Integer.parseInt(request.getParameter("qLevel"));
        String qName = request.getParameter("qName");
        String qKeyword = request.getParameter("qKeyword");
        int tScore = Integer.parseInt(request.getParameter("tScore"));

        question.setQid(qid);
        question.setqType(qType);
        question.setqLevel(qLevel);
        question.setqName(qName);
        question.setqKeyword(qKeyword);
        question.settScore(tScore);

        QuestionService questionService = new QuestionService();

        if (qType==0){  // 上传单选题
            String[] qContent = request.getParameterValues("qContent"); // 获取设置的选项内容（有四个），插入数据库
            String qAnswer = request.getParameter("qAnswer"); // 获取设置的正确答案，插入数据库
            // 将数组转为字符串，替换逗号为竖线，并将多余的空格替换为单个空格
            String qContentString = Arrays.toString(qContent)
                    .replaceAll(",\\s*", "|")   // 将逗号和紧随的空格替换为竖线
                    .replaceAll("\\s+", " ");   // 将多个空格替换为一个空格
            question.setqContent(qContentString); // 数组转为字符串
            question.setqAnswer(qAnswer);

            if (questionService.updateQuestion(qid,question)){
                request.setAttribute("msg", "单选题修改成功！");
                request.getRequestDispatcher("/teacher/updateSingleChoice.jsp").forward(request, response);
            }else {
                request.setAttribute("msg", "单选题修改失败！");
                request.getRequestDispatcher("/teacher/updateSingleChoice.jsp").forward(request, response);
            }
        }else if (qType==2){  // 上传填空题
            String qContent = request.getParameter("qContent");
            String qAnswer = request.getParameter("qAnswer");
            question.setqContent(qContent);
            question.setqAnswer(qAnswer);

            if (questionService.updateQuestion(qid,question)){
                request.setAttribute("msg", "填空题修改成功！");
                request.getRequestDispatcher("/teacher/updateFillBlank.jsp").forward(request, response);
            }else {
                request.setAttribute("msg", "填空题修改失败！");
                request.getRequestDispatcher("/teacher/updateFillBlank.jsp").forward(request, response);
            }
        }else if (qType==4){
            // 上传编程题
            String qContent = request.getParameter("qContent"); // 编程题qContent只有一个
            question.setqContent(qContent);
            String qInput = request.getParameter("qInput");// 题目是否需要输入数据，yes/no
            // 试题已写入数据库
            if (questionService.updateQuestion(qid,question)) {

                // 生成文件夹
                if (fi.createDir(qid)) {
                    // qInput：是否需要控制台输入数据
                    if (qInput.equals("yes")) {
                        String in[] = request.getParameterValues("in");
                        String answer[] = request.getParameterValues("answer");
                        for (int i = 0; i < in.length; i++) {
                            // 生成测试输入文件
                            fi.createFile(qid, "input" + qid + "0" + (i + 1), sp.strSplit(in[i]));
                            // 生成测试答案文件
                            fi.createFile(qid, "answer" + qid + "0" + (i + 1), sp.strSplit(answer[i]));
                        }
                        request.setAttribute("msg", "编程题修改成功！");
                        request.getRequestDispatcher("/teacher/updateProgram.jsp").forward(request, response);
                    } else {
                        String answer = request.getParameter("a");
                        String example = request.getParameter("e");
                        // 生成测试答案文件
                        fi.createFile(qid, "answer" + qid + "01", sp.strSplit(answer));
                        // 生成测试样例文件
                        fi.createFile(qid, "example" + qid + "01", sp.strSplit(example));
                        request.setAttribute("msg", "编程题修改成功！");
                        request.getRequestDispatcher("/teacher/updateProgram.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("msg", "编程题修改失败！");
                    request.getRequestDispatcher("/teacher/updateProgram.jsp").forward(request, response);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
