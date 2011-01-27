<#if (actionErrors?exists && actionErrors?size > 0)>
	<div class="errors" id="hasError">
 			<p><em>Oops... the following errors were encountered:</em></p>
 		 		<ul>
	<#list actionErrors as error>
		<li>${error}</li>
	</#list>
	</ul>
 			<p>Data has NOT been saved.</p>
 		</div>
	
</#if>
