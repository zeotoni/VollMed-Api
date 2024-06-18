package med.voll.vollmed_api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
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

    public void updateData(AddressData data) {
        if (data.street() != null) {
            this.street = data.street();
        }

        if (data.number() != null) {
            this.number = data.number();
        }

        if (data.complement() != null) {
            this.complement = data.complement();
        }

        if (data.postalCode() != null) {
            this.postalCode = data.postalCode();
        }

        if (data.city() != null) {
            this.city = data.city();
        }

        if (data.state() != null) {
            this.state = data.state();
        }
    }
}
