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
    public  Map<String,String> findConfig()
    {
        String [] propertyName = getPropertyName();
        Map<String,String> properties = new HashMap<>();
        try
        {
            String path = this.getClass().getResource(CustomProperty.CONFIG_DIR).getPath();
            File configFile = new File(path+CustomProperty.CONFIG_FILE_NAME);
            if (!configFile.exists())
                configFile.createNewFile();

            Scanner scanner=new Scanner(new FileInputStream(configFile));
            String version = "";

            int i=0;
            while (scanner.hasNextLine()) {
                String pname = scanner.nextLine();
                String[] split = pname.split(":");
                properties.put(propertyName[i], split[1]);
                i++;
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


    private  String [] getPropertyName()
    {
        return new String[]{CustomProperty.CURRENT_FILE,CustomProperty.CURRENT_VERSION};
    }


    public void writePropertiesToFile(Map<String,String> map)
    {
        try {
            String path = this.getClass().getResource(CustomProperty.CONFIG_DIR + CustomProperty.CURRENT_FILE).toURI().getPath();
            File file=new File(path);
            PrintWriter writer=new PrintWriter(new FileOutputStream(file));
            map.forEach((key,value)->{
                String line=key+":"+value;
                writer.println(line);
                writer.flush();
            });


        }
        catch (URISyntaxException syn)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Unable To Configure MySQL Home");
        } catch (FileNotFoundException e) {
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


