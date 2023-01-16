package com.solvd.laba.iis.persistence;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class PersistenceConfig {

    private final DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public GroupRepository groupRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(GroupRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public LessonRepository lessonRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(LessonRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public MarkRepository markRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(MarkRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public StudentRepository studentRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(StudentRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public SubjectRepository subjectRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(SubjectRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public TeacherRepository teacherRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(TeacherRepository.class);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application", name = "repository", havingValue = "mybatis")
    public UserRepository userRepository() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(UserRepository.class);
    }

}
