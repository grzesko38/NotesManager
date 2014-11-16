package pl.arczynskiadam.core.dao.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.arczynskiadam.core.model.note.NoteVO;

public interface NoteRepository extends JpaRepository<NoteVO, Integer> {}
