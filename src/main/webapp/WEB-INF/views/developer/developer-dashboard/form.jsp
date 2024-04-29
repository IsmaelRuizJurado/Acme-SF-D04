<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.average-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${trainingModuleTimeStats.getAverage()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.min-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${trainingModuleTimeStats.getMinimum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.max-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${trainingModuleTimeStats.getMaximum()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.lin-dev-training-modules-time"/>
		</th>
		<td>
			<acme:print value="${trainingModuleTimeStats.getDeviation()}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.training-modules-with-update-moment"/>
		</th>
		<td>
			<acme:print value="${numTrainingModulesWithUpdateMoment}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="developer.developerDashboard.form.label.training-sessions-with-link"/>
		</th>
		<td>
			<acme:print value="${numTrainingSessionsWithLink}"/>
		</td>
	</tr>	
</table>
<acme:return/>