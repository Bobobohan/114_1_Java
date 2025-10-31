public class Person {
    private String name;
    private String idNumber; // 身份證字號
    private Date birthDate;  // 出生日期

    public Person(String name, String idNumber, Date birthDate) {
        this.name = name;
        this.idNumber = idNumber;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
