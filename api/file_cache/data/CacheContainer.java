package com.dax.api.file_cache.data;

import java.util.ArrayList;
import java.util.List;

public class CacheContainer {

    private List<Blob> list;

    public CacheContainer() {
        this(new ArrayList<>());
    }

    public CacheContainer(List<Blob> list) {
        this.list = list;
    }

    public List<Blob> getList() {
        return list;
    }

}
