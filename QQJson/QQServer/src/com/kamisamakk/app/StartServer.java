package com.kamisamakk.app;

import com.kamisamakk.config.Config;
import com.kamisamakk.server.Server;

import java.io.IOException;

public class StartServer {
    public static void main(String[] args) throws IOException {
        new Config().init();
        new Server();
    }
}
