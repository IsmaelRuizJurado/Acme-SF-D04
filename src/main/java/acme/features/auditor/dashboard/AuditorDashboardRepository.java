
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;
import acme.entities.code_audits.CodeAuditsType;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT a FROM Auditor a WHERE a.userAccount.id = :id")
	Auditor findOneAuditorByUserAccountId(int id);

	@Query("SELECT (SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudits.id=c.id) FROM CodeAudits c WHERE c.auditor.id=:id")
	Collection<Double> findNumAuditRecordsByAuditorId(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits.auditor.userAccount.id = :id")
	Collection<AuditRecords> findAuditRecordsByAuditorId(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.auditor.userAccount.id = :id")
	Collection<CodeAudits> findCodeAuditsByAuditorId(int id);

	@Query("SELECT avg ( SELECT count(ar) FROM AuditRecords ar WHERE ar.codeAudits.id = c.id) FROM CodeAudits c WHERE c.auditor.id =:id ")
	Optional<Double> findAverageNumOfAuditRecords(int id);

	@Query("SELECT max (SELECT count(ar) FROM AuditRecords ar WHERE ar.codeAudits.id = c.id) FROM CodeAudits c WHERE c.auditor.id =:id ")
	Optional<Double> findMaxNumOfAuditRecords(int id);

	@Query("SELECT min( SELECT count(ar) FROM AuditRecords ar WHERE ar.codeAudits.id = c.id) FROM CodeAudits c WHERE c.auditor.id =:id ")
	Optional<Double> findMinNumOfAuditRecords(int id);

	/*
	 * @Query("SELECT STDDEV (SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudits.id = c.id) FROM CodeAudits c WHERE c.auditor.id = :id")
	 * Optional<Double> findDevNumOfAuditRecords(@Param("id") int id);
	 */
	@Query("SELECT count(c) FROM CodeAudits c WHERE c.auditor.id=:id and c.type =:type ")
	Optional<Integer> findCodeAuditByType(int id, CodeAuditsType type);

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits = :c ")
	List<AuditRecords> findAuditRecordsByCodeAudits(CodeAudits c);

}
