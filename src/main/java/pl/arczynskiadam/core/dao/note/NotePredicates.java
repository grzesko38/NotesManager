package pl.arczynskiadam.core.dao.note;

import java.util.Date;

import pl.arczynskiadam.core.model.note.QNoteVO;

import com.mysema.query.types.Predicate;

public class NotePredicates {

	public static Predicate from(final Date from) {
        QNoteVO note = QNoteVO.noteVO;
        return note.dateCreated.after(from);
    }
}
