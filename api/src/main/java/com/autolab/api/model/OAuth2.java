package com.autolab.api.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.*;

/**
 * Created by zhao on 15/10/22.
 */
@Data
public class OAuth2 {

    @Getter
    @Setter
    @SerializedName("access_token")
    private String accessToken;

    @Getter
    @Setter
    @SerializedName("token_type")
    private String tokenType;

    @Getter
    @Setter
    @SerializedName("refresh_token")
    private String refreshToken;

    @Getter
    @Setter
    @SerializedName("expires_in")
    private Integer expiresIn;

    @Getter
    @Setter
    @SerializedName("scope")
    private String scope;

    @Getter
    @Setter
    protected Integer errcode;
    @Getter
    @Setter
    protected String errmsg;

    public static <T> T fromJson(String json, java.lang.Class<T> classOfT) {
        Gson gson = new Gson();
        T entity;
        try {
            entity = gson.fromJson(json, classOfT);
        } catch (Exception e) {

            return null;
        }

        return entity;

    }

}
