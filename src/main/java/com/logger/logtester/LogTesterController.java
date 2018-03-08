package com.logger.logtester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class LogTesterController {

    Logger logger = LoggerFactory.getLogger(LogTesterController.class);

    @ResponseBody
    @RequestMapping("/test")
    public String createInfoLog(){
        logger.debug("This is a debug message.");
        logger.info("This is an info message.");
        logger.warn("This is a warn message.");
        logger.error("This is an error message.");
        return "logs created!";
    }
}
