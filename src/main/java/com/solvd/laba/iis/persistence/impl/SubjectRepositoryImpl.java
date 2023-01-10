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

    private static final String FIND_ALL_QUERY = "SELECT subjects.id as subject_id, subjects.name as subject_name FROM iis_schema.subjects";
    private static final String FIND_BY_ID_QUERY = "SELECT subjects.id as subject_id, subjects.name as subject_name FROM iis_schema.subjects WHERE id = ?";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.subjects (name) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.subjects WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.subjects SET name = ? WHERE id = ?";
    private final DataSource dataSource;

    @Override
    public List<Subject> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return SubjectRowMapper.mapSubjects(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all groups");
        }
    }

    @Override
    public Optional<Subject> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(SubjectRowMapper.mapSubject(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding subject by id = " + id);
        }
    }

    @Override
    public Subject create(Subject subject) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, subject.getName());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    subject.setId(key.getLong(1));
                }
                return subject;
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating subject");
        }
    }

    @Override
    public Subject save(Subject subject) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setString(1, subject.getName());
            statement.setLong(2, subject.getId());
            statement.executeUpdate();
            return subject;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving subject with id = " + subject.getId());
        }
    }

    @Override
    public void delete(Subject subject) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, subject.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting subject with id = " + subject.getId());
        }
    }
}
