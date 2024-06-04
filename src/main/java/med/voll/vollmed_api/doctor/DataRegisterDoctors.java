package med.voll.vollmed_api.doctor;

import med.voll.vollmed_api.address.AddressData;

public record DataRegisterDoctors(String name, String email, String crm, Specialty specialty, AddressData address) {
}
