package com.cathay.dicom.service.outBound;


import com.cathay.dicom.rest.transform.PostDicomImageResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "${outbound-service.flask-server.name}", url = "${outbound-service.flask-server.url}")
public interface SpringBootToFlaskApiClient {

    @PostMapping(value = "/fetchImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PostDicomImageResp> fetchImage(@RequestBody MultipartFile files);
}
