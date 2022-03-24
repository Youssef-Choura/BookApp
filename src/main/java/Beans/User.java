package Beans;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int userId;
    private String login;
    private String password;
    private String firstName;
    private String familyName;
    private String gender;
    private String grade;
    private String address;
    private String email;
    private String telephone;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws BeanException {
        //Checking Email validity
        final String regex = "^\\w+@\\w+\\.[a-z]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new BeanException("Wrong Email format");
        }
        else{
            this.email = email;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) throws BeanException {
        //Checking Phone number validity
        Pattern pattern = Pattern.compile("^([0-9]{8})$");
        if (pattern.matcher(telephone).matches()){
            this.telephone = telephone;
        }
        else {
            throw new BeanException("Phone number can only contain 8 numbers");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws BeanException {
        if (login.length()>10){
            throw new BeanException("Login maximum length is 10");
        }
        else {
            this.login = login;
        }
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws BeanException{
        if (password.length()<6){
            throw new BeanException("Password minimum length is 6");
        }
        else {
            this.password = password;
        }
    }
}
