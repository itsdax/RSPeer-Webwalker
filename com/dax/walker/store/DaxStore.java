package com.dax.walker.store;

import com.dax.walker.engine.definitions.PathLink;

import java.util.ArrayList;
import java.util.List;

public class DaxStore {

    public DaxStore() {
        this.pathLinks = new ArrayList<>(PathLink.getValues());
    }

    private List<PathLink> pathLinks;

    public void addPathLink(PathLink link) {
        this.pathLinks.add(link);
    }

    public void removePathLink(PathLink link) {
        this.pathLinks.remove(link);
    }

    public List<PathLink> getPathLinks() {
        return pathLinks;
    }
}
