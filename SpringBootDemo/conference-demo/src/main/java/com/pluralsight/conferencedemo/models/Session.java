package com.pluralsight.conferencedemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "sessions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sessionId;
  private String sessionName;
  private String sessionDescription;
  private Integer sessionLength;

  @ManyToMany
  @JoinTable(
      name = "session_speakers",
      joinColumns = @JoinColumn(name = "sessionId"),
      inverseJoinColumns = @JoinColumn(name = "speaker_id"))
  private List<Speaker> speakers;
}
