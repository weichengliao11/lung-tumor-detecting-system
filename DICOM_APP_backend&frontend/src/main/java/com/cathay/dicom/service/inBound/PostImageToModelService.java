package com.cathay.dicom.service.inBound;

import com.cathay.dicom.rest.transform.PostDicomImageResp;
import com.cathay.dicom.service.outBound.SpringBootToFlaskApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class PostImageToModelService {

    @Autowired
    SpringBootToFlaskApiClient springBootToFlaskApiClient;


    public PostDicomImageResp postImage(MultipartFile files, String fileName) throws IOException {

        log.info("Transfer file to flask...");
        ResponseEntity<PostDicomImageResp> responses = springBootToFlaskApiClient.fetchImage(files);
        PostDicomImageResp resp = responses.getBody();
        resp.setFileName(fileName);
        log.info(fileName + " finish detecting tumor...");


        log.info("Transfer completed.");
        return resp;
    }
}
