package edu.miu.appointmentsystem.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.appointmentsystem.domain.enums.ReservationStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reservation_id")
	private Integer id;

	@NotEmpty(message = "{String.empty}")
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	@ManyToOne
	@JoinColumn(name="client_id")
	@JsonIgnore
	private User client;

	@ManyToOne
	@JoinColumn(name="appointment_id", updatable = false, insertable = false)
	@JsonIgnore
	private Appointment appointment;

	public Reservation(Appointment appointment, User client) {
		this.status = ReservationStatus.PENDING;
		this.client = this.client;
		this.appointment = appointment;
	}
}
