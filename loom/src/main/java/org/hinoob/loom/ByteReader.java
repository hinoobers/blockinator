package org.hinoob.loom;

import org.hinoob.blockinator.Vector;

public class ByteReader {

    private byte[] bytes;
    private int index;

    public ByteReader(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte readByte() {
        if(index >= bytes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + bytes.length);
        }

        return bytes[index++];
    }

    public short readShort() {
        if(index + 1 >= bytes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + bytes.length);
        }

        return (short) ((bytes[index++] << 8) | (bytes[index++] & 0xFF));
    }

    public int readInt() {
        if(index + 3 >= bytes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + bytes.length);
        }

        return (bytes[index++] << 24) | ((bytes[index++] & 0xFF) << 16) | ((bytes[index++] & 0xFF) << 8) | (bytes[index++] & 0xFF);
    }

    public boolean available() {
        return index < bytes.length;
    }

    public String readString() {
        int length = readInt();
        if(index + length > bytes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + bytes.length);
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            builder.append((char) readByte());
        }
        return builder.toString();
    }

    public byte[] readBytes() {
        int len = readInt();
        if(index + len > bytes.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + bytes.length);
        }

        byte[] b = new byte[len];
        for(int i = 0; i < len; i++) {
            b[i] = readByte();
        }
        return b;
    }

    public Vector readVector() {
        return new Vector(readInt(), readInt());
    }

    public byte[] build() {
        return bytes;
    }
}
