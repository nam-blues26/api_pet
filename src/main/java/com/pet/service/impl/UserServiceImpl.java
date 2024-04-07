package com.pet.service.impl;

import com.pet.dto.request.AuthRequest;
import com.pet.entity.User;
import com.pet.exception.NotFoundException;
import com.pet.repository.IUserRepository;
import com.pet.service.IUserService;
import com.pet.service.base.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.pet.constant.MessageConstant.NOTFOUND_PRODUCT_EXCEPTION;
import static com.pet.constant.MessageConstant.NOTFOUND_USER_EXCEPTION;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IMessageService messageService;
    @Override
    public Boolean login(AuthRequest request) {
        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(
            () -> new NotFoundException(messageService.getMessage(NOTFOUND_USER_EXCEPTION))
        );
        if (user.getPassword().equals(request.getPassword())){
            return true;
        }
        return false;
    }
}
