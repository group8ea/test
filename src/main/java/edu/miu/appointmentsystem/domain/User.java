package edu.miu.appointmentsystem.domain;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Users")
@SecondaryTable(name = "Account")
@NoArgsConstructor
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@NotEmpty(message = "Enter the first name")
	private String firstName;

	@NotEmpty(message = "Enter the last name")
	private String lastName;

	@Email
	private String email;

	private String gender;

	@Column(table = "Account")
	@NotBlank(message = "{String.empty}")
	private String username;

	@Column(table = "Account")
	@NotBlank(message = "{String.empty}")
	private String password;

	@ElementCollection
	@CollectionTable(name = "Roles")
	@JsonIgnore
	private List<String> roles = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="client_id")
	@OrderColumn(name="sequence")
	@JsonIgnore
	private List<Reservation> reservations;


	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="provider_id")
	@OrderColumn(name="sequence")
	@JsonIgnore
	private List<Appointment> appointments;

	public boolean isRole(String roleName) {
		return this.getRoles().stream().anyMatch(r -> r.equals(roleName));
	}

	public boolean isClient() {
		return isRole("Client");
	}
	
	public boolean isAdmin() {
		return isRole("Admin");
	}
	
	public boolean isProvider() {
		return isRole("Provider");
	}

}
