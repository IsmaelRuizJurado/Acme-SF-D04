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
</table>

<jstl:choose>
<jstl:when test="${trainingModuleTimeStats.getMaximum()>0.0}">


	<h3><acme:message code="developer.developerDashboard.form.label.training-modules.information"/></h3>
	<div>
		<canvas id="canvas"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				labels : [
						"AVERAGE", "MAX", "MIN","DEVIATION"
				],
				datasets : [
					{
						data : [
							<jstl:out value="${trainingModuleTimeStats.getAverage()}"/>, 
							<jstl:out value="${trainingModuleTimeStats.getMaximum()}"/>, 
							<jstl:out value="${trainingModuleTimeStats.getMinimum()}"/>,
							<jstl:out value="${trainingModuleTimeStats.getDeviation()}"/>
						],
						backgroundColor: [
						      'rgb(40, 180, 99)',
					    	  'rgb(54, 162, 235)',
					    	  'rgb(255, 205, 86)',
					      	  'rgb(230, 170, 243)'
					    ]
					}
				]
			};
			var options = {
				scales : {
					yAxes : [
						{
							ticks : {
								suggestedMin : 0.0,
								suggestedMax : 100.0
							}
						}
					]
				},
				legend : {
					display : false
				}
			};
	
			var canvas, context;
	
			canvas = document.getElementById("canvas");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "bar",
				data : data,
				options : options
			});
		});
	</script>

</jstl:when>
</jstl:choose>
<jstl:choose>
<jstl:when test="${numTrainingSessionsWithLink != 0}">

	<h3><acme:message code="developer.developerDashboard.form.label.training-sessions.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				datasets : [
					{
						data : [
							<jstl:out value="${numTrainingSessionsWithLink}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)',
					      'rgb(255, 205, 86)',
					      'rgb(230, 170, 243)'
					    ]
					}
				]
			};
	
			var canvas, context;
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
</jstl:when>
</jstl:choose>

<jstl:choose>
<jstl:when test="${numTrainingModulesWithUpdateMoment != 0}">

	<h3><acme:message code="developer.developerDashboard.form.label.training-modules.type.information"/></h3>
	<div>
		<canvas id="canvas3"></canvas>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var data = {
				datasets : [
					{
						data : [
							<jstl:out value="${numTrainingModulesWithUpdateMoment}"/>, 
						],
						backgroundColor: [
					      'rgb(40, 180, 99)',
					      'rgb(54, 162, 235)',
					      'rgb(255, 205, 86)',
					      'rgb(230, 170, 243)'
					    ]
					}
				]
			};
	
			var canvas, context;
	
			canvas = document.getElementById("canvas3");
			context = canvas.getContext("2d");
			new Chart(context, {
				type : "doughnut",
				data : data,
			});
		});
	</script>
</jstl:when>
</jstl:choose>


<acme:return/>