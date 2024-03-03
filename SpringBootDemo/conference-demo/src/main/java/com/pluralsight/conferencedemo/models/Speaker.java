package com.pluralsight.conferencedemo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import java.sql.Types;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "speakers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Speaker {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long speakerId;

  private String firstName;
  private String lastName;
  private String title;
  private String company;
  private String speakerBio;

  @Lob
  /*@Type(type = "org.hibernate.type.BinaryType")*/
  @JdbcTypeCode(Types.VARBINARY)
  private byte[] speakerPhoto;

  @ManyToMany(mappedBy = "speakers")
  @JsonIgnore
  private List<Session> sessions;
}
