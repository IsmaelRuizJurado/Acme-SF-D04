

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.average-contract-budget"/>
		</th>
		<td>
			<acme:print value="${contractBudgetStats.getAverage()}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numPrincipalsPerRole"/>
		</th>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numAdmins"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numAdmins')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numManagers"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numManagers')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numClients"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numClients')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numSponsors"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numSponsors')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numProviders"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numProviders')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numDevs"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numDevs')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.numConsumers"/>
		</th>
		<td>
			<acme:print value="${totalPrincipalsWithEachRole.get('numConsumers')}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.ratioCriticalObjectives"/>
		</th>
		<td>
			<acme:print value="${ratioCriticalNonCriticalObjectives.get(criticalRatio)}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="admin.adminDashboard.form.label.ratioNonCriticalObjectives"/>
		</th>
		<td>
			<acme:print value="${ratioCriticalNonCriticalObjectives.get(nonCriticalRatio)}"/>
		</td>
	</tr>
	
	
	
</table>



<acme:return/>