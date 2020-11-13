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
    public static int getNumberByLevel(UserLevel level){
        switch(level){
            case BEGINNER:
                return 0;
            case AMATEUR:
                return 1;
            case EXPERT:
                return 2;
            case PROFESSIONAL:
                return 3;
            default:
                return -1;
        }
    }


}
