package service;

import materials.Material;

public class DisplayService {

    private Material featuredMaterial;

    public Material getFeaturedMaterial() {
        return featuredMaterial;
    }

    public void setFeaturedMaterial(Material featuredMaterial) {
        this.featuredMaterial = featuredMaterial;
    }

    public void printMaterialSummary(Material material) {
        System.out.println(material.getDisplayName());
    }
}

