package med.voll.vollmed_api.domain.patient;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.vollmed_api.domain.address.Address;

@Table(name = "patients")
@Entity(name = "Patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;

    @Embedded
    private Address address;

    private boolean active;

    public Patient(PatientRegistrationData data) {
        this.name = data.name();
        this.email = data.email();
        this.cpf = data.cpf();
        this.phone = data.phone();
        this.address = new Address(data.address());
        this.active = true;
    }
}
