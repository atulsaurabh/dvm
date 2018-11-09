package com.technoride.dbmgmt.server.dvm.restcontroller;


import com.technoride.dbmgmt.server.dvm.uriinfo.PublicURI;
import com.technoride.dbmgmt.server.dvm.util.CustomProperty;
import com.technoride.dbmgmt.server.dvm.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@RestController
@RequestMapping(PublicURI.VERSION_CONTROLLER_URI)
public class VersionController {

    @GetMapping
    public int sendCurrentVersion()
    {
           return findCurrentVersion();
    }


    private int findCurrentVersion()
    {
        String v = new FileUtil().findConfig().get(CustomProperty.CURRENT_VERSION);
        int currentVersion = Integer.parseInt(v);
        return currentVersion;
    }
}
