package edu.miu.appointmentsystem.dto;

import edu.miu.appointmentsystem.domain.Appointment;
import edu.miu.appointmentsystem.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String username;
    private String password;
    private List<String> roles ;
    private List<Reservation> reservations;
    private List<Appointment> appointments;

}
