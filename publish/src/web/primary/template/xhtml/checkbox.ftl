<#assign hasFieldErrors = fieldErrors?exists && fieldErrors[parameters.name]?exists/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<#rt/>
<#if parameters.id?exists>
 errorFor="${parameters.id}"<#rt/>
</#if>
        <span class="errorMessage">${error?html}</span><#t/>
</#list>
</#if>
<#if parameters.labelposition?default("") == 'top'>
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") != 'right'>
<em>*</em><#t/>
</#if>
${parameters.label?html}<#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
<em>*</em><#t/>
</#if>
:<#t/>
<#if parameters.tooltip?exists>
    <#include "/${parameters.templateDir}/xhtml/tooltip.ftl" />
</#if>
</label><#t/>
</#if>
<#include "/${parameters.templateDir}/simple/checkbox.ftl" />
<#else>
<#if parameters.labelposition?default("") == 'left'>
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") != 'right'>
<em>*</em><#t/>
</#if>
${parameters.label?html}<#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
<em>*</em><#t/>
</#if>
:<#t/>
<#if parameters.tooltip?exists>
    <img src='<@s.url value="/struts/tooltip/tooltip.gif" includeParams="none" encode="false"/>' alt="${parameters.tooltip}" title="${parameters.tooltip}" onmouseover="return escape('${parameters.tooltip?js_string}');" />
</#if>
</label><#t/>
</#if>
</#if>
<#if parameters.labelposition?default("") == 'right'>
    <#if parameters.required?default(false)>
        <em>*</em><#t/>
    </#if>
    <#if parameters.tooltip?exists>
        <img src='<@s.url value="/struts/tooltip/tooltip.gif" includeParams="none" encode="false"/>' alt="${parameters.tooltip}" title="${parameters.tooltip}" onmouseover="return escape('${parameters.tooltip?js_string}');" />
    </#if>
</#if>
<#if parameters.labelposition?default("") != 'top'>
                	<#include "/${parameters.templateDir}/simple/checkbox.ftl" />
</#if>                    
<#if parameters.labelposition?default("") != 'top' && parameters.labelposition?default("") != 'left'>
<#if parameters.label?exists> <label<#t/>
<#if parameters.id?exists>
 for="${parameters.id?html}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${parameters.label?html}</label><#rt/>
</#if>
</#if>
</#if>
 <#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" /><#nt/>
