package med.voll.vollmed_api.domain.address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {

    public void updateAddress(Address address, AddressData addressData) {
        if (addressData.street() != null) {
            address.setStreet(addressData.street());
        }
        if (addressData.number() != null) {
            address.setNumber(addressData.number());
        }
        if (addressData.complement() != null) {
            address.setComplement(addressData.complement());
        }
        if (addressData.postalCode() != null) {
            address.setPostalCode(addressData.postalCode());
        }
        if (addressData.city() != null) {
            address.setCity(addressData.city());
        }
        if (addressData.state() != null) {
            address.setState(addressData.state());
        }
    }
}
