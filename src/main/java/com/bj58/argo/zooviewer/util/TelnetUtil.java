package com.bj58.argo.zooviewer.util;

import org.apache.commons.net.telnet.TelnetClient;

public class TelnetUtil {

    private TelnetClient client = new TelnetClient();

    public void doTelnet() {
        try {
            client.connect("192.168.119.10", 2181);
            
        } catch (Exception e) {
        }
    }

}
