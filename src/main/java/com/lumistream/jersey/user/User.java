package com.lumistream.jersey.user;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Base64;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

public class User {

    private static final Integer APP1 = 1;
    private static final Random RANDOM = new SecureRandom();

    public static void addUser(String username, String password, String url) {
        String salt = User.getSalt();
        String encrypted_pass = encryptPass(password, salt);
        String sql = "INSERT INTO credential(name, password, salt) VALUES(" + username + ", " + encrypted_pass + ", "
                + salt + ")";

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(String username, String url) {

        if (UserSupervisor.isUserLoggedIn(username) == APP1) {
            String sql = "DELETE FROM credential WHERE name = " + username;

            try (Connection conn = DriverManager.getConnection(url)) {
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static String encryptPass(String pass, String salt) {
        String p_salted = pass + salt;
        String encripted_pass = hash(p_salted);
        return encripted_pass;
    }

    public static String getSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        String s_salt = Base64.getEncoder().encodeToString(salt);
        return s_salt;
    }

    public static String hash(String pass) {
        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        byte[] passwordBytes = pass.getBytes(StandardCharsets.UTF_8);
        byte[] hash = new byte[32];

        Argon2Parameters.Builder builder = new Argon2Parameters.Builder();
        builder.withIterations(3).withMemoryAsKB(16 * 1024).withParallelism(4);
        Argon2Parameters parameters = builder.build();

        generator.init(parameters);
        generator.generateBytes(passwordBytes, hash);

        String s_hash = Base64.getEncoder().encodeToString(hash);
        return s_hash;
    }

    public static Boolean Authenticate(String name, String password, String url) {
        String query = "SELECT (password, salt) from credential where name = " + name;

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement pstm = conn.prepareStatement(query);
            ResultSet res = pstm.executeQuery();
            String hash = res.getString("password");
            String salt = res.getString("salt");
            String salted_pass = password + salt;
            String hashed_pass = User.hash(salted_pass);
            return hashed_pass.equals(hash);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
