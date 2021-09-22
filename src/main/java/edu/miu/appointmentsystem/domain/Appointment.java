package edu.miu.appointmentsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.appointmentsystem.domain.enums.AppointmentStatus;
import edu.miu.appointmentsystem.domain.enums.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Appointment {
 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="appointment_id")
    private Integer id;

	@Future(message = "{Future.Appointment.appointmentDate.validation}")
    @NotBlank(message = "{String.empty}")
    private LocalDate appointmentDate;

	@NotBlank(message = "{String.empty}")
    private LocalTime startTime;

    @NotEmpty(message = "{String.empty}")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Embedded
    @Valid
    private Location location;

    @ManyToOne
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JoinColumn(name="provider_id", updatable = false, insertable = false)
    @JsonIgnore
    private User provider;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="appointment_id")
    @OrderColumn(name="sequence")
    @JsonIgnore
    private List<Reservation> reservations;

    public Appointment(LocalDate appointmentDate, LocalTime startTime,
                       Location location, Category category,
                       User provider, AppointmentStatus status) {
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.location = location;
        this.category = category;
        this.provider = provider;
        this.reservations = new ArrayList<>();
        this.status = AppointmentStatus.OPENED;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }



}

