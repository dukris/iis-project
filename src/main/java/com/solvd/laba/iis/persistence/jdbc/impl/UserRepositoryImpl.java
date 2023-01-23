package com.solvd.laba.iis.persistence.jdbc.impl;

import com.solvd.laba.iis.domain.UserInfo;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.UserRepository;
import com.solvd.laba.iis.persistence.jdbc.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_ALL_QUERY = """
            SELECT users_info.id as user_id,  users_info.name as user_name, users_info.surname as user_surname,
            users_info.email as user_email, users_info.password as user_password, users_info.role as user_role
            FROM users_info""";
    private static final String IS_EXIST_QUERY = "SELECT users_info.id as user_id FROM users_info WHERE email = ?";
    private static final String CREATE_QUERY = "INSERT INTO users_info (name, surname, email, password, role) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM users_info WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users_info SET name = ?, surname = ?, email = ?, password = ?, role = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<UserInfo> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return UserRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all users", ex);
        }
    }

    @Override
    public Optional<UserInfo> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + " WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(UserRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding user by id = " + id, ex);
        }
    }

    @Override
    public Optional<UserInfo> findByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + " WHERE email = ?")) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(UserRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding user by email = " + email, ex);
        }
    }

    @Override
    public boolean isExist(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(IS_EXIST_QUERY)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding user by email = " + email, ex);
        }
    }

    @Override
    public void create(UserInfo userInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userInfo.getName());
            statement.setString(2, userInfo.getSurname());
            statement.setString(3, userInfo.getEmail());
            statement.setString(4, userInfo.getPassword());
            statement.setString(5, userInfo.getRole().toString());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    userInfo.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating user", ex);
        }
    }

    @Override
    public void update(UserInfo userInfo) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, userInfo.getName());
            statement.setString(2, userInfo.getSurname());
            statement.setString(3, userInfo.getEmail());
            statement.setString(4, userInfo.getPassword());
            statement.setString(5, userInfo.getRole().toString());
            statement.setLong(6, userInfo.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving user with id = " + userInfo.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting user with id = " + id, ex);
        }
    }

}
