package com.pet.service;

import com.pet.dto.request.AuthRequest;

public interface IUserService {

    public Boolean login(AuthRequest request);
}
