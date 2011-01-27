function clearErrorMessages(form) {

    var errorDiv = document.getElementsByClassName('errors');
    if (errorDiv!=null){
    	errorDiv.parentNode.removeChild(errorDiv);
    }
}

function clearErrorLabels(form) {
    // set all labels back to the normal class
    var elements = form.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].className=="error"){
             elements[i].setAttribute("class", "");
        }
        var label = elements[i].parentNode.firstChild; 
        if (label.className=="error"){
             label.setAttribute("class", "");
        }
    }

}


document.getElementsByClassName = function ( className )
{
  var children = document.getElementsByTagName( "div" );
  for ( var a = 0; a < children.length; a++ )
  {
  	var thisclassname = children[a].className;
    if (thisclassname== className)
    	return children[a];
  }
  
}

function addError(e, errorText) {
    try {
        // clear out any rows with an "errorFor" of e.id
        // create the error box at the top
        var errorDiv = document.getElementsByClassName('errors');
        
       
        if (errorDiv!=null){
            var ul = document.getElementsByTagName('ul');
            var li = document.createElement('li');
             li.innerHTML = errorText;
            ul[0].appendChild(li);
           

        } else {
            
      	    var formContainer = document.getElementsByClassName('form-container');
     	    var formElement = document.forms[0];
	    errorDiv = document.createElement("div"); 
            formContainer.insertBefore(errorDiv,formElement);
            var errorP = document.createElement("p");
            var errorP1 = document.createElement("p");
            var errorEm = document.createElement("em");
            var ul = document.createElement("ul");
            var li = document.createElement("li");
            var error = document.createTextNode(errorText);
        
            var errorMsg = document.createTextNode("Oops... the following errors were encountered:");
       	    var errorNotSave = document.createTextNode("Data has NOT been saved.");
       	
            errorDiv.appendChild(errorP);
            errorDiv.appendChild(errorP1);
            errorP.appendChild(errorEm);
            errorP1.appendChild(errorNotSave);
            errorEm.appendChild(errorMsg );
            errorP.appendChild(ul);  
         
            ul.appendChild(li);
            li.appendChild(error);
        
            errorDiv.setAttribute("class", "errors");
            errorDiv.setAttribute("className", "errors"); //ie hack cause ie does not support setAttribute
           
            errorDiv.insertBefore(ul,errorP1);
            errorDiv.setAttribute("errorFor", e.id);
       }

 
    // change the label to red and clear the input
            var label = findLabel(e);
            label.className = "error";
            
        e.className = "error";
        
       // updat the label too
     //   var label = row.cells[0].getElementsByTagName("label")[0];
       // label.setAttribute("class", "errorLabel");
      //  label.setAttribute("className", "errorLabel"); //ie hack cause ie does not support setAttribute
    } catch (e) {
        alert(e);
    }
}

function findLabel(e)
{
  var label;
  if(e.tagName=='label'){
  	label = e;
  }
  else {
  	label = e.parentNode.getElementsByTagName('label')[0];
  }
  
  if (label!= null)
  	{return label;}
  else if
  	(e.tagName == "BODY")
  	{return null;}
  else
  {return findLabel(e.parentNode);}
}
