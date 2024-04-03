package com.pet.service.impl;

import com.pet.entity.Banner;
import com.pet.repository.IBannerRepository;
import com.pet.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private IBannerRepository bannerRepository;
    @Override
    public void uploadFiles(String urlImage) {
        Banner banner = Banner.builder()
                .image(urlImage)
                .active(true)
                .build();
        bannerRepository.save(banner);
    }
}
