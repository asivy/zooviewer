package com.bj58.argo.zooviewer.controllers;

import java.text.SimpleDateFormat;

import com.bj58.argo.ActionResult;
import com.bj58.argo.annotations.Path;
import com.bj58.argo.controller.AbstractController;

public class ConsoleController extends AbstractController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init() {
        super.init();
        System.out.println("controller init ...");
    }

    @Path("/index")
    public ActionResult checkVersion() {
        return view("index");
    }

    @Path("/znode")
    public ActionResult znode() {
        return view("znode");
    }

    @Path("/fcmd")
    public ActionResult fourCmd() {
        return view("fcmd");
    }

    @Path("/cluster")
    public ActionResult cluster() {
        return view("cluster");
    }

    @Path("/auth")
    public ActionResult auth() {
        return view("auth");
    }

}
