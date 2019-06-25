package com.dax.walker.models;

import com.allatori.annotations.DoNotRename;

@DoNotRename
public interface Requirement {
    boolean satisfies();
}
