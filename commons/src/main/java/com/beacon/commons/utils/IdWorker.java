package com.beacon.commons.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * From: https://github.com/twitter/snowflake
 * An object that generates IDs.
 * This is broken into a separate class in case
 * we ever want to support multiple worker threads
 * per process
 */
public class IdWorker {

    private long workerId;
    private long datacenterId;
    private long sequence = 0L; // 0，并发控制

    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L; // 只允许workId的范围为：0-1023
    private long datacenterIdBits = 5L;
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private long sequenceBits = 12L;

    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) { // workId < 1024[10位：2的10次方]
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) { // 如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp); // 重新生成timestamp
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    /**
     * 保证返回的毫秒数在参数之后
     *
     * @param lastTimestamp
     * @return
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获得系统当前毫秒数
     *
     * @return
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static Set<String> idsSet = new HashSet<String>(500000);

    private static boolean check(Long id, Random r) {
        String idS = id.toString();
//        int begin = r.nextInt(15);
//        String sub = idS.substring(begin, begin + 1) + idS.substring(7, 8) + idS.substring(idS.length() - 8, idS.length());
//        System.out.println(sub);
        if (idsSet.contains(idS)) {
            throw new RuntimeException("size:" + idsSet.size());
        }

        idsSet.add(idS);

        return true;
    }

    private static Random random = new Random(200);

    private static IdWorker idWorker = new IdWorker(30, 30);

    public static String getCode() {
        String idS = idWorker.nextId() + "";
        int begin = random.nextInt(15);
        return idS.substring(begin, begin + 1) + idS.substring(7, 8) + idS.substring(idS.length() - 8, idS.length());
    }

    public static String getOrderCode() {
        return idWorker.nextId() + "";
    }

    public static void main(String[] args) {
        IdWorker id = new IdWorker(2, 2);
        Random random = new Random(id.nextId());
        for (int i = 0; i < 100000; i++) {
            check(id.nextId(), random);
//            System.out.println(getOrderCode());
        }
        System.out.println("success");
    }

}
