package com.youtube_timestamp.api.common.exception;

public class DuplicatedDB extends RuntimeException {

    private static final long serialVersionUID = 8087908946312578899L;

    public DuplicatedDB() {
        super("기존에 중복되는 정보가 있습니다.");
    }

    public DuplicatedDB(String message) {
        super(message);
    }
}
