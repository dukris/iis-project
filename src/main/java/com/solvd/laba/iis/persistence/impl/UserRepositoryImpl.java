package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Role;
import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.domain.exception.DaoException;
import com.solvd.laba.iis.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSource dataSource;
    private static final String FIND_ALL_QUERY = """
            SELECT users.id,  users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.users;""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT users.id, users.name, users.surname, users.email, users.password, users.role
            FROM iis_schema.users
            WHERE id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.users (name, surname, email, password, role) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.users WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.users SET name = ?, surname = ?, email = ?, password = ?, role = ? WHERE id = ?";

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            List<User> users = new ArrayList<>();
            ResultSet rs = statement.executeQuery(FIND_ALL_QUERY);
            while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong(1));
                    user.setName(rs.getString(2));
                    user.setSurname(rs.getString(3));
                    user.setEmail(rs.getString(4));
                    user.setPassword(rs.getString(5));
                    user.setRole(Role.valueOf(rs.getString(6).toUpperCase()));
                    users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding all users");
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            User user = new User();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(Role.valueOf(rs.getString(6).toUpperCase()));
            }
            return Optional.of(user);
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while finding user by id = " + id);
        }
    }

    @Override
    @Transactional
    public User create(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            if(key.next()){
                user.setId(key.getLong(1));
            }
            return user;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while creating user");
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while saving user with id = " + user.getId());
        }
    }

    @Override
    @Transactional
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException("Exception occurred while deleting user with id = " + user.getId());
        }
    }
}
