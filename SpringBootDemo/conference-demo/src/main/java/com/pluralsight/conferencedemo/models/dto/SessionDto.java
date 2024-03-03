package com.pluralsight.conferencedemo.models.dto;

import com.pluralsight.conferencedemo.models.entity.Speaker;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
  private Long sessionId;
  private String sessionName;
  private String sessionDescription;
  private Integer sessionLength;
  private List<Speaker> speakers;

}
