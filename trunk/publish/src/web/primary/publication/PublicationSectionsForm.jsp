<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<head>
    <SCRIPT type="text/javascript">
        function onCheckBoxClick()
        {
            document.getElementById( "savePublicationSections_button_Delete" ).disabled = false;
        }
        
        function onTextfieldChange()
        {
            document.getElementById( "savePublicationSections_button_Save" ).disabled = false;
        }
    </SCRIPT>
</head>

<title><s:text name="PublicationSectionsForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:actionerror />
<s:actionmessage />

<s:form name="PublicationSectionsForm" action="savePublicationSections" method="post" validate="true">

    <s:label key="publication.publicationname" />
    
    <display:table name="publicationSections" class="list" requestURI="" id="orders" export="false" pagesize="10" sort="list">
        <fieldset>
            <display:column style="width: 5%" title="select">
                <s:checkbox name="selectedRowList" fieldValue="%{checkValues}" onclick="onCheckBoxClick()"/>
            </display:column>
        
            <display:column titleKey="sectionName.text">
                <s:textfield  name="sectionNameList" required="true" onchange="onTextfieldChange()" />
            </display:column>
    
            <display:column titleKey="secondLanguage.text">
                <s:textfield name="secondLanguageList" required="false" onchange="onTextfieldChange()" />
            </display:column>

            <display:column titleKey="order.text">
                <s:select name="orderList" list="orders" required="true" onchange="onTextfieldChange()" />
            </display:column>
        </fieldset>            
    </display:table>

    
    <div class="buttonrow">
    	<s:submit action="SavePublicationSections" key="button.Save" onclick="form.onsubmit=null" cssClass="fbtn" disabled="true" />
    	<s:submit action="DeletePublicationSections" key="button.Delete" onclick="form.onsubmit=null" cssClass="fbtn" disabled="true" />
    </div>		

</s:form>