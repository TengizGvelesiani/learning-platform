package com.tengo.learningplatform.materials;

import java.util.Objects;

import com.tengo.learningplatform.contracts.Displayable;
import com.tengo.learningplatform.domain.MaterialKind;


public abstract class Material implements Displayable {

    protected String name;

    protected Material(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getDisplayName();

    public abstract MaterialKind getMaterialKind();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(name, material.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
