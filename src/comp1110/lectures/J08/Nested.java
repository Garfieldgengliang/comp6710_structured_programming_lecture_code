package comp1110.lectures.J08;

import comp1110.lectures.O05.EqualsHashCode;

public class Nested {
    public static void main(String[] args) {
        LectureTheatre copland
                = new LectureTheatre("Copland Theatre", "Panasonic");
        System.out.println(copland);

        LectureTheatreStaticNested tank
                = new LectureTheatreStaticNested("Haydon Allen Tank", "Sony");
        System.out.println(tank);

        LectureTheatreInner robertson
                = new LectureTheatreInner("RN Robertson", "Sony");
        System.out.println(robertson);

        PersonPhoneInner Amy
                = new PersonPhoneInner("Amy","iphone");
        System.out.println(Amy);


    }
}
