package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Escalation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalationDao extends JpaRepository<Escalation, Long> {
}
