package materials;

import contracts.Displayable;
import java.util.Objects;

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

