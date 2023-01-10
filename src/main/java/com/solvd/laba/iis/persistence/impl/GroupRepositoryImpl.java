package com.solvd.laba.iis.persistence.impl;

import com.solvd.laba.iis.domain.Group;
import com.solvd.laba.iis.domain.exception.ResourceMappingException;
import com.solvd.laba.iis.persistence.GroupRepository;
import com.solvd.laba.iis.persistence.mapper.GroupRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {

    private static final String FIND_ALL_QUERY = "SELECT groups.id as group_id, groups.number as group_number FROM iis_schema.groups";
    private static final String FIND_BY_ID_QUERY = "SELECT groups.id as group_id, groups.number as group_number FROM iis_schema.groups WHERE id = ?";
    private static final String FIND_BY_TEACHER_QUERY = """
            SELECT groups.id as group_id, groups.number as group_number
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            WHERE lessons.teacher_id = ?""";
    private static final String FIND_BY_TEACHER_AND_SUBJECT_QUERY = """
            SELECT groups.id  as group_id, groups.number  as group_number
            FROM iis_schema.lessons
            LEFT JOIN iis_schema.groups ON (lessons.group_id = groups.id)
            WHERE lessons.teacher_id = ? AND lessons.subject_id = ?""";
    private static final String CREATE_QUERY = "INSERT INTO iis_schema.groups (number) VALUES(?)";
    private static final String DELETE_QUERY = "DELETE FROM iis_schema.groups WHERE id = ?";
    private static final String SAVE_QUERY = "UPDATE iis_schema.groups SET number = ? WHERE id = ?";
    private final DataSource dataSource;

    @Override
    public List<Group> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(FIND_ALL_QUERY)) {
                return GroupRowMapper.mapGroups(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding all groups");
        }
    }

    @Override
    public Optional<Group> findById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next() ? Optional.of(GroupRowMapper.mapGroup(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding group by id = " + id);
        }
    }

    @Override
    public List<Group> findByTeacher(long teacherId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_QUERY)) {
            statement.setLong(1, teacherId);
            try (ResultSet rs = statement.executeQuery()) {
                return GroupRowMapper.mapGroups(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding groups by teacher's id = " + teacherId);
        }
    }

    @Override
    public List<Group> findByTeacherAndSubject(long teacherId, long subjectId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_TEACHER_AND_SUBJECT_QUERY)) {
            statement.setLong(1, teacherId);
            statement.setLong(2, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                return GroupRowMapper.mapGroups(rs);
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while finding groups by teacher's id = " + teacherId + " and subject's id = " + subjectId);
        }
    }

//    private String updateQuery(GroupSearchCriteria groupSearchCriteria) {
//        String query = groupSearchCriteria.getSubjectId() != 0 ?
//                "WHERE lessons.teacher_id = ? AND lessons.subject_id = ?" : "WHERE lessons.teacher_id = ?";
//        return String.format("%s", query);
//    }

    @Override
    public Group create(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_QUERY,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, group.getNumber());
            statement.executeUpdate();
            try (ResultSet key = statement.getGeneratedKeys()) {
                if (key.next()) {
                    group.setId(key.getLong(1));
                }
                return group;
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while creating group");
        }
    }

    @Override
    public Group save(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY)) {
            statement.setInt(1, group.getNumber());
            statement.setLong(2, group.getId());
            statement.executeUpdate();
            return group;
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while saving group with id = " + group.getId());
        }
    }

    @Override
    public void delete(Group group) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, group.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Exception occurred while deleting group with id = " + group.getId());
        }
    }
}
