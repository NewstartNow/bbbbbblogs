package com.shell845.myblog.service;

import com.shell845.myblog.po.User;



public interface UserService {
    User checkUser(String username, String password);
}
