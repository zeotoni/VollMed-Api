package med.voll.vollmed_api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pagination);

    @Query("""
            select d from Doctor d
            where
            d.active = true
            and
            d.specialty = :specialty
            and
            d.id not in(
                select a.doctor.id from Appointment a
                where
                a.date = :date
            )
            order by rand()
            limit 1
            """)
    Doctor chooseAvailableRandomDoctor(Specialty specialty, LocalDateTime date);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :id
            """)
    Boolean findActiveById(Long id);

    @Query("""
            select d 
            from Doctor d 
            where 
            d.id = :id 
            and 
            d.active = true
            """)
    Optional<Doctor> findByIdAndActive(Long id);
}