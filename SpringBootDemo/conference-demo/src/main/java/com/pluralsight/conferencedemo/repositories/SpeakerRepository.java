package com.pluralsight.conferencedemo.repositories;

import com.pluralsight.conferencedemo.models.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
