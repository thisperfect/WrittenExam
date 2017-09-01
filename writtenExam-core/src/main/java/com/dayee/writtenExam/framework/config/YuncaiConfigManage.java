/**
 * 
 */

package com.dayee.writtenExam.framework.config;

import com.dayee.writtenExam.framework.exception.YuncaiSystemException;
import com.dayee.writtenExam.framework.exception.YuncaiSystemExceptionCode;
import com.ideamoment.config.IdeaConfiguration;

/**
 * @author Chinakite
 */
public class YuncaiConfigManage {

    protected static IdeaConfiguration config                  = new IdeaConfiguration();

    private static final String        DEFAULT_CONFIG_FILENAME = "resume.properties";

    public synchronized static void initConfig(String configPath) {

        if (!config.isInited()) {
            config.initConfig(configPath);
        } else {
            throw new YuncaiSystemException(
                    YuncaiSystemExceptionCode.YUNCAI_CONFIG_INIT_DUPLICATE,
                    "Duplicate init config file");
        }
    }

    public synchronized static void initConfig() {

        if (!config.isInited()) {
            config.initConfig(DEFAULT_CONFIG_FILENAME);
        } else {
            throw new YuncaiSystemException(
                    YuncaiSystemExceptionCode.YUNCAI_CONFIG_INIT_DUPLICATE,
                    "Duplicate init config file");
        }
    }

}
