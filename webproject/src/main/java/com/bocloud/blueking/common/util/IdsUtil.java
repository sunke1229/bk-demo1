package com.bocloud.blueking.common.util;

import com.bocloud.blueking.model.db.GenericEntity;
import com.bocloud.blueking.model.db.User;

import java.util.ArrayList;
import java.util.List;

public class IdsUtil {
    public static List<Long> transformStringToLongList(String ids){
        List<Long> idsLong = new ArrayList<>();
        String[] idsString = ids.trim().split(",");
        for(String id:idsString){
            idsLong.add(Long.parseLong(id));
        }
        return  idsLong;
    }

    public static String getIdsStringFromLongList( List<Long> idsLong){
        if(idsLong.size()<=0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(Long id:idsLong){
            sb.append(id+",");
        }
        sb.deleteCharAt(sb.length()-1);
        return  sb.toString();
    }

    public static String getIdsStringFromBeanList(List<? extends GenericEntity> beans){
        if(beans.size()<=0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(GenericEntity entity:beans){
            sb.append(entity.getId()+",");
        }
        sb.deleteCharAt(sb.length()-1);
        return  sb.toString();
    }

    public static String getIdsStringFromUserList(List<User> users){
        if(users.size()<=0){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for(User user:users){
            sb.append(user.getId()+",");
        }
        sb.deleteCharAt(sb.length()-1);
        return  sb.toString();
    }

    public static  List<Long> getIdsListFromUserList(List<User> users){
        List<Long> idsLong = new ArrayList<>();
        if(users.size()<=0){
            return idsLong;
        }
        for(User user:users){
            idsLong.add(user.getId());
        }
        return  idsLong;
    }
}
