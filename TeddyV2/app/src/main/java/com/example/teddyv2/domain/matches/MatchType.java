package com.example.teddyv2.domain.matches;

import com.example.teddyv2.domain.user.UserLevel;

public enum MatchType {
    SINGLES,
    DOUBLES;

    public static MatchType getTypeByInt(int i){
        if(i == 1){
            return  DOUBLES;
        }
        return  SINGLES;
    }

    public static int getIntByType(MatchType tipo){
        if(tipo.equals(DOUBLES)){
            return  1;
        }
        return  0;
    }



}
