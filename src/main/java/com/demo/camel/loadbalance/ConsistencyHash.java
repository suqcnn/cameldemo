package com.demo.camel.loadbalance;

import java.io.UnsupportedEncodingException;   
//import java.nio.ByteBuffer;   
//import java.nio.ByteOrder;   
import java.security.MessageDigest;   
import java.security.NoSuchAlgorithmException;   
import java.util.*;   

public class ConsistencyHash {   
    private TreeMap<Long,Object> nodes = null;   
    //��ʵ�������ڵ���Ϣ   
    private List<Object> shards = new ArrayList();   
    //��������ڵ���Ŀ   
    private int VIRTUAL_NUM = 4;   
  
    /**  
     * ��ʼ��һ�»�  
     */  
    public void init() {   
         shards.add("processor1");   
         shards.add("processor2");   
         shards.add("processor3");   
         shards.add("processor4");   
         shards.add("processor5");   
  
        nodes = new TreeMap<Long,Object>();   
        for(int i=0; i<shards.size(); i++) {   
            Object shardInfo = shards.get(i);   
            for(int j=0; j<VIRTUAL_NUM; j++) {   
                nodes.put(hash(computeMd5("SHARD-" + i + "-NODE-" + j),j), shardInfo);   
            }   
        }   
    }   
    
    public void addProcessor(Object shardInfo){
    	for(int j=0; j<VIRTUAL_NUM; j++) {   
            nodes.put(hash(computeMd5("SHARD-" + new Random().nextInt() + "-NODE-" + j),j), shardInfo);   
        }   
    }
  
    /**  
     * ����key��hashֵȡ�÷������ڵ���Ϣ  
     * @param hash  
     * @return  
     */  
    public Object getShardInfo(long hash) {   
        Long key = hash;   
        SortedMap<Long, Object> tailMap=nodes.tailMap(key);   
        if(tailMap.isEmpty()) {   
            key = nodes.firstKey();   
        } else {   
            key = tailMap.firstKey();   
        }   
        return nodes.get(key);   
    }   
  
    /**  
     * ��ӡԲ���ڵ�����  
     */  
     public void printMap() {   
         System.out.println(nodes);   
     }   
  
    /**  
     * ����2^32�ѽڵ�ֲ���Բ�����档  
     * @param digest  
     * @param nTime  
     * @return  
     */  
      public long hash(byte[] digest, int nTime) {   
        long rv = ((long) (digest[3+nTime*4] & 0xFF) << 24)   
                | ((long) (digest[2+nTime*4] & 0xFF) << 16)   
                | ((long) (digest[1+nTime*4] & 0xFF) << 8)   
                | (digest[0+nTime*4] & 0xFF);   
  
        return rv & 0xffffffffL; /* Truncate to 32-bits */  
      }   
  
    /**  
     * Get the md5 of the given key.  
     * ����MD5ֵ  
     */  
     public byte[] computeMd5(String k) {   
        MessageDigest md5;   
        try {   
            md5 = MessageDigest.getInstance("MD5");   
        } catch (NoSuchAlgorithmException e) {   
            throw new RuntimeException("MD5 not supported", e);   
        }   
        md5.reset();   
        byte[] keyBytes = null;   
        try {   
            keyBytes = k.getBytes("UTF-8");   
        } catch (UnsupportedEncodingException e) {   
            throw new RuntimeException("Unknown string :" + k, e);   
        }   
  
        md5.update(keyBytes);   
        return md5.digest();   
     }   
  
     public static void main(String[] args) throws InterruptedException {   
         Random ran = new Random();   
         ConsistencyHash hash = new ConsistencyHash();   
         hash.init();   
         hash.printMap();   
         //ѭ��50�Σ���Ϊ��ȡ50����������Ч������ȻҲ�����������κε�����������   
         for(int i=0; i<500; i++) {
        	 Thread.sleep(1000);
        	 int processorNum = ran.nextInt();
        	 if(processorNum%5==0){
        		 hash.addProcessor("processorN"+processorNum);
        		 System.out.println("----------------->������һ��proceoor:"+"processorN"+processorNum);
        	 }
             System.out.println("����request"+i+"��processor:"+hash.getShardInfo(hash.hash(hash.computeMd5(String.valueOf("request"+i)),ran.nextInt(hash.VIRTUAL_NUM))));   
         }   
   }   
  
}  
