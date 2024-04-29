
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.code-audits.list.label.code" path="code"  width="20%"/>
	<acme:list-column code="any.code-audits.list.label.executionDate" path="executionDate" width="20%" />
	<acme:list-column code="any.code-audits.list.label.type" path="type" width="20%" />
	<acme:list-column code="any.code-audits.list.label.correctiveActions" path="correctiveActions" width="20%" />
	<acme:list-column code="any.code-audits.list.label.link" path="link" width="20%" />

</acme:list>