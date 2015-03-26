package com.bj58.argo.zooviewer.controllers;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bj58.argo.ActionResult;
import com.bj58.argo.BeatContext;

public class JSONActionResult implements ActionResult {

    private static final Logger logger = Logger.getLogger(JSONActionResult.class);

    private String content;
    private String type;

    public JSONActionResult(String content) {
        super();
        this.content = content;
        this.type = "html";
    }

    public JSONActionResult(String content, String type) {
        super();
        this.content = content;
        this.type = type;
    }

    @Override
    public void render(BeatContext beat) {
        try {
            HttpServletResponse response = beat.getResponse();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/" + type + "; charset=utf-8");
            response.getWriter().print(content);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
