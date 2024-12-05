import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<ExampleGeneric<?>> genericList = new ArrayList<>();
        genericList.add(new ExampleGeneric<>("String1", "String2"));
        genericList.add(new ExampleGeneric<>(2, "123"));
        genericList.add(new ExampleGeneric<>("String3", 45.67));

        System.out.println(genericList);
        for (int i = 0; i < genericList.size(); i++) {
            ExampleGeneric<?> item = genericList.get(i);
            System.out.println("Analyzing item at index " + i);

            Field[] fields = item.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    field.setAccessible(true);
                    Object value = field.get(item);
                    System.out.println("  Field value: " + value);
                } catch (IllegalAccessException ile) {
                    System.err.println("Could not access field: " + field.getName());
                }
                System.out.println("  Field name: " + field.getName());
                System.out.println("  Field type: " + field.getType());
            }
            System.out.println();
        }

//        try {
//            ExampleGeneric<String> stringGeneric = (ExampleGeneric<String>) genericList.get(2);
//            System.out.println(stringGeneric.getField2());
//        } catch (ClassCastException e) {
//            System.err.println("Runtime exception: " + e);
//        }
        Field[] fields = IllegalArgumentException.class.getFields();
        https://chatgpt.com/c/674d7f2a-bb70-800d-b89f-18576df5492a;
        System.out.println(fields.getClass());
        System.out.println(IllegalArgumentException.class);

    }
}





//List<Doctors> doctors = new ArrayList<>();
//        doctors.add(new Doctors("Jean Pierre","+764654654", Speciality.SURGEREON));
//
//        doctors.add(new Doctors("Louis Pierre","+764654654", Speciality.THERAPIST));
//
//        doctors.add(new Doctors("Carlos Pierre","+764654654", Speciality.SURGEREON));
//
//        doctors.add(new Doctors("Pete Pierre","+764654654", Speciality.CHILLERON));
//
//        doctors.add(new Doctors("Oliver Pierre","+764654654", Speciality.SURGEREON));
//
//        doctors.add(new Doctors("San Pierre","+764654654", Speciality.THERAPIST));
//Map<Speciality, String> map;
//map = doctors.stream().collect(Collectors.groupingBy(Doctors::getSpeciality,
//                               Collectors.mapping(Doctors::getName, Collectors.joining(", "))));
//
//        System.out.printf("Result:" + map);
//    }
//
//static class Doctors {
//    String name;
//    String phone;
//    Speciality speciality;
//
//    public Doctors(String name, String phone, Speciality speciality) {
//        this.name = name;
//        this.phone = phone;
//        this.speciality = speciality;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Speciality getSpeciality() {
//        return speciality;
//    }
//
//    public void setSpeciality(Speciality speciality) {
//        this.speciality = speciality;
//    }
//
//    @Override
//    public String toString() {
//        return "Doctors{" +
//                "name='" + name + '\'' +
//                ", phone='" + phone + '\'' +
//                ", speciality=" + speciality +
//                '}';
//    }
//}
//
//private enum Speciality {
//    SURGEREON, THERAPIST, CHILLERON
//}