package com.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ThreadLocalRandom;

public class Sequence {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sequence.class);
    /**
     * epoch of start time 2017/01/01 00:00:00
     */
    private final long timeEpoch = 1483200000000L;
    /**
     * worker id bits
     */
    private final long workerIdBits = 5L;
    /**
     * data center id bits
     */
    private final long dataCenterIdBits = 5L;
    /**
     * max worker id 31
     */
    private final long maxWorkerId = ~(-1L << workerIdBits);
    /**
     * max data center id 31
     */
    private final long maxDataCenterId = ~(-1L << dataCenterIdBits);
    /**
     * sequence bits
     */
    private final long sequenceBits = 12L;
    /**
     * worker id left shift
     */
    private final long workerIdShift = sequenceBits;
    /**
     * data center id left shift
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;
    /**
     * timestamp left shift
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    /**
     * mask of sequence 2^12-1
     */
    private final long sequenceMask = ~(-1L << sequenceBits);

    private long workerId;

    private long dataCenterId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public Sequence() {
        this.dataCenterId = getDataCenterId(maxDataCenterId);
        this.workerId = getMaxWorkerId(dataCenterId, maxWorkerId);
    }

    public Sequence(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("data center id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    private static long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(dataCenterId);
        //get the name representing the running jvm
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!StringUtils.isEmpty(name)) {
            //get jvm pid
            sb.append(name.split("@")[0]);
        }
        return (sb.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    private static long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(localHost);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDataCenterId + 1);
            }
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.error("get data center id : {}", e.getMessage());
            }
        }
        return id;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                try {
                    wait(offset << 1);
                    timestamp = timeGen();
                    if (timestamp < lastTimestamp) {
                        throw new RuntimeException(String.format("clock moved backwards,refusing to generate id for %d milliseconds", offset));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException(String.format("clock moved backwards,refusing to generate id for %d milliseconds", offset));
            }
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = ThreadLocalRandom.current().nextLong(1, 3);
        }
        lastTimestamp = timestamp;
        return timestamp - timeEpoch << timestampLeftShift | dataCenterId << dataCenterIdShift | workerId << workerIdShift | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return SystemTimer.now();
    }
}
