package it.uniroma2.progisssr.dto;

public class ProductDto {

    private Long ID;
    private String name;
    private Integer version;
    private String description;

    public ProductDto() {}

    public ProductDto(Long ID, String name, Integer version, String description) {
        this.ID = ID;
        this.name = name;
        this.version = version;
        this.description = description;
    }
}
