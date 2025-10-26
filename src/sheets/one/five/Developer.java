package sheets.one.five;

import java.util.Random;

public class Developer {
    private String fullName;
    private int experienceYears;
    private int age;
    private String programmingLanguage;
    private double salary;

    public String getFullName() {
        return fullName;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public int getAge() {
        return age;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public double getSalary() {
        return salary;
    }

    public void setFullName(String fullName) {
        if (fullName != null && !fullName.isEmpty()) {
            this.fullName = fullName;
        } else {
            throw new IllegalArgumentException("Developer's full name cannot be empty !");
        }
    }

    public void setExperienceYears(int experienceYears) {
        if (experienceYears >= 0) {
            this.experienceYears = experienceYears;
        } else {
            throw new IllegalArgumentException("Developer's experience years cannot be negative !");
        }
    }

    public void setAge(int age) {
        if (age > 18 && age < 60) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("This company does not hire developer under 18 or above 60");
        }
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        if (programmingLanguage != null && !programmingLanguage.isEmpty()) {
            this.programmingLanguage = programmingLanguage;
        } else {
            throw new IllegalArgumentException("Developer's programming language cannot be empty !");
        }
    }

    public void setSalary(double salary) {
        if (salary > 0) {
            this.salary = salary;
        } else {
            throw new IllegalArgumentException("Developer's salary cannot be negative !");
        }
    }

    public void worksOnProject(String projectId) {
        if (projectId != null) {
            System.out.println("Developer works on project " + projectId);
        }
    }

    public void lastMeetingAttendance() {
        if (randomInteger() % 2 == 0) {
            System.out.println("Developer attended last meeting.");
        } else {
            System.out.println("Developer did not attended last meeting.");
        }
    }

    public void workStatus() {
        if (randomInteger() % 3 == 0) {
            System.out.println("Developer is online");
        } else {
            System.out.println("Developer is offline");
        }
    }

    public void reviewCode() {
        if (this.experienceYears > 2) {
            System.out.println("Developer can review code");
        } else {
            System.out.println("Developer can not review code");
        }
    }

    public void testCode() {
        System.out.println("ALl Developers have to test there code before deploying");

    }

    public static int randomInteger() {
        Random random = new Random();
        return random.nextInt(10);
    }
}
