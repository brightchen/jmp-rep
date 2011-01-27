function PrintMessages(b,c){
	var sUrl="print.jsp?type=message&cpids="+b+"&isSafe="+c;
	var oWindow=window.open(sUrl,"","width=600px,height=400px,menubar=no,location=no,toolbar=no,resizable=yes,scrollbars=yes");
	CancelPrint();
	}

function CheckAndPrintOneMsg(){
	var items=document.getElementsByName("selectedMessages");
	var found=false;var idx=0;
	for(var i=0;i<items.length;i++){
		if(items[i].checked){
			if(found)return false;
			found=true;
			idx=i;
		}
	}
	
	PrintMessages(items[idx].value+",-1",false);
	return true;
}

function CancelPrint(){
	var infoPane=document.getElementById("error");
	
	if(infoPane)infoPane.style.display="none";
}

function selectone(e){
	 
	if (window.event) e = window.event;  
	var srcElement = e.srcElement? e.srcElement : e.target;
	
	
	//var srcElement=e.srcElement;
	var checkall=document.getElementById("checkall");
	
	if(checkall.checked==true&&srcElement.checked==false)checkall.checked=false;
	else{
		if(checkall.checked==false&&srcElement.checked==true){
			// enable the delete button
			var delButton=document.getElementById("updateMsg__delete").disabled=false;
			var checks=document.getElementsByName("selectedMessages");
			var allchecked=true;
			for(var i=0;i<checks.length;i++){
				if(checks[i].checked==false){
					allchecked=false;break;
				}
			}
			if(allchecked==true)
				checkall.checked=true;
		}
	}
	isSelected();
}

function selectall(){
	var checkall=document.getElementById("checkall");
	var checks=document.getElementsByName("selectedMessages");
	var ischecked=checkall.checked;
	for(var i=0;i<checks.length;i++){
		checks[i].checked=ischecked;
	}
	// enable the delete button
	var delButton=document.getElementById("updateMsg__delete").disabled=false;
	isSelected();
}

function isSelected(){
	var checks=document.getElementsByName("selectedMessages");
	var onecheck=false;
	for(var i=0;i<checks.length;i++){
		onecheck = onecheck ||checks[i].checked;
	
	}
	var pagelink = getElementsByClassName1(document,"pagelinks");
	var delButton = document.getElementById("updateMsg__delete");
	if (onecheck){
		delButton.disabled=false;
		delButton.style.margin="20px 0 0 0";
		for (var i=0 ; i < pagelink.length;i++){
			pagelink[i].style.display = "none";
		}
	}else {
		delButton.disabled=true;
		delButton.style.margin="0";
		for (var i=0 ; i < pagelink.length;i++){
			pagelink[i].style.display = 'block';
		}
	}
}

function getElementsByClassName1(node, classname)
{
    var a = [];
    var re = new RegExp('\\b' + classname + '\\b');
    var els = node.getElementsByTagName("*");
    for(var i=0,j=els.length; i<j; i++)
        if(re.test(els[i].className))a.push(els[i]);
    return a;
}