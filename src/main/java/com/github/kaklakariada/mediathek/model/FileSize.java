package com.github.kaklakariada.mediathek.model;

public class FileSize {

    private static final int BYTES_PER_MEGABYTE = 1024 * 1024;
    private final long bytes;

    public FileSize(long bytes) {
        this.bytes = bytes;
    }

    public static FileSize ofBytes(long bytes) {
        return new FileSize(bytes);
    }

    public long getBytes() {
        return bytes;
    }

    public long getMegaBytes() {
        return bytes / BYTES_PER_MEGABYTE;
    }

    @Override
    public String toString() {
        return getMegaBytes() + "MB";
    }
}
