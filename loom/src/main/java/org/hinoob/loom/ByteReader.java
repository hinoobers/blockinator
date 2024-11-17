package org.hinoob.loom;

public class ByteReader {

    private byte[] bytes;
    private int index;

    public ByteReader(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte readByte() {
        return bytes[index++];
    }

    public short readShort() {
        return (short) ((bytes[index++] << 8) | (bytes[index++] & 0xFF));
    }

    public int readInt() {
        return (bytes[index++] << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
    }

    public boolean available() {
        return index < bytes.length;
    }

    public String readString() {
        int length = readInt();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append((char) readByte());
        }
        return builder.toString();
    }

    public byte[] getBytes() {
        return bytes;
    }
}
