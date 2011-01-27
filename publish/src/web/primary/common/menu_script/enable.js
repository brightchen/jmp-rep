function PrintMessages(b,c){
	var sUrl="print.jsp?type=message&cpids="+b+"&isSafe="+c;
	var oWindow=window.open(sUrl,"","width=600px,height=400px,menubar=no,location=no,toolbar=no,resizable=yes,scrollbars=yes");
	CancelPrint();
	}



function selectone(e){
	 
	
	isSelected();
}

function enableButtons(){
	var button1=document.getElementById("button.enable");
	var button2=document.getElementById("button.disable");	
	button1.disabled=false;
	button2.disabled=false;
}

function isSelected(){
	var checks=document.getElementsByName("selectedMessages");
	var onecheck=false;
	for(var i=0;i<checks.length;i++){
		onecheck = onecheck ||checks[i].checked;
	
	}
	var pagelink = getElementsByClassName1(document,"pagelinks");
	var eButton = document.getElementById("button");

	if (onecheck){
		for (var i=0;i<eButton.length;i++){
			eButton[i].disabled=false;
			eButton[i].style.margin="20px 0 0 0";
		}
		
		for (var i=0 ; i < pagelink.length;i++){
			pagelink[i].style.display = "none";
		}
	}else {
		for (var i=0;i<eButton.length;i++){
			eButton[i].disabled=true;
			eButton[i].style.margin="0";
		}
	
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