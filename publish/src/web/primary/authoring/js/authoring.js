
// SERVER must be set by the page that includes this script file
var SERVER = "http://localhost/iseepublish/";
var DOCUMENT_ID = 'empty';
var pageIndex = 0;
var pageObject;

////////////////////////////////////////////////////////
// Server communication functions

	function postUpdateLayoutRequest(pageIndex, myXML)
	{
		var request = HTTP.newRequest();
		request.open("POST", SERVER + "/authoring/update.do", false);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var layoutUpdate = "docID=" + DOCUMENT_ID + "&pageIndex=" + pageIndex + "&layout=" + escape(myXML);
		//alert("UpdateLayoutRequest:\n" + layoutUpdate);
		request.send(layoutUpdate);
		
	}
	
	function getPageLayoutFromServer(pageIndex){
		var layoutXML = XML.load(SERVER + "/authoring/layout.do?docID=" + DOCUMENT_ID + "&pageIndex=" + pageIndex + "&action=getLayout");
		var layoutString = XML.serialize(layoutXML);
		//alert("PageLayoutFromServer:\n" + layoutString);
		return layoutString;
	}
	
	function getPageCountFromServer(){
	
		var pageCountResponse = XML.load(SERVER + "/authoring/layout.do?docID=" + DOCUMENT_ID + "&action=getPageCount");
		var pageCountString = XML.serialize(pageCountResponse);
		//alert("Page Count response:\n" + pageCountString);
		return pageCountString;
	}
	
	function getTumbImageUrl(pageIndex){
		var tumbUrl = SERVER + "/docs/" + DOCUMENT_ID + "/thumbnail?page=" + pageIndex + "&wid=50&qlt=80";
		//alert("Thumb image URL:\n" + tumbUrl);
		return tumbUrl;
	}
	
	function getImageUrl(pageIndex, wid){
		var imageUrl = SERVER + "/docs/" + DOCUMENT_ID + "/thumbnail?page=" + pageIndex + "&wid=" + wid + "&qlt=80";
		//alert("Authoring image URL:\n" + imageUrl);
		return imageUrl;
	}
	
////////////////////////////////////////////////////////
// Authoring functions
	function loadDocument()
	{
		//alert("new document ID = '" + DOCUMENT_ID + "'");
		//deleteHotspots();
		pageIndex = 0;
		//pageObject.iseepagelayout.page.contentgroup = [];
		//alert("initialize....");
		initialize();
	}
	
	function changePage(page)
	{
		deleteHotspots();
		pageObject.iseepagelayout.page.contentgroup = [];
		pageIndex = page;
		initialize();
	}
	
	function saveTable()
	{
		var cg = pageObject.iseepagelayout.page.contentgroup;

	var t = $("table1");
	// Iterate through table and store in data set
	for (var i = 0; i < cg.length+1; i++)
	{
		// If data row was deleted then skip
		if (cg[i] == null) continue;
		if (t.rows[i+1] == null) continue;

		// Set empty values in data set
		if (cg[i].text == null) cg[i].text = [];
		if (cg[i].figure == null) cg[i].figure = [];

		cg[i].titlestring = t.rows[i+1].cells[1].firstChild.value;
		cg[i].type = t.rows[i+1].cells[2].firstChild.value;

		var email = t.rows[i+1].cells[3].firstChild.value;
		if(typeof(email) == "string" && email != undefined) cg[i].email = email;

		var phone = t.rows[i+1].cells[4].firstChild.value;
		if(typeof(phone) == "string" && phone != undefined) cg[i].phone = phone;

		var sms = t.rows[i+1].cells[5].firstChild.value;
		if(typeof(sms) == "string" && sms != undefined) cg[i].sms = sms;

/*
		cg[i].email = t.rows[i+1].cells[3].firstChild.value;
		cg[i].phone = t.rows[i+1].cells[4].firstChild.value;
		cg[i].sms = t.rows[i+1].cells[5].firstChild.value;
*/
	}
	}
	function submit()
	{
		return;

		var cg = pageObject.iseepagelayout.page.contentgroup;

    	var ii = $("iimage");
    
    	saveTable();
    	// Set hotspot coordinates in data set
    	for (var i = 0; i < cg.length; i++)
    	{	
    		// If data row was deleted then skip
    		if (cg[i] == null) continue;
    
    		var div = $("hotspot" + i);
    		var x1  = parseInt(div.style.left) / ii.width;
    		var y1 = parseInt(div.style.top) / ii.height;
    		var x2 = parseInt(div.style.width) / ii.width + x1;
    		var y2 = parseInt(div.style.height) / ii.height + y1;
    		var hotspot_coords = "" + x1 + "," + y1 + "," + x2 + "," + y2;
    		// alert(cg[i].type);
    		if (cg[i].type == "article")
    			cg[i].text.area = hotspot_coords;
    		else if (cg[i].type == "ad") 
    			cg[i].figure.area = hotspot_coords;
    	}
    	
    	// Iterate through table and store in data set
    
    
    	// var myXML = '<?xml version="1.0"?><iseePageLayout><page section="1"><contentGroup type="article" titleString="U.S. takes big step to end Net oversight"><text area="0.178,0.18375,0.33199999999999996,0.6125" newParagraph="true"></text></contentGroup><contentGroup type="ad" titleString="Tag Heuer Advertisement"><figure area="0.668,0.755,0.978,0.98375" captionText="Tag Heuer Advertisement"></figure></contentGroup></page></iseePageLayout>';
    	// Store data set in XML
    	var myXML = '<?xml version="1.0"?>'
    	myXML += json2xml(pageObject, " ");
    	var ph_xml = $("placeholder_xml");
    	ph_xml.innerText = myXML;
	
		// Send XML to server	
		postUpdateLayoutRequest(pageIndex, myXML);

	}
	
	function deleteRow(row)
	{	
		var cg = pageObject.iseepagelayout.page.contentgroup;
		
		cg[row] = null;
		deleteHotspot(row);
		buildTable();
	}
	
	function addRow()
	{	
		var cg = pageObject.iseepagelayout.page.contentgroup;

    	var ii = $("iimage");
    
    	// Add new data row
    	cg[cg.length] = 
    	{
    		type:'article',
            	titlestring:'Title of article',
    		text:
    		{
    			area:'0.1,0.1,0.25,0.25', newparagraph:'true'
    		}
    	}
    
    	// Store the values in the table to the data set
    	saveTable();
    
    	// Rebuild the table from the data set
    	buildTable();
    
    	// initializeHotspots();
    
    	// Add the hotspot
    	hotspot = createHotspot(cg.length - 1);
    
    	// Position the hotspot
    	hotspot.style.left = 0.1 * ii.width + "px";
    	hotspot.style.top = 0.1 * ii.height + "px";
    	hotspot.style.width = (0.25 - 0.1) * ii.width  + "px";
    	hotspot.style.height = (0.25 - 0.1) * ii.height + "px";
	}
/**	
	// let loadDocument() initialize the page
	window.onload = function()
	{
		initialize();
	}
*/	
	function initialize()
	{

		var layoutString = getPageLayoutFromServer(pageIndex);
	
		var ph_xml = $("placeholder_xml");
    	ph_xml.innerText = layoutString;
    	ph_xml.innerHTML += "<br>" + xml2json.parser(layoutString, "", "html");
    
    	pageObject = xml2json.parser(layoutString);
    
    	if (pageObject == null) pageObject = {};
    	if (pageObject.iseepagelayout == null) pageObject.iseepagelayout = {};
    	if (pageObject.iseepagelayout.page == null) pageObject.iseepagelayout.page = {};
    	if (pageObject.iseepagelayout.page.contentgroup == null) pageObject.iseepagelayout.page.contentgroup = [];
    
    	if (pageObject.iseepagelayout.page.contentgroup.length == undefined)
    	{
    		var temp = pageObject.iseepagelayout.page.contentgroup;
    		pageObject.iseepagelayout.page.contentgroup = new Array(temp);
    	}
    
    	buildTable();
    	updateImage("large", false);
	
		var pageString = getPageCountFromServer();
		var pageCountObject = xml2json.parser(pageString);

	    var thumbnailsString = "<center>";
    	for (var i = 0; i < pageCountObject.pages.count; i++)
    	{
			var tumbImageUrl = getTumbImageUrl(i);
			thumbnailsString += '<p><img border="1" onclick="javascript:changePage(' + i + ')" src="' + tumbImageUrl + '"></p>';
		}
		var th = $("thumbnails");
	    th.innerHTML = thumbnailsString + "</center>";
	
	}
	
	function changeType(row, type)
	{
	}
	
	function moveRowUp(row)
	{
		var cg = pageObject.iseepagelayout.page.contentgroup;
    	if (row > 0)
    	{
    		// Change rows in data set
    		var temp = cg[row];
    		cg[row] = cg[row - 1];
    		cg[row-1] = temp;
    
    		// Rename hotspot
    		blurHotspot(row);
    		var myDiv = $("hotspot" + row);
    		var prevDiv = $("hotspot" + (row - 1 ) );
    		prevDiv.setAttribute("id", "hotspot" + "temp");
    		myDiv.setAttribute("id", "hotspot" + (row - 1));
    		prevDiv.setAttribute("id", "hotspot" + row);
    
    		buildTable();
    	}
	}
	
	function moveRowDown(row)
	{
		var cg = pageObject.iseepagelayout.page.contentgroup;
    	if (row < cg.length - 1)
    	{
    		// Change rows in data set
    		var temp = cg[row];
    		cg[row] = cg[row + 1];
    		cg[row+1] = temp;
    
    		// Rename hotspot
    		blurHotspot(row);
    		var myDiv = $("hotspot" + row);
    		var nextDiv = $("hotspot" + (row + 1) );
    		nextDiv.setAttribute("id", "hotspot" + "temp");
    		myDiv.setAttribute("id", "hotspot" + (row + 1));
    		nextDiv.setAttribute("id", "hotspot" + row);
    
    		buildTable();
    	}
	}
	
	function focusHotspot(row)
	{	
		var hs = $("hotspot" + row);
    	hs.style.backgroundColor = "yellow";
    	hs.style.opacity = "0.50";
    	hs.style.filter = "alpha(opacity=50)";
    	hs.style.borderColor = "red";
	}
	
	function blurHotspot(row)
	{	
		var hs = $("hotspot" + row);
    	hs.style.backgroundColor = "";
    	hs.style.opacity = "";
    	hs.style.filter = "";
    	hs.style.borderColor = "blue";
	}
	
	function buildTable()
	{
	// var cg = pageObject.iseepagelayout.page.contentgroup;
	var page = pageObject.iseepagelayout.page;
    	var tableString = '<table id="table1" border="0"><tr align="center"><td></td><td><b>Title</b></td><td><b>Type</b></td><td><b>E-mail</b></td><td><b>Phone</b></td><td><b>SMS</b></td></tr>';
    	/* for (var i = 0; i < page.articles.contentgroup.length; i++)
    	{
    		// If data row was deleted then skip
    		// if (cg[i] == null) continue;
		// tableString += createRow();
    	}
    	for (var i = 0; i < page.ads.contentgroup.length; i++)
    	{
    		// If data row was deleted then skip
		// createRow(cg);

    	} */
    	for (var i = 0; i < page.unassignedcontent.text.length; i++)
    	{
		tableString += createRow(page.unassignedcontent.text[i], i);
    		// tableString += "<tr><td>blah</td></tr>"
    	}

    	tableString += '</table><table border="0"><tr ><td><a href="javascript:addRow();"><img border="0" src="images/add.gif"></a></td></tr></table>';
    	var ph_data = $("placeholder_data");
    	ph_data.innerHTML = tableString;
    	if (page.unassignedcontent.text.length == 0) addRow();
	}

	function createRow(rowData, i)
	{
		var tableString = "";

		var titleString = "";
		if ( rowData.titleString == null ) titleString = "unassigned";
		else titleString = rowData.titleString;

    		tableString += '<tr>'
    			+ '<td><a onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" href="javascript:deleteRow(' + i + ')"><img border="0" src="images/x.gif"></a>'
    			+ '<a onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" href="javascript:moveRowUp(' + i + ')"><img border="0" src="images/up.gif"></a>'
    			+ '<a onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" href="javascript:moveRowDown(' + i + ')"><img border="0" src="images/down.gif"></a></td>'
    			+ '<td><input onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" id="textinput" size="30" type="text" value="' + titleString + '"></input></td>';
    		if (true)
    		{
    			tableString += '<td><select onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" onchange="changeType('+ i + ', this.value);"><option value="ad">Ad<option value="article" SELECTED>Article</select></td>'
    			// tableString += "<td>" + cg[i].text.area + "</td>"
    		}
    		else
    		{
    			tableString += '<td><select onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" onchange="changeType('+ i + ', this.value);"><option value="ad" SELECTED>Ad<option value="article">Article</select></td>'
    			// tableString += "<td>" + cg[i].figure.area + "</td>"
    		}
    		var email = "";
    		if(typeof(rowData.email) == "string" && rowData.email != undefined) email = rowData.email;
    		var phone = "";
    		if(typeof(rowData.phone) == "string" && rowData.phone != undefined) phone = rowData.phone;
    		var sms = "";
    		if(typeof(rowData.sms) == "string" && rowData.sms != undefined) sms = rowData.sms;
    
    		tableString += '<td><input onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" id="textinput" size="20" type="text" value="' + email + '"></input></td>';
    		tableString += '<td><input onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" id="textinput" size="10" type="text" value="' + phone + '"></input></td>';
    		tableString += '<td><input onblur="blurHotspot(' + i + ');" onfocus="focusHotspot(' + i + ');" id="textinput" size="3" type="text" value="' + sms + '"></input></td>';
    		tableString += "</tr>";
		return tableString;
	}
	
	window.onunload = function()
	{
		var message = "" +
			"You have made changes to the publication:\n" +
			"Your changes have not been not saved.\n" +
			"        ______________________________\n\n" +
			"Click \"OK\" to save or \"Cancel\" to cancel changes.\n";
		// Save data before exiting
		// if(confirm(message)) submit();
	};
	
	function $(x)
	{
		if (typeof x == "string") return document.getElementById(x);
		return x;
	}
	
	// var headline = document.getElementById("headline");
	// headline.setAttribute("align", "center"); // Set align="center"
	
	function updateImage(size, isUpdate)
	{
		var ii = $("iimage");

    	isUpdate = true;
    	var wid = 300;
    	switch(size)
    	{
    		case "small":
    			wid = 300;
    			break;
    		case "medium":
    			wid = 450;
    			break;
    		case "large":
    			wid = 600;
    			break;
    		default:
    			wid = 300;
    			break;
    	}
    	// var a1 = $("a1");
    	
    	ii.onload = afterImageLoaded;
		
		var imageUrl = getImageUrl(pageIndex, wid);
		
		ii.onload = afterImageLoaded;
		ii.src = imageUrl;
		if (isUpdate)
    	{
    
    		// a1.x1 = parseInt(a1.style.left) / ii.width;
    		// a1.y1 = parseInt(a1.style.top) / ii.height;
    		// a1.x2 = parseInt(a1.style.width) / ii.width + a1.x1;
    		// a1.y2 = parseInt(a1.style.height) / ii.height + a1.y1;
    		// a1.style.visibility = "hidden";
    	}
	}
	
	function afterImageLoaded()
	{
		initializeHotspots();
    	var ii = $("iimage");
    	dragresize.maxLeft = ii.width;
    	dragresize.maxTop = ii.height;
	}
	
	function createHotspot(id)
	{
		var div = document.createElement("div");

    	div.setAttribute("id", "hotspot" + id);
    	div.className = "drsElementAlt drsMoveHandleAlt";
    	div.style.position = "absolute";
    	div.style.left = 0;
    	div.style.top = 0;
           	div.style.width = 300;
           	div.style.height = 300;
           	div.style.zOrder="+2";
    	// Show name of hotspot
    	// div.innerHTML = "<b>" + div.getAttribute("id") + "</b>";
    	var c = $("canvas");
    	c.appendChild(div);
    	return div;
	}
	
	function deleteHotspot(hotspot)
	{
		var c = $("canvas");
    	var d = $("hotspot" + hotspot);
    	c.removeChild(d);
	}
	
	function deleteHotspots()
	{
	
		var cg = pageObject.iseepagelayout.page.contentgroup;

    	for (var i = 0; i < cg.length; i++)
    	{
    		if (cg[i] == null) continue;
    		deleteHotspot(i);
    	}
		
	}
	
	function initializeHotspots()
	{
	
	//var cg = pageObject.iseepagelayout.page.contentgroup;
	var cg = pageObject.iseepagelayout.page.unassignedcontent.text;
    	var ii = $("iimage");
    
    	for (var i = 0; i < cg.length; i++)
    	{
    		if (cg[i] == null) continue;
    
    		var coords = "";
    
    		// if (cg[i].text) 
    		if (cg[i].area) 
    			// coords = cg[i].text.area;
    			coords = cg[i].area;
    		else if (cg[i].figure) 
    			coords = cg[i].figure.area;
    		var point = coords.split(",");
    
    		var hotspot = $("hotspot" + i);
    		// If hotspots doesn't exist then create new one.
    		if (hotspot == null)
    		{
    			hotspot = createHotspot(i);
    		}
    		hotspot.style.left = point[0] * ii.width + "px";
    		hotspot.style.top = point[1] * ii.height + "px";
    		hotspot.style.width = (point[2] - point[0]) * ii.width  + "px";
    		hotspot.style.height = (point[3] - point[1]) * ii.height + "px";
    	}
    	/*
    	a1.style.left = a1.x1 * ii.width + "px";
    	a1.style.top = a1.y1 * ii.height + "px";
    	a1.style.width = (a1.x2 - a1.x1) * ii.width  + "px";
    	a1.style.height = (a1.y2 - a1.y1) * ii.height + "px";
    	a1.style.visibility = "visible";
    	*/
	}
	
	function changeView()
	{
		var c = $("canvas");
    	var th = $("thumbnails");
    	var ph_data = $("placeholder_data");
    	if ("255px" == th.style.top)
    	{
    		th.style.top = "95px";
    		c.style.top = "95px";
    		ph_data.style.left = "710px";
    		ph_data.style.width = "250px";
    		placeholder_data.style.height = "400px";
    	}
    	else
    	{
    		th.style.top = "255px";
    		c.style.top = "255px";
    		placeholder_data.style.left = "10px";
    		placeholder_data.style.width = "690px";
    		placeholder_data.style.height = "150px";
    	}
	}
	
	// Using DragResize is simple!
    // You first declare a new DragResize() object, passing its own name and an object
    // whose keys constitute optional parameters/settings:
    
    var dragresize = new DragResize('dragresize',
     { minWidth: 20, minHeight: 20, minLeft: 0, minTop: 0, maxLeft: 600, maxTop: 960 });
    
    dragresize.isElement = function(elm)
    {
     if (elm.className && elm.className.indexOf('drsElement') > -1) return true;
    };
    dragresize.isHandle = function(elm)
    {
     if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
    };
    
    
    dragresize.ondragfocus = function() { };
    dragresize.ondragstart = function(isResize) { };
    dragresize.ondragmove = function(isResize) { 
    };
    dragresize.ondragend = function(isResize) { };
    dragresize.ondragblur = function() { };
    
    // Finally, you must apply() your DragResize object to a DOM node; all children of this
    // node will then be made draggable. Here, I'm applying to the entire document.
    dragresize.apply(document);
	