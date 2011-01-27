<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<title><s:text name="PublicationSectionsForm.title"/></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />

<s:form name="EditPublicationSectionsForm" method="post" validate="true">

    <h3><s:text name="publication.publicationname" /></h3>
    <h3><s:label name="publication.publicationname" /></h3>
    
    <s:iterator status="stat" value="{1,2}" >
        <!-- grab the index (start with 0 ... ) -->
        <s:property value="#stat.index" />

        <!-- grab the top of the stack which should be the -->
        <!-- current iteration value (0, 1, ... 5) -->
        <s:property value="top" />
        <br/>
    </s:iterator>

<s:bean name="cg.iseepublish.web.action.PublicationSectionsAction" id="psa">
  <s:param name="task" value="new task" />
  The value of foot is : <s:property value="task"/>, when inside the bean tag <br />
</s:bean>

<h3><s:label name="task" /></h3>

    <s:iterator  status="stat" value="orderList">
        <s:property value="#stat.index" />

        <!-- grab the top of the stack which should be the -->
        <!-- current iteration value (0, 1, ... 5) -->
        <s:property value="top" />
    </s:iterator>
    
    <s:textfield name="publication.publicationname" value="%{'ca'}" />
    
    <display:table name="publicationSections" class="list" requestURI="" id="publicationSectionList" export="false" pagesize="10" sort="list">
        <display:column style="width: 5%" title="check">
            <input type="checkbox" name="id" />
        </display:column>
        
        <display:column sortable="true" titleKey="sectionNameCopy" >
            <s:textfield key="publicationSectionList.sectionName"/>
        </display:column>
        
        <display:column property="sectionName" sortable="true" titleKey="sectionName.text" />
        <display:column property="secondLanguage" sortable="true" titleKey="secondLanguage.text"/>		
        <display:column property="order" sortable="true" titleKey="order.text"/> 
    </display:table>
    
    <div class="buttonrow">
    	<s:submit action="EditPublicationSections" key="button.Save" name="operation" value="save" />
    	<s:submit key="Edit" name="operation" value="Edit" />
    	<s:submit key="Delete" name="operation" value="Delete" />
    	<s:submit key="Add" name="operation" value="Add" />

    </div>		

</s:form>