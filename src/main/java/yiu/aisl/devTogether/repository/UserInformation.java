package yiu.aisl.devTogether.repository;

import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

public interface UserInformation {
    Long getId();
    String getEmail();
    String getNickname();
    RoleCategory getRole();
    String getBirth();
    GenderCategory getGender();
    Integer getAge();
    String getMethod();
    String getLocation1();
    String getLocation2();
    String getLocation3();
    String getSubject1();
    String getSubject2();
    String getSubject3();
    String getSubject4();
    String getSubject5();
}
