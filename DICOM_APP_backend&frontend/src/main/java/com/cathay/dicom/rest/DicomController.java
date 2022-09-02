package com.cathay.dicom.rest;

import com.cathay.dicom.rest.transform.PostDicomImageResp;
import com.cathay.dicom.service.inBound.PostImageToModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
@Slf4j
public class DicomController {

    @Autowired
    PostImageToModelService postImageToModelService;

    @GetMapping("/")
    public String home() {

        return "index";
    }

    @PostMapping(value = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String postImageToModel(Model model, @RequestBody MultipartFile[] files) throws IOException {


        List<PostDicomImageResp> responseList = new ArrayList<>();
        long startTime = System.nanoTime();
        Arrays.asList(files).stream().forEach(file -> {
            try {
                PostDicomImageResp resp = postImageToModelService.postImage(file, file.getOriginalFilename());
                responseList.add(resp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        long endTime = System.nanoTime();
        long seconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Execution Time: " + String.valueOf(seconds) + " millionseconds");

        model.addAttribute("responseList", responseList);

        return "index";
    }
}
