package com.bj58.argo.zooviewer.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

import com.bj58.argo.BeatContext;
import com.bj58.argo.annotations.Path;
import com.bj58.argo.controller.AbstractController;
import com.bj58.argo.zooviewer.util.ZkPool;

public class ZKController extends AbstractController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Path("/search")
    public JSONActionResult search() {
        JSONObject json = new JSONObject();
        BeatContext beat = beat();
        String path = beat.getRequest().getParameter("path");
        try {
            CuratorFramework client = ZkPool.getClient();
            Stat stat = new Stat();
            byte[] bs = client.getData().storingStatIn(stat).forPath(path);
            JSONObject obj = new JSONObject();
            obj.element("id", 1);
            obj.element("pid", 0);
            obj.element("name", path);
            obj.element("hasClick", false);
            obj.element("isParent", false);
            
            //
            json.element("data", bs == null || bs.length < 1 ? "NA" : new String(bs));
            json.element("obj", obj);
            json.element("stat", toStat(stat));
            json.element("succ", 1);
        } catch (Exception e) {
            e.printStackTrace();
            json.element("succ", 0);
        }
        System.out.println(json.toString());
        return new JSONActionResult(json.toString(), "json");
    }

    @Path("/getChildren")
    public JSONActionResult getChildren() {
        JSONObject json = new JSONObject();
        BeatContext beat = beat();
        String ppath = beat.getRequest().getParameter("ppath");
        String pid = beat.getRequest().getParameter("pid");
        try {
            CuratorFramework client = ZkPool.getClient();
            List<String> children = client.getChildren().forPath(ppath);
            JSONArray array = new JSONArray();
            for (int i = 1; i < children.size(); i++) {
                String str = children.get(i);
                JSONObject obj = new JSONObject();
                obj.element("id", pid + "" + i);
                obj.element("pid", pid);
                String name;
                if (ppath.endsWith("/")) {
                    name = ppath + str;
                } else {
                    name = ppath + "/" + str;
                }
                obj.element("name", name);
                obj.element("hasClick", false);
                obj.element("isParent", false);
                array.add(obj);
            }

            Stat stat = new Stat();
            byte[] bs = client.getData().storingStatIn(stat).forPath(ppath);

            json.element("childs", array.toArray());
            json.element("data", bs == null || bs.length < 1 ? "NA" : new String(bs));
            json.element("stat", toStat(stat));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONActionResult(json.toString(), "json");
    }

    public String toStat(Stat stat) {
        StringBuilder sb = new StringBuilder();
        sb.append("czxid").append("：：&nbsp;&nbsp;").append(stat.getCzxid()).append("</br>");
        sb.append("mzxid").append("：：&nbsp;&nbsp;").append(stat.getMzxid()).append("</br>");
        sb.append("pzxid").append("：：&nbsp;&nbsp;").append(stat.getPzxid()).append("</br>");
        sb.append("numChildren").append("：：&nbsp;&nbsp;").append(stat.getNumChildren()).append("</br>");
        sb.append("version").append("：：&nbsp;&nbsp;").append(stat.getVersion()).append("</br>");
        sb.append("cversion").append("：：&nbsp;&nbsp;").append(stat.getCversion()).append("</br>");
        sb.append("aversion").append("：：&nbsp;&nbsp;").append(stat.getAversion()).append("</br>");
        sb.append("ctime").append("：：&nbsp;&nbsp;").append(sdf.format(new Date(stat.getCtime()))).append("</br>");
        sb.append("mtime").append("：：&nbsp;&nbsp;").append(sdf.format(new Date(stat.getMtime()))).append("</br>");
        return sb.toString();
    }

    @Path("/modifyNodeData")
    public JSONActionResult modifyNodeData() {
        JSONObject json = new JSONObject();
        BeatContext beat = beat();
        String path = beat.getRequest().getParameter("path");
        String id = beat.getRequest().getParameter("id");
        String value = beat.getRequest().getParameter("value");
        if ("1".equals(id)) {
            path = "/";
        }
        if (value == null || value.length() < 1) {
            return null;
        }

        try {
            CuratorFramework client = ZkPool.getClient();
            client.setData().forPath(path, value.getBytes());

            Stat stat = new Stat();
            byte[] bs = client.getData().storingStatIn(stat).forPath(path);

            json.element("data", bs == null ? "NA" : new String(bs));
            json.element("issuccess", true);
            json.element("stat", stat.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.element("issuccess", false);
        }
        return new JSONActionResult(json.toString(), "json");
    }

    @Path("/telnet")
    public JSONActionResult telnetFourCMD() {
        JSONObject json = new JSONObject();
        BeatContext beat = beat();
        String path = beat.getRequest().getParameter("path");
        String id = beat.getRequest().getParameter("id");
        String value = beat.getRequest().getParameter("value");
        if ("1".equals(id)) {
            path = "/";
        }
        if (value == null || value.length() < 1) {
            return null;
        }

        try {
            CuratorFramework client = ZkPool.getClient();
            client.setData().forPath(path, value.getBytes());

            Stat stat = new Stat();
            byte[] bs = client.getData().storingStatIn(stat).forPath(path);

            json.element("data", bs == null ? "NA" : new String(bs));
            json.element("issuccess", true);
            json.element("stat", stat.toString());
        } catch (Exception e) {
            e.printStackTrace();
            json.element("issuccess", false);
        }
        return new JSONActionResult(json.toString(), "json");
    }
}
