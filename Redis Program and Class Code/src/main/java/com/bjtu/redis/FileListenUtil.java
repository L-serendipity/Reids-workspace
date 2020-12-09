package com.bjtu.redis;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileListenUtil extends FileAlterationListenerAdaptor {
    private Logger logger = LoggerFactory.getLogger(FileListenUtil.class);

    @Override
    public void onFileChange(File file) {

        System.out.println("json文件已重新配置");
        Main.jsonFileConfig();
    }
}
