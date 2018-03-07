package com.mydana.alms.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@Controller
public class AsxMetaStockImport {
/*
    // save uploaded file to this folder
    private static String UPLOADED_FOLDER = "E://temp//";


    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        //try {

        try{
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);



        } catch (IOException e) {
            System.out.println("----------Err file ;" +e);
        }
        System.out.println("---------- file  Done" );

        modelMap.addAttribute("message", file.getName());
        return "uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

*/
}
