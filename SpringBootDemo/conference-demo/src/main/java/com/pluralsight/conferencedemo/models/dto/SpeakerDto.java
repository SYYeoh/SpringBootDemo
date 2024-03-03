package com.pluralsight.conferencedemo.models.dto;

import com.pluralsight.conferencedemo.models.entity.Session;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeakerDto {

  private Long speakerId;
  private String firstName;
  private String lastName;
  private String title;
  private String company;
  private String speakerBio;
  private byte[] speakerPhoto;
  private List<Session> sessions;

}
