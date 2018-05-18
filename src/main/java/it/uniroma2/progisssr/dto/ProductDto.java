package it.uniroma2.progisssr.dto;

public class ProductDto {

    private Long ID;
    private String name;
    private String version;
    private String description;

    public ProductDto() {}
/*
    public ProductDto(Long ID, String name, Integer version, String description) {
        this.ID = ID;
        this.name = name;
        this.version = version;
        this.description = description;
    }*/

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
