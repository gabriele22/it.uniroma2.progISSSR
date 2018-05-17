package it.uniroma2.progisssr.dto;

public class TicketDto {

    private Long ID;
    private String status;
    private String dateStart;
    private String dateEnd;
    private String category;
    private String title;
    private String description;
    private Integer customerPriority;
    private Long productId;
    private Long customerId;
    private Long teamId;
//constructor for create ticket
    public TicketDto(String dateStart, String category, String title, String description, Integer customerPriority, Long productId, Long customerId) {
        this.dateStart =dateStart;
        this.category = category;
        this.title = title;
        this.description = description;
        this.customerPriority = customerPriority;
        this.productId = productId;
        this.customerId = customerId;
    }
//constructor for modify ticket(case customer)
    public TicketDto(String category, String title, String description, Integer customerPriority, Long productId, Long customerId) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.customerPriority = customerPriority;
        this.productId = productId;
        this.customerId = customerId;
    }


//costructor for

    public TicketDto( String category, String title, String description, Integer customerPriority, Long productId) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.customerPriority = customerPriority;
        this.productId = productId;
    }



    public TicketDto(){}

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCustomerPriority() {
        return customerPriority;
    }

    public void setCustomerPriority(Integer customerPriority) {
        this.customerPriority = customerPriority;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
}
