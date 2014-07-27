package com.aionemu.gameserver;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by xujian on 2014/7/13.
 */
public class TestAlive {
    @Test
    public void test() throws IOException {
        Socket so = new Socket("192.168.0.103",7777);
        System.out.println(so.isConnected());
        so.getOutputStream().write(new byte[5]);
        so.getOutputStream().flush();
    }
}
