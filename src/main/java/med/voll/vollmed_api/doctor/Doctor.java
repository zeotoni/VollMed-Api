package med.voll.vollmed_api.doctor;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.vollmed_api.address.Address;


@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String crm;
    private String phone;

    @Enumerated
    private Specialty specialty;

    @Embedded
    private Address address;

    private boolean active;

    public Doctor(DoctorRegistrationData data) {
        this.name = data.name();
        this.email = data.email();
        this.crm = data.crm();
        this.phone = data.phone();
        this.specialty = data.specialty();
        this.address = new Address(data.address());
        this.active = true;
    }

    public void updateData(DoctorUpdateData data) {
        if (data.name() != null) {
            this.name = data.name();
        }

        if (data.phone() != null) {
            this.phone = data.phone();
        }

        if (data.address() != null) {
            this.address.updateData(data.address());
        }
    }

    public void delete() {
        this.active = false;
    }
}
