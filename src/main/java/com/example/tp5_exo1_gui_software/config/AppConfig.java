package com.example.tp5_exo1_gui_software.config;

import java.awt.*;

// Un Singleton qui a les différents Constantes et Valeurs pour configurer l'application
public class AppConfig {
    private static AppConfig instance;

    public final int LOGIN_WINDOW_WIDTH = 460;
    public final int LOGIN_WINDOW_HEIGHT = 420;
    public final int WINDOW_WIDTH = 400;
    public final int WINDOW_HEIGHT = 300;
    public final int TITLE_SIZE = 24;
    public final int SMALL_FONT_SIZE = 14;
    public final int NORMAL_FONT_SIZE = 20;
    public final int TEXT_FIELD_SIZE = 20;
    public final int SMALL_PADDING = 10;
    public final int LARGE_PADDING = 40;
    public final Color BG_COLOR = Color.decode("0x2c3e50");
    public final Color TEXT_COLOR = Color.decode("0xecf0f1");
    public final Color BTN_COLOR = Color.decode("0x2ecc71");

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}