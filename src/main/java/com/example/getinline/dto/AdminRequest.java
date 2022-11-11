package com.example.getinline.dto;

public record AdminRequest(
        String email,
        String nickName,
        String password,
        String phoneNumber,
        String memo
) {
    public static AdminRequest of (
            String email,
            String nickName,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminRequest(email, nickName, password, phoneNumber, memo);
    }
}
