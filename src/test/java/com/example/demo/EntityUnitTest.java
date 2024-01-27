    package com.example.demo;

    import static org.assertj.core.api.Assertions.assertThat;
    import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

    import com.example.demo.entities.*;

    @DataJpaTest
    class EntityUnitTest {
        /** TODO
         * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
         * Make sure you are as exhaustive as possible. Coverage is checked ;)
         */
        @Autowired
        private TestEntityManager entityManager;

        private Doctor doctor;
        private Patient patient;
        private Room room;
        private Appointment appointment;

        @BeforeEach
        void setUp() {
            doctor = new Doctor("John", "Doe", 35, "john.doe@example.com");
            patient = new Patient("Alice", "Smith", 28, "alice.smith@example.com");
            room = new Room("Cardiology");

            appointment = new Appointment(patient, doctor, room,
                    LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        }

        @Test
        void saveAndRetrieveDoctor() {
            Doctor savedDoctor = entityManager.persistAndFlush(doctor);
            Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
            assertThat(retrievedDoctor).isEqualTo(savedDoctor);
            assertThat(retrievedDoctor.getFirstName()).isEqualTo(doctor.getFirstName());
        }

        @Test
        void saveAndRetrievePatient() {
            Patient savedPatient = entityManager.persistAndFlush(patient);
            Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());

            assertThat(retrievedPatient).isEqualTo(savedPatient);
            assertThat(retrievedPatient.getFirstName()).isEqualTo(patient.getFirstName());
        }

        @Test
        void saveAndRetrieveRoom() {
            Room savedRoom = entityManager.persistAndFlush(room);
            Room retrievedRoom = entityManager.find(Room.class, savedRoom.getRoomName());

            assertThat(retrievedRoom).isEqualTo(savedRoom);
            assertThat(retrievedRoom.getRoomName()).isEqualTo(room.getRoomName());
        }

        @Test
        void saveAndRetrieveAppointment() {
            Appointment savedAppointment = entityManager.persistAndFlush(appointment);
            Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
            assertThat(retrievedAppointment).isEqualTo(savedAppointment);
            assertThat(retrievedAppointment.getPatient()).isEqualTo(appointment.getPatient());
            assertThat(retrievedAppointment.getDoctor()).isEqualTo(appointment.getDoctor());
            assertThat(retrievedAppointment.getRoom()).isEqualTo(appointment.getRoom());
        }

        @Test
        void doctorValidationFirstName() {
            doctor.setFirstName(null);
            assertThatThrownBy(() -> entityManager.persistAndFlush(doctor))
                    .isInstanceOf(javax.validation.ConstraintViolationException.class);
        }

        @Test
        void doctorValidationAge() {
            doctor.setAge(-5);
            assertThatThrownBy(() -> entityManager.persistAndFlush(doctor))
                    .isInstanceOf(javax.validation.ConstraintViolationException.class);
        }
        @Test
        void patientValidationEmailFormat() {
            patient.setEmail("invalid_email");
            assertThatThrownBy(() -> entityManager.persistAndFlush(patient))
                    .isInstanceOf(javax.validation.ConstraintViolationException.class);
        }

        @Test
        void roomValidationUniqueName() {
            Room room1 = new Room("Cardiology");
            Room room2 = new Room("Cardiology");
            entityManager.persistAndFlush(room1);
            assertThatThrownBy(() -> entityManager.persistAndFlush(room2))
                    .isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class);
        }

        @Test
        void appointmentValidationEndTime() {
            appointment.setFinishesAt(appointment.getStartsAt().minusHours(1));
            assertThatThrownBy(() -> entityManager.persistAndFlush(appointment))
                    .isInstanceOf(javax.validation.ConstraintViolationException.class);
        }

    }
