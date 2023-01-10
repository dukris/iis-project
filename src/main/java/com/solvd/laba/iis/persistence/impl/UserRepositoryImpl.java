package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.User;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.UserRepository;
import com.solvd.laba.iis.persistence.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT users.id as user_id,  users.name as user_name, users.surname as user_surname,
            users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.users""";
    private static final String FIND_BY_ID_QUERY = """
            SELECT users.id as user_id,  users.name as user_name, users.surname as user_surname,
            users.email as user_email, users.password as user_password, users.role as user_role
            FROM iis_schema.users
            WHERE id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.users (name, surname, email, password, role) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.users WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.users SET name = ?, surname = ?, email = ?, password = ?, role = ? WHERE id = ?";
    private final DataSource dataSource;

    @Override
    public List<User> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return UserRowMapper.mapUsers(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all users");
        }
    }

    @Override
    public Optional<User> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(UserRowMapper.mapUser(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding user by id = " + id);
        }
    }

    @Override
    public User create(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    user.setId(key.getLong(1));
                }
                return user;
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating user");
        }
    }

    @Override
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
            throw new ResourceMappingException("Exception occurred while saving user with id = " + user.getId());
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting user with id = " + user.getId());
        }
    }
}
