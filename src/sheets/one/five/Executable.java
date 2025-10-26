package sheets.one.five;

public class Executable {


    static void main() {
        Developer me = new Developer();

        me.setFullName("Yassine Asghar");
        me.setAge(26);
        me.setExperienceYears(2);
        me.setProgrammingLanguage("Python");
        me.setSalary(454.34);

        System.out.println("Developer's Information:");
        System.out.println("Full Name: " + me.getFullName());
        System.out.println("Age: " + me.getAge());
        System.out.println("Experience Years: " + me.getExperienceYears());
        System.out.println("Programming Language: " + me.getProgrammingLanguage());
        System.out.println("Salary: $" + me.getSalary());

        me.worksOnProject("FRAUAS_HIS_122324");
        me.lastMeetingAttendance();
        me.workStatus();
        me.reviewCode();
        me.testCode();
    }
}


