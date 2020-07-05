package br.com.rzandonai.tesseractTest.controllers;

import cn.easyproject.easyocr.EasyOCR;
import cn.easyproject.easyocr.ImageType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class UploadController {

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        EasyOCR e = new EasyOCR();
//        e.setTesseractOptions("-l apofont");
        e.setTesseractOptions("digits");
           Resource resource = new ClassPathResource("code2.jpg");
        StringBuilder retorno = new StringBuilder();

        String CAPTCHA_NORMAL = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CAPTCHA_NORMAL);
        if(!StringUtils.isEmpty(CAPTCHA_NORMAL)){
            retorno.append(" CAPTCHA_NORMAL:"+CAPTCHA_NORMAL);
        }
        String CAPTCHA_WHITE_CHAR = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CAPTCHA_WHITE_CHAR);
        if(!StringUtils.isEmpty(CAPTCHA_WHITE_CHAR)){
            retorno.append(" CAPTCHA_WHITE_CHAR:"+CAPTCHA_WHITE_CHAR);
        }
        String CAPTCHA_HOLLOW_CHAR = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CAPTCHA_HOLLOW_CHAR);
        if(!StringUtils.isEmpty(CAPTCHA_HOLLOW_CHAR)){
            retorno.append(" CAPTCHA_HOLLOW_CHAR:"+CAPTCHA_HOLLOW_CHAR);
        }
        String CAPTCHA_INTERFERENCE_LINE = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CAPTCHA_INTERFERENCE_LINE);
        if(!StringUtils.isEmpty(CAPTCHA_INTERFERENCE_LINE)){
            retorno.append(" CAPTCHA_INTERFERENCE_LINE:"+CAPTCHA_INTERFERENCE_LINE);
        }
        String CAPTCHA_SPOT = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CAPTCHA_SPOT);
        if(!StringUtils.isEmpty(CAPTCHA_NORMAL)){
            retorno.append(" CAPTCHA_SPOT:"+CAPTCHA_SPOT);
        }
        String TEAM = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.TEAM);
        if(!StringUtils.isEmpty(TEAM)){
            retorno.append(" TEAM:"+TEAM);
        }
        String CLEAR = e.discernAndAutoCleanImage(file.getInputStream(), ImageType.CLEAR);
        if(!StringUtils.isEmpty(CAPTCHA_NORMAL)){
            retorno.append(" CAPTCHA_NORMAL:"+CAPTCHA_NORMAL);
        }

        attributes.addFlashAttribute("message", "Tesseract decrypt: "+retorno+"!");

        return "redirect:/";
    }

}