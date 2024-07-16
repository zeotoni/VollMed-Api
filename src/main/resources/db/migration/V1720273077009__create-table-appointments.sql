CREATE TABLE IF NOT EXISTS appointments(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT fk_appointments_doctor_id FOREIGN KEY(doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_appointments_patient_id FOREIGN KEY(patient_id) REFERENCES patients(id)
);