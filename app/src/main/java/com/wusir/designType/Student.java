package com.wusir.designType;

import android.webkit.WebResourceResponse;

import java.util.Hashtable;


/**
 * Created by zy on 2018/3/2.
 */

public class Student {
    private int age;
    private String name;
    private String sex;
    private String address;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    private Student() {

    }

    private Student(Student origin) {
        this.age = origin.age;
        this.name = origin.name;
        this.sex = origin.sex;
        this.address = origin.address;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public static class Builder {
        private Student target;

        public Builder() {
            target = new Student();
        }

        public Builder age(int age) {
            target.age = age;
            return this;
        }

        public Builder name(String name) {
            target.name = name;
            return this;
        }

        public Builder sex(String sex) {
            target.sex = sex;
            return this;
        }

        public Builder address(String address) {
            target.address = address;
            return this;
        }

        public Student build() {
            return new Student(target);
        }
    }

    public static void main(String[] args) {
        Student s = new Student.Builder().age(11).name("xx").sex("man").address("xx").build();
        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("zz", 1);
        table.put("xx", 2);
        table.put("cc", 3);
        table.remove("cc");
        System.out.print(table.get("xx"));
    }
}
