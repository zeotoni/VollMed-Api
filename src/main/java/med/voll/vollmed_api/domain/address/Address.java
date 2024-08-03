package med.voll.vollmed_api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String number;
    private String complement;
    private String postalCode;
    private String city;
    private String state;

    public Address(AddressData data) {
        this.street = data.street();
        this.number = data.number();
        this.complement = data.complement();
        this.postalCode = data.postalCode();
        this.city = data.city();
        this.state = data.state();
    }
}
