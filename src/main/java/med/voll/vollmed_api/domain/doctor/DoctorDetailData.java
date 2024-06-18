package med.voll.vollmed_api.domain.doctor;

import med.voll.vollmed_api.domain.address.Address;

public record DoctorDetailData(Long id, String name, String email, String crm, String phone, Specialty specialty, Address address) {
    public DoctorDetailData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhone(), doctor.getSpecialty(), doctor.getAddress());
    }
}
