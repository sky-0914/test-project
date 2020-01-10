package com.spring.cloud.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: 赵小超
 * @Date: 2018/11/1 23:25
 * @Description:
 */
@RestController
public class ServerController {

    @GetMapping(value = "/upload")
    public String test(@RequestParam MultipartFile file) throws IOException {
        byte[] b = file.getBytes();
        File f = new File(file.getOriginalFilename());
        FileCopyUtils.copy(b, f);
        return f.getAbsolutePath();
    }
}
