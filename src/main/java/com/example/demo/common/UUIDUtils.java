package com.example.demo.common;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDUtils {

    public static UUID getUuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }


    public static String getUuidStringFromByteArray(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        for(int i=0; i<bytes.length; i++) {
            buffer.append(String.format("%02x", bytes[i]));
        }
        return buffer.toString();
    }
}
