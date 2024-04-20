
package acme.features.auditor.audit_records;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_records.AuditRecords;
import acme.entities.code_audits.CodeAudits;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT a FROM AuditRecords a WHERE a.codeAudits.auditor.userAccount.id = :id")
	Collection<AuditRecords> findAuditRecordByAuditorId(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.id = :id")
	AuditRecords findAuditRecordsById(int id);

	@Query("SELECT a FROM AuditRecords a WHERE a.code = :code")
	AuditRecords findAuditRecordsByCode(String code);

	@Query("select a from Auditor a where a.id  = :id")
	Auditor findOneAuditorById(int id);

	@Query("SELECT c FROM CodeAudits c WHERE c.auditor.userAccount.id =:id")
	Collection<CodeAudits> findCodeAuditsByAuditor(int id);

}
