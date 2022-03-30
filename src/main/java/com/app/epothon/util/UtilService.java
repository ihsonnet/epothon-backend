package com.app.epothon.util;

import com.app.epothon.dto.BasicTableInfo;
import com.app.epothon.jwt.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class UtilService {
    private final JwtProvider jwtProvider;


    public BasicTableInfo generateBasicTableInfo(String name, String token){

        String id = UUID.randomUUID().toString();
        String slug = name.toLowerCase().replace(" ", "-")
                + "-" + id.substring(0, 4);
        String createBy = null;
        createBy = jwtProvider.getUserNameFromJwt(token);
        Long creationTime = System.currentTimeMillis();

        String SKU = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 10);

        return new BasicTableInfo(id, slug, SKU, createBy, creationTime);

    }

    public BasicTableInfo generateBasicTableInfoWithoutToken(String name){

        String id = UUID.randomUUID().toString();
        String slug = name.toLowerCase().replace(" ", "-")
                + "-" + id.substring(0, 4);
        String createBy = null;
        Long creationTime = System.currentTimeMillis();

        String SKU = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 10);

        return new BasicTableInfo(id, slug, SKU, createBy, creationTime);

    }
}
