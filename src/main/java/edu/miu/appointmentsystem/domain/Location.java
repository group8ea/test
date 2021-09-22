package edu.miu.appointmentsystem.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Embeddable
public class Location {

	@Size(min = 5, max = 5, message = "{Size.Location.zipcode.validation}")
	@Digits(integer = 5, fraction = 0)
	private String zipcode;

	@NotEmpty(message = "{String.empty}")
	private String street;

	private String city;

	@Size(min = 2, max = 2, message = "{Size.Location.state.validation}")
	private String state;

	private String country;

}
