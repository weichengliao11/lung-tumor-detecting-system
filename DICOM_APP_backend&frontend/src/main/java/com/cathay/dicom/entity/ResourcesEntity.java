package com.cathay.dicom.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "RESOURCES")
public class ResourcesEntity {

    @Id
    @Column(name = "INTERNALID")
    @NotBlank
    private Integer internalId;

    @Column(name = "RESOURCETYPE")
    @NotBlank
    private Integer resourceType;

    @Column(name = "PUBLICID")
    @NotBlank
    private String publicId;

    @Column(name = "PARENTID")
    private Integer parentId;

}
