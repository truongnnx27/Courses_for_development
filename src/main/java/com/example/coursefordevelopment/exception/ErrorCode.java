package com.example.coursefordevelopment.exception;

public enum ErrorCode {

    LESSON_Type_NOT_FOUND(1000, "LessonType not found"),
    LESSON_NOT_FOUND(1001, "LessonType not found"),
    INVALID_CONTENT(1002, "Content invalid"),
    INVALID_KEY(1003, "Title invalid"),
    QUIZ_NOT_FOUND(1004, "Quiz not found"),
    QUESTION_TYPE_NOT_FOUND(1005, "Question type not found"),
    USER_NOT_FOUND(1006, "User not found"),
    COURSE_NOT_FOUND(1007, "Course not found"),
    WISHLIST_ALREADY_EXIST(1008, "Wishlist already exist"),
    WISHLIST_NOT_FOUND(1009,"Wishlist not found"),
    SUCESSFULLY(9999, "Sucessfully");


    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
