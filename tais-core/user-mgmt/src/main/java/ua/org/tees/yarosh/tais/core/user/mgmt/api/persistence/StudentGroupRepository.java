package ua.org.tees.yarosh.tais.core.user.mgmt.api.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.org.tees.yarosh.tais.core.common.models.Registrant;
import ua.org.tees.yarosh.tais.core.common.models.StudentGroup;

import java.util.List;

public interface StudentGroupRepository extends JpaRepository<StudentGroup, String> {
    @Query("select r from Registrant r where r.group.id = :id")
    List<Registrant> findRegistrantsByStudentGroup(@Param("id") String id);
}
