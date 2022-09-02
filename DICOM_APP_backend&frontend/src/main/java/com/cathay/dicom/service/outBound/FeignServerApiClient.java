package com.cathay.dicom.service.outBound;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "${outbound-service.dicom-server.name}", url = "${outbound-service.dicom-server.url}")
public interface FeignServerApiClient {

    @GetMapping(value = "/instances/{instanceId}/simplified-tags", consumes = "application/json")
    ResponseEntity<String> getDicomInstance(@PathVariable(value = "instanceId") String instanceId);

    @GetMapping(value = "/instances/{instanceId}/frames/{num}/preview?returnUnsupportedImage", consumes = "application/octet-stream")
    byte[] getDicomImage(@PathVariable(value = "instanceId") String instanceId,
                         @PathVariable(value = "num") String num);

    @GetMapping(value = "/studies/{studyId}", consumes = "application/json")
    ResponseEntity<String> getDicomStudy(@PathVariable(value = "studyId") String studyId);

    @GetMapping(value = "/patients/{patientId}", consumes = "application/json")
    ResponseEntity<String> getDicomPatient(@PathVariable(value = "patientId") String patientId);

}
