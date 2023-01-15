package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Subject;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.SubjectRepository;
import com.solvd.laba.iis.persistence.mapper.SubjectRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepository {

    private static final String FIND_ALL_QUERY = "SELECT subjects.id as subject_id, subjects.name as subject_name FROM subjects ";
    private static final String IS_EXIST_QUERY = "SELECT subjects.id as subject_id FROM subjects WHERE name = ?";
    private static final String CREATE_QUERY = "INSERT INTO subjects (name) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM subjects WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE subjects SET name = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<Subject> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return SubjectRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all groups", ex);
        }
    }

    @Override
    public Optional<Subject> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(SubjectRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding subject by id = " + id, ex);
        }
    }

    @Override
    public boolean isExist(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(IS_EXIST_QUERY)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding subject by name = " + name, ex);
        }
    }

    @Override
    public void create(Subject subject) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subject.getName());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    subject.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating subject", ex);
        }
    }

    @Override
    public void update(Subject subject) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, subject.getName());
            statement.setLong(2, subject.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving subject with id = " + subject.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting subject with id = " + id, ex);
        }
    }

}
