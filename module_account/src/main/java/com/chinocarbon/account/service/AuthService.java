package com.chinocarbon.account.service;

import org.springframework.stereotype.Service;

public interface AuthService {
    String createToken(String userId);

    String getUserIdFromToken(String token);
}
