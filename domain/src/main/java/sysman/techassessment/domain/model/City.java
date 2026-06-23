package sysman.techassessment.domain.model;

public class City {
    private String code;
    private String name;
    private String departmentCode;

    public City() {}

    public City(String departmentCode, String name, String code) {
        this.departmentCode = departmentCode;
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
