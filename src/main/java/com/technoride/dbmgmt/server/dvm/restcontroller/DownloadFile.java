package com.technoride.dbmgmt.server.dvm.restcontroller;

import com.technoride.dbmgmt.server.dvm.uriinfo.PublicURI;
import com.technoride.dbmgmt.server.dvm.util.CustomProperty;
import com.technoride.dbmgmt.server.dvm.util.FileUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;


@RestController
@RequestMapping(value=PublicURI.CURRENT_FILE_CONTROLLER_URI)
public class DownloadFile {

    @GetMapping
    public ResponseEntity<Resource> downloadFile(@PathVariable("currentversion")long currentversion,
                                                 HttpServletRequest request) {
        // Load file as Resource

        FileUtil fileUtil = new FileUtil();
        Map<Long,String> map = fileUtil.findConfig();
        Resource resource = fileUtil.loadFileAsResource(map.get(currentversion+1));
        if (resource.exists())
        {
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
        // Try to determine file's content type

    }
}
