package com.example.bob.health_helper.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词处理工具
 */
public class SensitiveWordUtil {

    //敏感词匹配规则
    public static final int MinMatchType=1;
    public static final int MaxMatchType=2;

    //敏感词集合
    public static HashMap sensitiveWordMap;

    /**
     * 初始化敏感词库
     * @param sensitiveWordSet
     */
    public static synchronized void init(Set<String> sensitiveWordSet){
        initSensitiveWordMap(sensitiveWordSet);
    }

    private static void initSensitiveWordMap(Set<String> sensitiveWordSet){
        //初始化敏感词容器，减少扩容操作
        sensitiveWordMap=new HashMap(sensitiveWordSet.size());
        String key;//敏感关键词
        Map currentMap;
        Map<String,String> newMap;
        Iterator<String> iterator=sensitiveWordSet.iterator();
        //遍历敏感词集合
        while (iterator.hasNext()){
            key=iterator.next();
            currentMap=sensitiveWordMap;
            for(int i=0;i<key.length();i++){
                char node=key.charAt(i);
                Object wordMap=currentMap.get(node);
                if(wordMap!=null)//当前节点已经存在
                    currentMap=(Map)wordMap;
                else {
                    newMap=new HashMap<>();
                    newMap.put("isEnd","0");
                    currentMap.put(node,newMap);
                    currentMap=newMap;
                }
                if(i==key.length()-1)//关键词中的最后一个字
                    currentMap.put("isEnd","1");
            }
        }
    }

    /**
     * 检查文字中包含敏感词长度
     * @param text
     * @param beginIndex
     * @param matchType
     * @return
     */
    private static int checkSensitiveWord(String text,int beginIndex,int matchType){
        boolean flag=false;//敏感词结束标识位(用于只有一位时）
        int matchFlag=0;//匹配标识数
        char key;
        Map currentMap=sensitiveWordMap;
        for(int i=beginIndex;i<text.length();i++){
            key=text.charAt(i);
            currentMap=(Map) currentMap.get(key);
            if(currentMap!=null){
                matchFlag++;
                if("1".equals(currentMap.get("isEnd"))){
                    flag=true;
                    if(MinMatchType==matchType)//最小匹配时结束
                        break;
                }
            }else
                break;
        }
        if(matchFlag<2 || !flag)//敏感词字符长度至少为2
            matchFlag=0;
        return matchFlag;
    }

    /**
     *  判断文字是否包含敏感字符
     * @param text
     * @param matchType
     * @return
     */
    public static boolean contains(String text,int matchType){
        boolean flag=false;
        for(int i=0;i<text.length();i++){
            int matchFlag=checkSensitiveWord(text,i,matchType);
            if(matchFlag>0)
                flag=true;
        }
        return flag;
    }

    public static boolean contains(String text){
        return contains(text,MaxMatchType);
    }

    /**
     * 获取文字中的敏感词
     * @param text
     * @param matchType
     * @return
     */
    public static Set<String> getSensitiveWord(String text,int matchType){
        Set<String> sensitiveWords=new HashSet<>();
        for(int i=0;i<text.length();i++){
            int length=checkSensitiveWord(text,i,matchType);
            if(length>0){
                sensitiveWords.add(text.substring(i,i+length));
                i+=length-1;
            }
        }
        return sensitiveWords;
    }

    public static Set<String> getSensitiveWord(String text) {
        return getSensitiveWord(text,MaxMatchType);
    }

    /**
     * 替换敏感词
     * @param text
     * @param replaceChar
     * @param matchType
     * @return
     */
    public static String replaceSensitiveWord(String text,char replaceChar,int matchType){
        String result=text;
        Set<String> set=getSensitiveWord(text,matchType);//获取文本中的所有敏感词
        Iterator<String> iterator=set.iterator();
        String word;
        String replaceString;
        while (iterator.hasNext()){
            word=iterator.next();
            replaceString=getReplaceChars(replaceChar,word.length());
            result=result.replaceAll(word,replaceString);
        }
        return result;
    }

    public static String replaceSensitiveWord(String text,char replaceChar){
        return replaceSensitiveWord(text,replaceChar,MaxMatchType);
    }

    private static String getReplaceChars(char replaceChar,int length){
        String resultReplace=String.valueOf(replaceChar);
        for(int i=1;i<length;i++)
            resultReplace+=replaceChar;
        return resultReplace;
    }

    public static String replaceSensitiveWord(String text,String replaceStr,int matchType){
        String result=text;
        Set<String> set=getSensitiveWord(text,matchType);//获取文本中的所有敏感词
        Iterator<String> iterator=set.iterator();
        String word;
        while (iterator.hasNext()){
            word=iterator.next();
            result=result.replaceAll(word,replaceStr);
        }
        return result;
    }

    public static String replaceSensitiveWord(String text,String replaceStr){
        return replaceSensitiveWord(text,replaceStr,MaxMatchType);
    }

}
