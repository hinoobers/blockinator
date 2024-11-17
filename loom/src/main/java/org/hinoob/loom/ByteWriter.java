package org.hinoob.loom;

import org.hinoob.blockinator.Vector;

public class ByteWriter {

    private byte[] bytes;
    private int index;

    public void ensureCapacity(int capacity) {
        if (bytes == null) {
            bytes = new byte[capacity];
        } else if (bytes.length < capacity) {
            byte[] newBytes = new byte[capacity];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            bytes = newBytes;
        }
    }

    public ByteWriter writeByte(byte b) {
        ensureCapacity(index + 1);
        bytes[index++] = b;
        return this;
    }

    public ByteWriter writeShort(short s) {
        ensureCapacity(index + 2);
        bytes[index++] = (byte) (s >> 8);
        bytes[index++] = (byte) s;
        return this;
    }

    public ByteWriter writeInt(int i) {
        ensureCapacity(index + 4);
        bytes[index++] = (byte) (i >> 24);
        bytes[index++] = (byte) (i >> 16);
        bytes[index++] = (byte) (i >> 8);
        bytes[index++] = (byte) i;
        return this;
    }

    public ByteWriter writeString(String s) {
        byte[] stringBytes = s.getBytes();
        writeInt(stringBytes.length);
        ensureCapacity(index + stringBytes.length);
        System.arraycopy(stringBytes, 0, bytes, index, stringBytes.length);
        index += stringBytes.length;
        return this;
    }

    public ByteWriter writeBytes(byte[] b) {
        writeInt(b.length);
        ensureCapacity(index + b.length);
        for(byte Byte : b) {
            writeByte(Byte);
        }
        return this;
    }

    public ByteWriter appendBytes(byte[] b) {
        ensureCapacity(index + b.length);
        for(byte Byte : b) {
            writeByte(Byte);
        }
        return this;
    }

    public ByteWriter writeVector(Vector vector) {
        writeInt(vector.getX());
        writeInt(vector.getY());
        return this;
    }


    public byte[] getBytes() {
        byte[] result = new byte[index];
        System.arraycopy(bytes, 0, result, 0, index);
        return result;
    }
}
