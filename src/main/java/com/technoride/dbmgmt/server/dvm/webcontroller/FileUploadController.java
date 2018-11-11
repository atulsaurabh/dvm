package com.technoride.dbmgmt.server.dvm.webcontroller;


import com.technoride.dbmgmt.server.dvm.uriinfo.PublicURI;
import com.technoride.dbmgmt.server.dvm.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(PublicURI.FILE_UPLOAD)
public class FileUploadController
{
    @PostMapping
    public String upload(@RequestParam("mysqlupdate")MultipartFile file, Model model)
    {
        try {
            byte [] bytes = file.getBytes();
            FileUtil fileUtil = new FileUtil();
            Map<Long,String> map = fileUtil.findConfig();
            String name="";
            Long max=0l;
            if (!map.isEmpty())
            {
                Set<Long> keys = map.keySet();
                max=keys.stream().max((o1, o2) -> {
                    return (o1.intValue() - o2.intValue());
                }).get();
                name="mysqldump_"+(max+1)+".sql";
            }
            else
            {
                name="mysqldump_1.sql";
            }

            File uploadFile = new File(this.getClass().getResource("/config/").getPath()+name);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(uploadFile));
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.flush();
            map.put(max+1,name);
            fileUtil.writePropertiesToFile(map);
            model.addAttribute("message","file uploaded successfully");
        }
        catch (IOException io)
        {
            model.addAttribute("message","file not added successfully");
            io.printStackTrace();
        }

        return "home";
    }
}
