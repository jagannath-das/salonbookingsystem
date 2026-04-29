package com.proj.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User 
{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String fullname;
@NotNull
@Email
private String emailid;
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
@NotNull
private String password;
@NotNull
private String phone;
@JdbcTypeCode(SqlTypes.JSON)
@Column(columnDefinition = "json")
private List<String> role;
@CreationTimestamp
private LocalDateTime created;
@UpdateTimestamp
private LocalDateTime updated;

@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)

private List<Appointment> appointments;
  


}
