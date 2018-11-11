package com.technoride.dbmgmt.server.dvm.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil
{
    public  Map<Long,String> findConfig()
    {
        Map<Long,String> properties = new HashMap<>();
        try
        {
            String path = this.getClass().getResource(CustomProperty.CONFIG_DIR).getPath();
            File configFile = new File(path+CustomProperty.CONFIG_FILE_NAME);
            if (!configFile.exists())
                configFile.createNewFile();

            Scanner scanner=new Scanner(new FileInputStream(configFile));
            String version = "";
            while (scanner.hasNextLine()) {
                String pname = scanner.nextLine();
                String[] split = pname.split(":");
                properties.put(Long.parseLong(split[0]), split[1]);
            }
            /* Write map properties to dvmserver.cfg file*/
            //writePropertyNameToFile(properties);
        }
        catch (FileNotFoundException fnf)
        {
          fnf.printStackTrace();
        }
        catch (IOException io)
        {
           io.printStackTrace();
        }
        return properties;
    }
    public void writePropertiesToFile(Map<Long,String> map)
    {
        try {
            String path = this.getClass().getResource(CustomProperty.CONFIG_DIR+"/dvmserver.cfg").getPath();
            File file=new File(path);
            PrintWriter writer=new PrintWriter(new FileOutputStream(file));
            map.forEach((key,value)->{
                String line=key+":"+value;
                writer.println(line);
                writer.flush();
            });


        }
         catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public Resource loadFileAsResource(String fileName) {
        try {
               URI fileUri= this.getClass().getResource(CustomProperty.CONFIG_DIR+"/"+fileName).toURI();
            Resource resource = new UrlResource(fileUri);
            if(resource.exists()) {
                return resource;
            }
        }
        catch (URISyntaxException urisyn)
        {
            urisyn.printStackTrace();
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}


