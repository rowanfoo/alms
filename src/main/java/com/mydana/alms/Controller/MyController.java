package com.mydana.alms.Controller;

import com.mydana.alms.admin.BaseAdmin;
import com.mydana.alms.admin.CalcAverage;
import com.mydana.alms.admin.CalcChangePercent;
import com.mydana.alms.admin.CalcRSI;
import com.mydana.alms.entity.CoreData;
import com.mydana.alms.repo.DataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Scanner;
@Controller
public class MyController {

    @Autowired
    DataRepo dataRepo;

    // save uploaded file to this folder
    private static String UPLOADED_FOLDER = "E://temp//";

    @Autowired  ArrayList<String > allasxcodes;

    String name;


    @Autowired   CalcChangePercent calcChangePercent;
    @Autowired   CalcAverage calcAverage;
    @Autowired   CalcRSI  calcRSI;



    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        System.out.println("=======>submit");

        //try {
            System.out.println("---------- file ;" );
            System.out.println("---------- file ;"  + file.getName() );
            System.out.println("---------- file ;"  + file.getOriginalFilename() );
        System.out.println("---------- My NAME ;" + name );


        try{
            byte[] bytes = file.getBytes();
            String content = new String(bytes );

         //   System.out.println("---------- CONTENXT  ;" + content );


            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            Scanner scanner = new Scanner(content);
            while (scanner.hasNextLine()) {

                // use comma as separator
                String[] country = scanner.nextLine().split(" ");
                //System.out.println("-------------------------DATA----country---: "+ country[0] +  allcodes.contains( country[0].toUpperCase()));

                if( allasxcodes.contains( country[0].toUpperCase())  ){
                    LocalDate date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyyyMMdd"));

                    CoreData data = new CoreData( country[0]+".AX", date, new Double(country[5]), new Double("0"), new Double("0"), new Double(country[2]), new Double(country[3]),
                            new Double(country[4]),new Long(country[6]));

                    System.out.println("-------------------------DATA-------: "+ data);
                    dataRepo.save(data);
                }






            }

            scanner.close();
            System.out.println("-------------------------calcPercent-------: ");
             calcChangePercent.run();
            System.out.println("-------------------------calcAvg-------: ");
            System.out.println("-------------------------calcAvg-----calcAverage--: "+calcAverage);
             calcAverage.run();
             calcRSI.run();






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


}