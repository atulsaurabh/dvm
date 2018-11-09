package com.technoride.dbmgmt.server.dvm.webcontroller;

import com.technoride.dbmgmt.server.dvm.uriinfo.PublicURI;
import com.technoride.dbmgmt.server.dvm.util.CustomProperty;
import com.technoride.dbmgmt.server.dvm.util.FileUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class CheckCurrentFile {

    @GetMapping
    public String sendCurrentFile()
    {
        return findCurrentFile();

    }

    public  String findCurrentFile()


    {
        String filename = "";
        try {
            String configFile = this.getClass().getResource(CustomProperty.CONFIG_DIR).toURI().getPath();
            File file = new File(configFile + CustomProperty.CURRENT_FILE);

            if (!file.exists()) {
                file.createNewFile();
            } else {

                Scanner scanner = new Scanner(new FileInputStream(file));
                while (scanner.hasNextLine()) {

                    filename = scanner.nextLine();
                }


            }

        }
        catch (URISyntaxException syn)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Unable To Configure MySQL Home");
        }
        catch (FileNotFoundException fnf) {
            fnf.printStackTrace();

        } catch (IOException io) {
            io.printStackTrace();
        }

        return filename;
 }
}

