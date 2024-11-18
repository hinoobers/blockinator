package org.hinoob.blockinator.util;

public class TwoBox {

    private int x, y;
    private int width, height;

    public TwoBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(TwoBox other) {
        return this.x < other.x + other.width && this.x + this.width > other.x && this.y < other.y + other.height && this.y + this.height > other.y;
    }

    public boolean intersects(int x, int y, int width, int height) {
        return this.x < x + width && this.x + this.width > x && this.y < y + height && this.y + this.height > y;
    }
}
