package com.koral.vister.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class Word {
    public static boolean wordPattern(String pattern, String s) {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
        String [] strArgs = s.split(" ");
        int patLen = pattern.length();
        if(patLen != strArgs.length){
            return false;
        }
        Map charToMap= new HashMap<Character,String>();
        for(int i = 0; i < patLen; i++){
            char word = pattern.charAt(i);
            if(!charToMap.containsKey(word)){
                if(charToMap.containsValue(strArgs[i])){
                    return false;
                }
                charToMap.put(word,strArgs[i]);
            }else if(!charToMap.get(word).equals(strArgs[i])){
                return false;
            }
        }
        return true;
    }

    public static int lengthOfLastWord(String s) {
        int count = 0;
        String trim = s.trim();
        for (int i = trim.length() -1; i>=0 ; i--) {
            if(' ' == s.charAt(i)){
                break;
            }
            count ++;
        }
        return count;
    }

    public static void main(String[] args){
        Thread.currentThread().interrupt();
        System.out.println(wordPattern("abab", "ahaha ba haha ba"));
        System.out.println(lengthOfLastWord("aaa bbb ccccc"));
    }

}