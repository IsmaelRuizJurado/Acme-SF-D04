
package acme.features.auditor.codeAudits;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.entities.audit_records.MarkType;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditsRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudits c WHERE c.auditor.userAccount.id = :id")
	Collection<CodeAudits> findCodeAuditsByAuditorId(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.id =:id")
	CodeAudits findCodeAuditsById(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits = :codeAudits ")
	Collection<AuditRecords> findAuditRecordsByCodeAudits(CodeAudits codeAudits);

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findOneAuditorById(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.code = :code")
	CodeAudits findCodeAuditsByCode(String code);

	@Query("SELECT count(ar) FROM AuditRecords ar WHERE ar.id=:id and ar.mark=:mark")
	Optional<Integer> findNumAuditsByMark(int id, MarkType mark);

	default MarkType AuditMark(final Integer id, final int APLUS, final int A, final int B, final int C, final int F, final int FMINUS) {

		if (APLUS > A && APLUS > B && APLUS > C && APLUS > F && APLUS > FMINUS)
			return MarkType.APLUS;
		else if (A > APLUS && A > B && A > C && A > F && A > FMINUS)
			return MarkType.A;
		else if (B > APLUS && B > A && B > C && B > F && B > FMINUS)
			return MarkType.B;
		else if (C > APLUS && C > A && C > B && C > F && C > FMINUS)
			return MarkType.C;
		else if (F > APLUS && F > A && F > B && F > C && F > FMINUS)
			return MarkType.F;
		else
			return MarkType.FMINUS;

	}

	default Map<MarkType, Integer> auditRecordsPerMark(final Collection<AuditRecords> auditrecords) {
		final Map<MarkType, Integer> res = new HashMap<>();
		for (final AuditRecords a : auditrecords) {
			final int APLUS = this.findNumAuditsByMark(a.getId(), MarkType.APLUS).get();
			final int A = this.findNumAuditsByMark(a.getId(), MarkType.A).get();
			final int B = this.findNumAuditsByMark(a.getId(), MarkType.B).get();
			final int C = this.findNumAuditsByMark(a.getId(), MarkType.C).get();
			final int F = this.findNumAuditsByMark(a.getId(), MarkType.F).get();
			final int FMINUS = this.findNumAuditsByMark(a.getId(), MarkType.FMINUS).get();
			final MarkType m = this.AuditMark(a.getId(), APLUS, A, B, C, F, FMINUS);
			if (!res.containsKey(m))
				res.put(m, 1);
			else
				res.put(m, res.get(m) + 1);
		}
		return res;
	}
}
