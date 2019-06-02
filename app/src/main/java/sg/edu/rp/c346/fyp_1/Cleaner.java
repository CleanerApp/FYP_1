package sg.edu.rp.c346.fyp_1;

public class Cleaner {

    private String name;
    private String age;
    private String gender;
    private String contact;
    private String rate;
    private String experience;

    public Cleaner(String name, String age, String gender, String contact, String rate, String experience) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.rate = rate;
        this.experience = experience;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
