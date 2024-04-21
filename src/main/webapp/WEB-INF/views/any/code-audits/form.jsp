<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.code-audits.form.label.code" path="code"/>	
	<acme:input-moment code="any.code-audits.form.label.executionDate" path="executionDate"/>	
	<acme:input-textbox code="any.code-audits.form.label.type" path="type"/>	
	<acme:input-textbox code="any.code-audits.form.label.correctiveActions" path="correctiveActions"/>	
	<acme:input-url code="any.code-audits.form.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${hasAuditRecords}">
			<acme:button code="any.code-audits.audit-records" action="/any/audit-records/list?masterId=${id}"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>