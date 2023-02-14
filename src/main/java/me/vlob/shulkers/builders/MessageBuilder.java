package me.vlob.shulkers.builders;

import me.vlob.shulkers.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MessageBuilder {

    private String text;
    private List<String> list;

    public MessageBuilder(String text){
        this.text = text;
    }

    public MessageBuilder(List<String> list){
        this.list = list;
    }

    public MessageBuilder replace(String regex, String toReplace){
        this.text = StringUtils.replace(this.text, regex, toReplace);
        return this;
    }

    public MessageBuilder replaceArray(String regex, String toReplace){
        this.list = this.list.stream().map(string -> StringUtils.replace(string, regex, toReplace)).collect(Collectors.toList());
        return this;
    }

    public List<String> toArray(){
        return this.list;
    }

    public String toString(){
        return this.text;
    }


}
