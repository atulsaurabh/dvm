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
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

@RestController
@RequestMapping(PublicURI.VERSION_CONTROLLER_URI)
public class VersionController {

    @GetMapping
    public long sendCurrentVersion()
    {
           return findCurrentVersion();
    }
    private long findCurrentVersion()
    {
        Map<Long,String> map = new FileUtil().findConfig();
        Set<Long> keys = map.keySet();
        long max=keys.stream().max((o1, o2) -> {
            int x = o1.intValue() - o2.intValue();
            return x;
        }).get();

        return max;
    }
}
