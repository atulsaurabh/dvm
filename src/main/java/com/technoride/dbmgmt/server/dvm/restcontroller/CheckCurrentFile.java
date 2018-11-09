package com.technoride.dbmgmt.server.dvm.restcontroller;

import com.technoride.dbmgmt.server.dvm.uriinfo.PublicURI;
import com.technoride.dbmgmt.server.dvm.util.CustomProperty;
import com.technoride.dbmgmt.server.dvm.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(PublicURI.CURRENT_FILE_CONTROLLER_URI)
public class CheckCurrentFile {

    @GetMapping
    public String sendCurrentFile()
    {
        return findCurrentFile();

    }
    public  String findCurrentFile()
    {
        String curfile=new FileUtil().findConfig().get(CustomProperty.CONFIG_FILE_NAME);
        return curfile;
    }
}
