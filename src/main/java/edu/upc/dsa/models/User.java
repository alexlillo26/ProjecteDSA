package edu.upc.dsa.models;

import edu.upc.dsa.annotations.CustomAnnotation;
import edu.upc.dsa.util.RandomUtils;

public class User {
    @CustomAnnotation("id_exclude")
    private String id;

    private String username;
    private String password;
    private String isAdmin;
    private String fullName;
    private String email;
    private int age;
    private String profilePicture;
    private int coins;
    private String partida;
    private float JumpPotions;
    private float SpeedPotions;
    private float MaxHealthPotions;
    private float AttackSpeedPotions;

    public float getAttackSpeedPotions() {
        return AttackSpeedPotions;
    }

    public void setAttackSpeedPotions(float attackSpeedPotions) {
        AttackSpeedPotions = attackSpeedPotions;
    }

    public float getMaxHealthPotions() {
        return MaxHealthPotions;
    }

    public void setMaxHealthPotions(float maxHealthPotions) {
        MaxHealthPotions = maxHealthPotions;
    }

    public float getSpeedPotions() {
        return SpeedPotions;
    }

    public void setSpeedPotions(float speedPotions) {
        SpeedPotions = speedPotions;
    }

    public float getJumpPotions() {
        return JumpPotions;
    }

    public void setJumpPotions(float jumpPotions) {
        JumpPotions = jumpPotions;
    }




    public User() {
        this.setId(RandomUtils.getId());
        this.isAdmin = "notadmin";
        this.coins = 0;
    }

    public User(String username, String password, String isAdmin) {
        this();
        this.setUsername(username);
        this.setPassword(password);
        this.isAdmin = isAdmin;
    }

    public User(String username, String password, String isAdmin, String fullName, String email, int age, String profilePicture, int coins, String partida, float jumpPotions, float speedPotions, float maxHealthPotions, float attackSpeedPotions) {
        this(username, password, isAdmin);
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.profilePicture = profilePicture;
        this.coins = coins;
        this.partida = partida;
        this.JumpPotions = jumpPotions;
        this.SpeedPotions = speedPotions;
        this.MaxHealthPotions = maxHealthPotions;
        this.AttackSpeedPotions = attackSpeedPotions;
    }



    // Getters and Setters
    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getPartida() {
        return partida;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }

    public boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }



    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", fullName=" + fullName +
                ", email=" + email + ", age=" + age + ", profilePicture=" + profilePicture + ", coins=" + coins + "]";
    }
}