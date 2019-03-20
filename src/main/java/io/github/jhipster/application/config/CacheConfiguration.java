package io.github.jhipster.application.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.School.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SchoolYear.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SchoolSchoolYear.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.LeaveHoliDay.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PeriodType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Period.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Cycle.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Level.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Diploma.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ClassRoom.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Subject.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.RoomType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Room.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Days.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Program.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Rubric.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.RubricAmount.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Actor.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ActorName.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Address.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IdentifierType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ActorIdentifierNumber.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Image.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Student.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Payment.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Regimen.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Enrolment.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Responsible.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.RelationshipType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ActorRelationship.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Teacher.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Teacher.class.getName() + ".subjects", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TeacherSubjectSchoolYear.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Salary.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Job.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Staff.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Staff.class.getName() + ".staffJobs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.StaffJob.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ClassSessionType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.ClassSession.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.EvaluationType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Evaluation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Evaluation.class.getName() + ".classRooms", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.StudentEvaluation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.EvaluationMode.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.StudentMissingSession.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.TeacherMissingSession.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.AskingPermission.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PermissionAgreement.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.StudentDiploma.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
