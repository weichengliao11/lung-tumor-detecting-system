package com.cathay.dicom.repository;

import com.cathay.dicom.entity.ResourcesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourcesRepository extends JpaRepository<ResourcesEntity, Integer> {

    ResourcesEntity findByResourceType(int resourceType);

}
