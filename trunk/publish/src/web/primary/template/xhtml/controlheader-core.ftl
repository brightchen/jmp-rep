<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#assign hasFieldErrors = parameters.name?exists && fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<div errorFor="${parameters.id}">
     <span class="errorMessage">${error?html}</span><#t/>
</div>
</#list>
</#if>
<#--
	if the label position is top,
	then give the label it's own row in the table
-->
<#if parameters.label?exists>
    <label <#t/>
<#if parameters.id?exists>
        for="${parameters.id?html}" <#t/>
</#if>
<#if hasFieldErrors>
        class="error"<#t/>
<#else>
        class="label"<#t/>
</#if>
    ><#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") != 'right'>
        <em>*</em><#t/>
</#if>
${parameters.label?html}<#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
<em>*</em><#t/>
</#if>
:<#t/>
<#include "/${parameters.templateDir}/xhtml/tooltip.ftl" /> 
</label><#t/>
</#if>
    <#lt/>
<#-- add the extra row -->


