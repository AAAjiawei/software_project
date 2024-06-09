package com.violetks.service;

import com.violetks.dao.ExaminationDao;
import com.violetks.entity.Examination;

public class ExaminationService {
    ExaminationDao examinationDao = new ExaminationDao();
    //  发布作业
    public boolean releaseExamination(Examination examination) {
        return examinationDao.addExamination(examination);
    }
}
