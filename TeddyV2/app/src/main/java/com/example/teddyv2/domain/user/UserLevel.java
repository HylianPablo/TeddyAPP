package com.example.teddyv2.domain.user;

public enum UserLevel {
    BEGINNER,
    AMATEUR,
    EXPERT,
    PROFESSIONAL;

    public static UserLevel getUserLevelByNumber(int i){
        switch(i){
            case 0:
                return BEGINNER;
            case 1:
                return AMATEUR;
            case 2 :
                return EXPERT;
            case 3:
                return PROFESSIONAL;
            default:
                return null;
        }
    }
}
