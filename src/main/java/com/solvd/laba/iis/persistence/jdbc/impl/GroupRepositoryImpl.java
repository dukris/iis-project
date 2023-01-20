package com.solvd.laba.iis.persistence.jdbc.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.criteria.GroupSearchCriteria;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.persistence.jdbc.mapper.GroupRowMapper;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {

    private static final String FIND_ALL_QUERY = "SELECT DISTINCT groups.id as group_id, groups.number as group_number FROM iis.groups ";
    private static final String IS_EXIST_QUERY = "SELECT groups.id as group_id FROM iis.groups WHERE number = ?";
    private static final String CREATE_QUERY = "INSERT INTO iis.groups (number) VALUES (?)";
    private static final String DELETE_QUERY = "DELETE FROM iis.groups WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE iis.groups SET number = ? WHERE id = ?";

    private final DataSource dataSource;

    @Override
    public List<Group> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return GroupRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all groups", ex);
        }
    }

    @Override
    public Optional<Group> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + "WHERE id = ?")) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(GroupRowMapper.mapRow(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding group by id = " + id, ex);
        }
    }

    @Override
    public List<Group> findByTeacherAndSubject(Long teacherId, Long subjectId) {
        String joinQuery = "LEFT JOIN iis.lessons ON groups.id = lessons.group_id ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY + joinQuery + "WHERE lessons.teacher_id = ? AND lessons.subject_id = ?")) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                return GroupRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding group by teacher's id = " + teacherId + " and subject's id = " + subjectId, ex);
        }
    }

    @Override
    public boolean isExist(Integer number) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(IS_EXIST_QUERY)) {
            statement.setInt(1, number);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding group by number = " + number, ex);
        }
    }

    @Override
    public List<Group> findByCriteria(Long teacherId, GroupSearchCriteria groupSearchCriteria) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(updateQuery(teacherId, groupSearchCriteria))) {
                return GroupRowMapper.mapRows(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding groups by teacher's id = " + teacherId, ex);
        }
    }

    private String updateQuery(Long teacherId, GroupSearchCriteria groupSearchCriteria) {
        String joinQuery = "LEFT JOIN lessons ON (lessons.group_id = groups.id) ";
        return Objects.nonNull(groupSearchCriteria.getSubjectId()) ?
                FIND_ALL_QUERY + joinQuery + "WHERE lessons.teacher_id = " + teacherId + " AND lessons.subject_id = " + groupSearchCriteria.getSubjectId() :
                FIND_ALL_QUERY + joinQuery + "WHERE lessons.teacher_id = " + teacherId;
    }

    @Override
    public void create(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, group.getNumber());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    group.setId(key.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating group", ex);
        }
    }

    @Override
    public void update(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, group.getNumber());
            statement.setLong(2, group.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving group with id = " + group.getId(), ex);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting group with id = " + id, ex);
        }
    }

}
