package com.technoride.dbmgmt.server.dvm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
}