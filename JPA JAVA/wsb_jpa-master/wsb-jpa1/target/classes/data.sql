-- Wstawianie adresów
INSERT INTO address (id, city, address_line1, address_line2, postal_code)
VALUES
    (901, 'xx', 'yy', 'city', '60-400'),
    (902, 'Warszawa', 'ul. Koszykowa 75', '', '00-662'),
    (903, 'Warszawa', 'ul. Akacjowa 14', ' lok. 5', '00-678'),
    (904, 'Krakow', 'ul. Długa 12', 'lok. 3', '31-147'),
    (905, 'Krakow', 'ul.Długa 30', 'lok.6', '31-147');

-- Wstawianie lekarzy
INSERT INTO doctor (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
VALUES
    (10, 'Paulina', 'Nowak', '123456789', 'p.nowak@clinic.com', 'DOC001', 'DERMATOLOGIST', 902),
    (11, 'Ewa', 'Wójcik', '444555666', 'e.wojcik@clinicl.com', 'DOC002', 'OCULIST', 903);

-- Wstawianie pacjentów
INSERT INTO patient (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id, insured)
VALUES
    (20, 'Jan', 'Kowalski', '987654321', 'jan.kowalski@patient.com', 'PAT001', '1990-05-20', 904, 1),
    (21, 'Anna', 'Nowak', '987654321', 'anna.nowak@patient.com', 'PAT002', '1992-09-22', 905, 0);

-- Wstawianie wizyt
INSERT INTO visit (id, description, time, doctor_id, patient_id)
            VALUES
            (31, 'Badanie kontrolne', '2024-04-10 10:00:00', 10, 20),
            (32, 'Konsultacja okulistyczna', '2024-04-12 14:30:00', 11, 21);

-- wstawianie zabiegów medycznych (po wstawieniu wizyt)
INSERT INTO medical_treatment (id, description, type, visit_id)
            VALUES
            (41, 'Testowe badanie', 'RTG', 31),
            (42, 'Zlecenie badanie', 'EKG', 32);
