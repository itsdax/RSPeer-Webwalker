package com.dax.api.file_cache.data;

import com.dax.api.io.FileManager;
import com.dax.api.time.Expirable;

import java.io.File;

public class Blob implements Expirable {

    private long expiration;
    private String url;
    private String blobName;

    public Blob(String url) {
        this(-1, url);
    }

    public Blob(long expiration, String url) {
        this.url = url;
        this.expiration = expiration;
        this.blobName = ((System.nanoTime() % 100000) * 31) + ".blob";
    }

    public String getUrl() {
        return url;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getFileName() {
        return blobName;
    }

    public File getFile() {
        return new File(FileManager.getWorkingDir(getFileName(), "cache"));
    }

    @Override
    public boolean isExpired() {
        if (expiration == -1) return false;
        return System.currentTimeMillis() > expiration;
    }

}
