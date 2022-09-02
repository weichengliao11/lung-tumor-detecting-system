package com.cathay.dicom.rest.transform;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PostDicomImageResp {

    @JsonProperty("INPUT")
    private String input;

    @JsonProperty("OUTPUT")
    private String output;

    @JsonProperty("TUMOR_SIZE")
    private Integer tumorSize;

    @JsonProperty("TUMOR_EXISTENCE")
    private String tumorExistence;

    @JsonProperty("FILENAME")
    private String fileName;
}
