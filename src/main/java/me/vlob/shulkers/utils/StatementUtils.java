package me.vlob.shulkers.utils;

import java.util.List;

public class StatementUtils {

    public static String createTable(String name, List<String> paths){
        StringBuilder stringBuilder = new StringBuilder();
        String primaryKey = paths.get(0);
        stringBuilder.append("create table if not exists " + name + " (");
        paths.forEach(path -> {
            stringBuilder.append(path + " varchar(255) not null,");
        });
        stringBuilder.append("primary key(" + primaryKey + "));");
        return stringBuilder.toString();
    }

    public static String updateObject(String table, String primaryValue, List<String> paths, List<String> values){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE `" + table + "` SET " + paths.get(0) +"='" + primaryValue);
        for(int i = 0; i < paths.size(); i++){
            if(i == 0) continue;
            stringBuilder.append("', " + paths.get(i) + "='" + values.get(i));
        }
        stringBuilder.append("' WHERE " + paths.get(0) + "='" + primaryValue + "'");
        return stringBuilder.toString();
    }

    public static String insertObject(String table, List<String> values){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO `" + table + "` VALUES('" + values.get(0));
        values.forEach(value -> {
            if(values.get(0).equals(value)) return;
            stringBuilder.append("', '" + value);
        });
        stringBuilder.append("')");
        return stringBuilder.toString();
    }

}
