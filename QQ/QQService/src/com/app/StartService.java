package com.app;

import com.config.Config;
import com.server.Server;

public class StartService {
    public static void main(String[] args) {
        Config.init();
        new Server();
    }
}
