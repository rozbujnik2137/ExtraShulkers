package me.vlob.shulkers.managers.impl;

import me.vlob.shulkers.Main;
import me.vlob.shulkers.managers.FileManager;

import java.io.File;

public class FileManagerImpl implements FileManager {

    private final File mainDir,configFile;

    private final Main main;

    public FileManagerImpl(Main main) {
        this.main = main;
        this.mainDir = this.main.getDataFolder();
        this.configFile = new File(this.mainDir, "config.yml");
    }

    @Override
    public void checkFiles() {
        if(!this.mainDir.exists()) this.mainDir.mkdir();
        if(!this.configFile.exists()) this.main.saveDefaultConfig();
    }
}
