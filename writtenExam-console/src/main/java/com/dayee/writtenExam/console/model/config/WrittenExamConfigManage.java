package com.dayee.writtenExam.console.model.config;

import com.dayee.writtenExam.console.model.constants.exception.ExamSystemExceptionCode;
import com.dayee.writtenExam.console.model.exception.ExamSystemException;
import com.ideamoment.config.IdeaConfiguration;

/**
 * Created by Administrator on 2017/8/7.
 */
public class WrittenExamConfigManage {

    protected static IdeaConfiguration config                  = new IdeaConfiguration();

    private static final String        DEFAULT_CONFIG_FILENAME = "written.properties";

    public synchronized static void initConfig(String configPath) {

        if (!config.isInited()) {
            config.initConfig(configPath);
        } else {
            throw new ExamSystemException(
                    ExamSystemExceptionCode.REPORT_CONFIG_INIT_DUPLICATE,
                    "Duplicate init config file");
        }
    }

    public synchronized static void initConfig() {

        if (!config.isInited()) {
            config.initConfig(DEFAULT_CONFIG_FILENAME);
        } else {
            throw new ExamSystemException(
                    ExamSystemExceptionCode.REPORT_CONFIG_INIT_DUPLICATE,
                    "Duplicate init config file");
        }
    }
}
