package org.hinoob.loom;

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

    public byte[] getBytes() {
        return bytes;
    }
}
