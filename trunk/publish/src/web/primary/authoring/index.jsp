<%@taglib prefix="s" uri="/struts-tags"%><html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>iseepublish Editor 3</title>
<link rel="stylesheet" type="text/css" href="src/yui/reset-fonts-grids/reset-fonts-grids.css" />
<link rel="stylesheet" type="text/css" href="src/yui/resize/assets/skins/sam/resize.css" />
<link rel="stylesheet" type="text/css" href="src/yui/layout/assets/skins/sam/layout.css" />
<link rel="stylesheet" type="text/css" href="src/yui/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="src/yui/datatable/assets/skins/sam/datatable.css" />
<link rel="stylesheet" type="text/css" href="src/yui/treeview/assets/skins/sam/treeview.css" />
<link rel="stylesheet" type="text/css" href="src/yui/container/assets/skins/sam/container.css" />

<script type="text/javascript" src="src/yui/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="src/yui/connection/connection-min.js"></script>
<script type="text/javascript" src="src/yui/datasource/datasource-beta-min.js"></script>
<script type="text/javascript" src="src/yui/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="src/yui/element/element-beta-min.js"></script>
<script type="text/javascript" src="src/yui/layout/layout-beta-min.js"></script>

<script type="text/javascript" src="src/yui/button/button-min.js"></script>
<script type="text/javascript" src="src/yui/datatable/datatable-beta-min.js"></script>
<script type="text/javascript" src="src/yui/resize/resize-beta-min.js"></script>
<script type="text/javascript" src="src/yui/treeview/treeview-min.js"></script>
<script type="text/javascript" src="src/yui/imageloader/imageloader-min.js"></script>

<script type="text/javascript" src="src/yui/get/get-min.js"></script>
<script type="text/javascript" src="src/yui/container/container-min.js"></script>

<style type="text/css"> 
.icon-text { display:block; height: 16px; padding-left: 20px; background: transparent url(images/hotspot_text.png) 0 0px no-repeat; } 
.icon-image { display:block; height: 16px; padding-left: 20px; background: transparent url(images/hotspot_image.png) 0 0px no-repeat; } 
.icon-ad { display:block; height: 16px; padding-left: 20px; background: transparent url(images/type_ad.png) 0 0px no-repeat; } 
.icon-article { display:block; height: 16px; padding-left: 20px; background: transparent url(images/type_article.png) 0 0px no-repeat; } 
.icon-misc { display:block; height: 16px; padding-left: 20px; background: transparent url(images/type_misc.png) 0 0px no-repeat; } 
#save button { padding-left: 2em; background: url(images/toolbar_save.png) 10% 50% no-repeat; }
#previous button { padding-left: 0em; width: 16px; height: 16px; background: url(images/toolbar_previous.png) 10% 50% no-repeat; }
#next button { padding-left: 0em; width: 16px; height: 16px; background: url(images/toolbar_next.png) 10% 50% no-repeat; }
#add button { padding-left: 2em; background: url(images/layout_add.png) 10% 50% no-repeat; }
#remove button { padding-left: 2em; background: url(images/layout_delete.png) 10% 50% no-repeat; }
#exit button { padding-left: 2em; background: url(images/toolbar_exit.png) 10% 50% no-repeat; }
#top1 { padding-top: 5px; }

div.selected {
    background-color: yellow;
	opacity: 0.76;
	filter: alpha(opacity=76);
}
div.unselected {
    background-color: none;
	opacity: 1;
	filter: alpha(opacity=100);
}

</style> 

</head>


<body class="yui-skin-sam">
<div>
<div id="top1">
	<div style="margin: 0 0 0 5px; float: left;">
	<button type="button" id="save" name="save">Save</button>
	<button type="button" id="previous" name="previous">&nbsp;&nbsp;</button>
	<span id="page"></span>
	<button type="button" id="next" name="next">&nbsp;&nbsp;</button>
	<span id="section"></span>
	<button type="button" id="add" name="add">Add&nbsp;&nbsp;&nbsp;</button>
	<button type="button" id="remove" name="remove">Remove</button>
	<!-- <button type="button" id="section" name="section">&nbsp;&nbsp;</button> -->
	<button type="button" id="exit" name="exit">Exit</button>
	</div>
	<img src='images/logo-etisalat.gif' style="margin: 0 1em; float: right;"/>
</div>
<div id="bottom1">
	<div id="dataDiv"></div>
	<div id="getLayoutDiv" style="visibility: hidden"></div>
	<!-- <div id="advertisersDiv"></div> -->
</div>
<div id="right1">
<div id="queue"></id>
</div>
<div id="left1">
	<div id="treeDiv"></div>
</div>
<div id="center1">
	<div id="imgDiv" style="position: absolute; left: 0px; top: 0px;">
		<img id="img" style='position: absolute; left: 0px; top: 0px;' />
		<div id="hotspotsDiv" style='position: absolute; left: 0px; top: 0px;'></div>
	</div>
</div>
</div>

<div id="advertisersDiv" style="background-color: white; overflow: scroll; border: 0px; position: absolute; left: 300px; top: 200px; width: 475px; height: 200px; display: none"></div>


<script>

$debug = true;

(function() {
    var Dom = YAHOO.util.Dom,
        Event = YAHOO.util.Event;

    Event.onDOMReady(function() {
        var layout = new YAHOO.widget.Layout({
            units: [
                { position: 'top', height: 75, header: 'iseepublish Editor 3', body: 'top1', gutter: '5px', collapse: false, resize: false },
                { position: 'right', header: 'Preview', width: 200, resize: true, gutter: '5px', footer: '', collapse: true, scroll: true, body: 'right1', animate: true },
                { position: 'bottom', header: 'Description', height: 200, resize: true, body: 'bottom1', gutter: '5px', collapse: true, scroll: true },
                { position: 'left', header: 'Title', gutter: '5px', width: 200, resize: true, body: 'left1', collapse: true, close: false, scroll: true, animate: true },
                { position: 'center', body: 'center1', scroll: true }
            ]
        });
		if ($debug) $layout = layout;
        layout.render();
		// layout.getUnitByPosition('right').collapse();
		layout.getUnitByPosition('bottom').collapse();
		if ($debug) $layout = layout;
    });
})();
</script>

<script type="text/javascript">
var isDirty = false;
function exit() {
	if (isDirty) {
		// var leave = confirm('You have unsaved data. Click on the Cancel and Save buttons to save the data.');
		// return 'You have unsaved data. Click on the Cancel and Save buttons to save the data, or OK to continue.';
		return 'You have unsaved data. Press OK to continue and lose the data, or Cancel and Save to keep the data.';
	}
}
window.onbeforeunload = exit;

var $debug = true;
var $tree = null;
$dd_source = null;
$dd_target = null;
$page = 0;
$page_max = 1;
$_articles = null;
$_ads = null;
$_unassignedcontent = null;
var $oParsedResponse = null;
var $oFullResponse = null;

YAHOO.namespace("iseemedia.util");
YAHOO.iseemedia.util.CustomDataSource = function (oLiveData, oConfigs) {
	YAHOO.iseemedia.util.CustomDataSource.superclass.constructor.call(this, oLiveData, oConfigs);
};

YAHOO.lang.extend(YAHOO.iseemedia.util.CustomDataSource, YAHOO.util.DataSource, {
	parseXMLData:function (oRequest, oFullResponse) {
	    var bError = false;
		if ($debug) $oFullResponse = oFullResponse;

	    var oParsedResponse = { meta:{} };
		if ($debug) $oParsedResponse = oParsedResponse;
		
		oParsedResponse.results = [];
		var oResult = {};
			
		var iseePageLayoutNode = oFullResponse.firstChild;
		var pageNode = oFullResponse.getElementsByTagName('page')[0];

		// Get the nodes for 'ads', 'articles', and 'unassignedContent'
		var ads, articles, unassignedContent;
		for (var i = 0; i < pageNode.childNodes.length; i++) {
			switch (pageNode.childNodes[i].nodeName) {
				case 'ads':
					ads = pageNode.childNodes[i];
					break;
				case 'articles':
					articles = pageNode.childNodes[i];
					break;
				case 'unassignedContent':
					unassignedContent = pageNode.childNodes[i];
					break;
			}
		}
		if ($debug) {
			$pageNode = pageNode; $ads = ads; $articles = articles; $unassignedContent = unassignedContent;
		}

		function area2css(area) {
			var point = area.split(',');
			var x1 = parseFloat(point[0]);
			var y1 = parseFloat(point[1]);
			var x2 = parseFloat(point[2]);
			var y2 = parseFloat(point[3]);
			/* // ORIGINAL
			var left = Math.floor(x1 * pageImageWidth);
			var top = Math.floor(y1 * pageImageHeight);
			var width = Math.ceil( (x2 - x1) * pageImageWidth);
			var height = Math.ceil( (y2 - y1) * pageImageHeight);
			*/
			var left = Math.floor(x1 * pageImageWidth);
			var top = Math.floor(y1 * pageImageHeight);
			var right = Math.ceil(x2 * pageImageWidth);
			var bottom = Math.ceil(y2 * pageImageHeight);
			var width = right - left;
			var height = bottom - top;
		
			return { left: left, top: top, width: width, height: height };
		}

		// Create a DIV tag element that will become the hotspot on the page
		function createDiv(css, type, id, textContent, layerId) {
			var div = document.createElement('div');
			div.id = id;
			div.style.position = 'absolute';
			div.style.left = css.left + 'px';
			div.style.top = css.top + 'px';
			div.style.width = css.width + 'px';
			div.style.height = css.height + 'px';
			div.style.border = '1px dotted blue';
			div.style.overflow = 'hidden';
			div.style.backgroundImage = 'url(images/cover.gif)';

			var icon = document.createElement('img');
			if (type === 'text') {
				div.setAttribute('_type', 'text');
				icon.setAttribute('src', 'images/hotspot_text.png');
			}
			else if (type === 'figure') {
				div.setAttribute('_type', 'figure');
				icon.setAttribute('src', 'images/hotspot_image.png');
			}
			if (textContent) {
				icon.setAttribute('alt', textContent);
				icon.setAttribute('title', textContent);
			}
			else {
				// icon.setAttribute('alt', id);
				// icon.setAttribute('title', id);
			}
			div.appendChild(icon);

			// Append div to canvas:
			var layerDiv = document.getElementById(layerId);
			if (!layerDiv) {
				var hotspotsDiv = document.getElementById('hotspotsDiv');
				var layerDiv = document.createElement('div');
				layerDiv.id = layerId;
				layerDiv.style.position = 'absolute';
				layerDiv.style.visibility = 'hidden';
				hotspotsDiv.appendChild(layerDiv);
			}
			layerDiv.appendChild(div);
			
			return div;
		}
		
		// Iterate through articles looking for the 'contentGroup'
		function iterateContentGroup(content) {
			var c = content.childNodes;
			var oResponse = [];
			for (var i = 0; i < c.length; i++) {
				var oResult = {};
				if ( 'contentGroup' === c[i].nodeName ) {
					var hotspots = [];
					// Find all the attributes...
					oResult['id'] = c[i].attributes.getNamedItem('id').value;
					oResult['type'] = c[i].attributes.getNamedItem('type').value;
					oResult['titleString'] = c[i].attributes.getNamedItem('titleString').value;
					if (c[i].attributes.getNamedItem('adRef'))
						oResult['adRef'] = c[i].attributes.getNamedItem('adRef').value;
					else oResult['adRef'] = '';
					// ... and then iterate to find the rest
					var pos = 0;
					for (var j = 0; j < c[i].childNodes.length; j++) {
						switch (c[i].childNodes[j].nodeName) {
							case 'text':
								var area = c[i].childNodes[j].attributes.getNamedItem('area').value;
								var type = c[i].childNodes[j].nodeName;
								var css = area2css(area);
								var id = 'g' + oResult.id + 'h' + pos++;
								if (type === 'text' && c[i].childNodes[j].firstChild) {
									var textContent = c[i].childNodes[j].firstChild.firstChild.nodeValue;
									hotspots.push( { area: area, type: type, css: css, id: id,
										div: createDiv(css, type, id, textContent, 'g' + oResult.id) } );
								}
								else {
									hotspots.push( { area: area, type: type, css: css, id: id,
										div: createDiv(css, type, id, '', 'g' + oResult.id) } );
								}
								new DDSend( id, "dd", { isTarget: false } );
								break;
							case 'figure':
								var area = c[i].childNodes[j].attributes.getNamedItem('area').value;
								var type = c[i].childNodes[j].nodeName;
								var css = area2css(area);
								var id = 'g' + oResult.id + 'h' + pos++;
								if (type === 'text' && c[i].childNodes[j].firstChild) {
									var textContent = c[i].childNodes[j].firstChild.firstChild.nodeValue;
									hotspots.push( { area: area, type: type, css: css, id: id,
										div: createDiv(css, type, id, textContent, 'g' + oResult.id) } );
								}
								else {
									hotspots.push( { area: area, type: type, css: css, id: id,
										div: createDiv(css, type, id, '', 'g' + oResult.id) } );
								}
								new DDSend( id, "dd", { isTarget: false } );
								break;
							case 'url':
								if(c[i].childNodes[j].firstChild)
									oResult['url'] = c[i].childNodes[j].firstChild.nodeValue;
								else oResult['url'] = '';
								break;
							/* case 'email':
								if(c[i].childNodes[j].firstChild)
									oResult['email'] = c[i].childNodes[j].firstChild.nodeValue;
								else oResult['email'] = '';
								break; */
							case 'phone':
								if(c[i].childNodes[j].firstChild)
									oResult['phone'] = c[i].childNodes[j].firstChild.nodeValue;
								else oResult['phone'] = '';
								break;
							case 'sms':
								if(c[i].childNodes[j].firstChild)
									oResult['sms'] = c[i].childNodes[j].firstChild.nodeValue;
								else oResult['sms'] = '';
								break;
						}
					}
					oResult.hotspots = hotspots;
					oResponse.push(oResult);
					oParsedResponse.results.push(oResult);
				}
			}
			return oResponse;
		}

		function iterateUnassignedContent(content) {
			var c = content.childNodes;
			var oResponse = [];
			var hotspots = [];
			var pos = 0;
			for (var i = 0; i < c.length; i++) {
				if ( 'figure' === c[i].nodeName || 'text' === c[i].nodeName ) {
					var id = 'g0h' + pos;
					// Find all the attributes...
					var area = c[i].attributes.getNamedItem('area').value;
					var type = c[i].nodeName;
					var css = area2css(area);
					if ( 'text' === c[i].nodeName && c[i].firstChild ) {
						var textContent = "";
						if ( c[i].firstChild.firstChild ) textContent = c[i].firstChild.firstChild.nodeValue;
						var textContentShort = textContent.substring(0, 50);
						hotspots.push( { area: area, type: type, css: css, id: id,
							// div: createDiv(css, type, id, textContentShort, 'g0'), textContent: textContent } );
							div: createDiv(css, type, id, textContent, 'g0'), textContent: textContent } );
					}
					else {
						hotspots.push( { area: area, type: type, css: css, id: id,
							div: createDiv(css, type, id, '', 'g0') } );
					}
					// TO-DO:  Auto-updating.
					/* oResult.convert = function () {
						this.x1 = this.area;
					} */
					new DDSend( id, "dd", { isTarget: false } );
					pos++;
				}
			}
			var oResult = { id: '0', type: 'misc', titleString: 'Misc', adRef: '', url: '', /* email: '', */ phone: '', sms: '' };
			oResult.hotspots = hotspots;
			oParsedResponse.results.push(oResult);
			return oResponse;
		}
		iterateUnassignedContent(unassignedContent);
		iterateContentGroup(ads);
		iterateContentGroup(articles);
		
	    if(bError) {
	        YAHOO.log("XML data could not be parsed: " +
				YAHOO.lang.dump(oFullResponse), "error", this.toString());
	        oParsedResponse.error = true;
	    }
	    else {
	        YAHOO.log("Parsed XML data is " +
				YAHOO.lang.dump(oParsedResponse), "info", this.toString());
	    }
	    return oParsedResponse;
	}
} );

var DataTable = YAHOO.widget.DataTable;
var DataSource = YAHOO.util.DataSource;
var CustomDataSource = YAHOO.iseemedia.util.CustomDataSource;

var document_name = "";
var section_name = "";

function next() {
	function go() {
		if ($page < ($page_max-1) ) {
			$page++;
			getLayout();
		}
	}
	var shouldContinue = null;
	if (isDirty) {
		shouldContinue = window.confirm( "Are you sure?\n\n" + exit() );
		if (shouldContinue) {
			go();
		}
	}
	else go();
}
function previous() {
	function go() {
		if ($page > 0) {
			$page--;
			getLayout();
		}
	}
	var shouldContinue = null;
	if (isDirty) {
		shouldContinue = window.confirm( "Are you sure?\n\n" + exit() );
		if (shouldContinue) {
			go();
		}
	}
	else go();
}

function getAdvertisers() {
	$advertisers = new DataSource("/iseepublish/authoring/advertisers.do?docID=" + document_name + "/" + section_name + "&temp=");
	$advertisers.connMethodPost = false;
	$advertisers.responseType = DataSource.TYPE_XML;
	// ****
	/* $advertisers = new DataSource( [
		{name: "Sony", url: "http://www.sony.com", phone: "555-1234", sms: "54321"},
		{name: "Google", url: "http://www.google.com", phone: "555-1234", sms: "54321"} ],
		{responseType: DataSource.TYPE_JSARRAY} );
	*/
	
	$advertisers.responseSchema = {
		resultNode: "advertiser",
		fields: ["name", "url", "phone", "sms"]
	};
	
	$advertisersData = new DataTable("advertisersDiv",
		[ {key:"name", label:"Advertiser"}, {key:"url", label:"Web"}, {key:"phone", label:"Phone"}, {key:"sms", label:"SMS"} ],
		$advertisers);
	$advertisersData.hide = function () {
		// document.getElementById("advertisersDiv").style.visibility = "hidden";
		document.getElementById("advertisersDiv").style.display = "none";
	}
	$advertisersData.show = function () {
		// document.getElementById("advertisersDiv").style.visibility = "hidden";
		var rs = $advertisersData.getRecordSet();
		if (rs.getLength() > 0) {
			document.getElementById("advertisersDiv").style.display = "block";
		}
	}
	// ****
	$advertisersData.subscribe("rowClickEvent", function (args) {
		$advertisersData.onEventSelectRow(args);
		// alert(YAHOO.lang.dump(args.target.innerHTML));
		var row = args.target;
		var record = $advertisersData.getRecord(row);
		var name = record.getData("name");
		var url = record.getData("url");
		var phone = record.getData("phone");
		var sms = record.getData("sms");
		var rs = $myDataTable1.getRecordSet();
		var len = rs.getLength();
		// alert(len);
		var t_record = rs.getRecord(len-1);
		// alert(t_record.getData("titleString"));
		t_record.setData("titleString", name);
		t_record.setData("url", url);
		t_record.setData("phone", phone);
		t_record.setData("sms", sms);
		// alert(name + " " + url + " " + phone + " " + sms);
		$myDataTable1.render();
		window.setTimeout( $advertisersData.hide, 500);
	} );
}

function init(docid) {
	var document_id = "";
	if (docid) {
		document_id = docid;
	}
	else {
		var href = window.location.href;
		params = href.split("&");
		for (var i = 0; i < params.length; i++) {
			var param1 = params[i].split("=");
			if (param1[0] == "doc") document_id = param1[1];
			if (param1[0] == "page") $page = param1[1];
		}
	}

	var arr = document_id.split("/");
	document_name = arr[0];
	section_name = arr[1];
	if (section_name) {
		var s = document.getElementById("section");
		s.innerHTML = "<b>Section:</b>&nbsp;&nbsp;&nbsp;" + section_name;
	}
	var t = $layout.getUnitByPosition('top');
	t.header.innerHTML = "<h2>" + document_name + "</h2>";
	getPageCount();
	getAdvertisers();
	
	var buttonSave = new YAHOO.widget.Button("save");
	buttonSave.on("click", submit);

	var buttonPrevious = new YAHOO.widget.Button("previous");
	buttonPrevious.on( "click", previous );
	var buttonNext = new YAHOO.widget.Button("next");
	buttonNext.on( "click", next );
	var buttonAdd = new YAHOO.widget.Button("add");
	buttonAdd.on("click", group);
	var buttonRemove = new YAHOO.widget.Button("remove");
	buttonRemove.on("click", remove);

	/* var sections = [
	    { text: "Main", value: "main" },
	    { text: "Sports", value: "sports" },
	    { text: "Entertainment", value: "Entertainment" }
	];
	var buttonSection = new YAHOO.widget.Button("section", {
		type: 'menu',
		label: 'Select a section',
		menu: sections } );

	function onMenuClick(p_sType, p_aArgs) {
		var oMenuItem = p_aArgs[1];
		if (oMenuItem) {
			buttonSection.set("label", ("<em>" + oMenuItem.cfg.getProperty("text") + "</em>"));
		}
	}
	
	buttonSection.on("appendTo", function () {
		buttonSection.getMenu().subscribe("click", onMenuClick);
	} );
	*/

	var buttonExit = new YAHOO.widget.Button("exit");
	buttonExit.on("click", function () { history.go(-2); } );
	getLayout();
}

var selectionQueue = [];

function addToQueue(element, arr) {
	var arrDup = [];
	for (var i = 0; i < arr.length; i++) {
		if ( arr[i] !== element) {
			arrDup.push(arr[i]);
		}
	}
        arrDup.push(element);
	return arrDup;
}

function removeFromQueue(element, arr) {
	var arrDup = [];
	for (var i = 0; i < arr.length; i++) {
		if ( arr[i] !== element) {
			arrDup.push(arr[i]);
		}
	}
	return arrDup;
}

function highlightHotspot(id) {
	var t, g;
	if ( 't' === id.substring(0,1) ) {
		g = document.getElementById( "g" + id.substring(1) );
		t = document.getElementById( id );
	}
	else {
		g = document.getElementById( id );
		t = document.getElementById( "t" + id.substring(1) );
	}
	if (g.className !== 'selected') {
		selectionQueue = addToQueue(g.id, selectionQueue);
		g.className = 'selected';
		if (t) t.className = 'selected';
	}
	else {
		selectionQueue = removeFromQueue(g.id, selectionQueue);
		g.className = 'unselected';
		if (t) t.className = 'unselected';
	}
	preview();
}	

function preview() {
	var str = '';
	for (var i = 0; i < selectionQueue.length; i++) {
		var sq = document.getElementById( selectionQueue[i] );
		var img = sq.firstChild;
		var title = img.getAttribute('title');
		if (title) {
			if (i === 0) str += '<b>' + title + '</b>' + '<br>';
			else str += title + '<br>';
		}
		else {
			// alert("left: " + sq.style.left);
			// Look for a way to optimize this:
			str += '<center><div style="position: relative; overflow: hidden; width: ' + sq.style.width + '; height: ' + sq.style.height + ';">' +
				'<img src="/iseepublish/docs/' + document_name + '/' +
				section_name + '/thumbnail?page=' + $page + '&wid=800&qlt=80"' +
				' style="position: absolute; left: -' + sq.style.left + '; top: -' + sq.style.top + ';" />' + 
				'</div></center><br>';
		}
	}
	var queue = document.getElementById('queue');
	// queue.innerHTML = YAHOO.lang.dump(selectionQueue);
	queue.innerHTML = str;
}

function reorderLayer(layerId) {
	var layer = document.getElementById('g' + layerId);
	for ( var i = 0; i < layer.childNodes.length; i++ ) {
		layer.childNodes[i].id = 'g' + layerId + 'h' + i;
	}
}

function shortenTitle(strArg) {
	// Trim any leading and trailing spaces
	var str = YAHOO.lang.trim(strArg);
	// Remove newline
	str = str.replace(/\n/g,' ');
	if (str.length < 50) {
		// No need to shorten if string is less than 50 characters
		return str;
	}
	else {
		var start = 40;
		var sub = str.substring(start, 60);
		var whitespace = start + sub.indexOf(' ');
		str = str.substring(0, whitespace);
		return str;
	}
}

function showAll(layer) {
	// Clear everything
	selectionQueue = [];
	var queue = document.getElementById('queue');
	queue.innerHTML = '';
	
	showLayer(layer);
	var layerDiv = document.getElementById('g' + layer);
	for ( var i = 0; i < layerDiv.childNodes.length; i++ ) {
		var id = layerDiv.childNodes[i].id;
		highlightHotspot(id);
	}
	return false;
}

function remove() {
	// ****
	if (oldT !== null) {
		var node = $tree.getNodeByElement(oldT);
		if (node.layer == 0) return;
		// $myDataTable1.deleteRow(String(node.layer));
		var rs = $myDataTable1.getRecordSet();
		// &&&&
		var records = rs.getRecords();
		for (var index = 0, length = records.length; index < length; index++) {
			if ( node.layer == records[index].getData('id') ) {
				var a = rs.deleteRecord(index);
				break;
			}
		}
		$myDataTable1.render();
		$tree.removeChildren(node);
		$tree.removeNode(node);
		$tree.draw();

		var layer = document.getElementById('g' + node.layer);
		var originalLayer = document.getElementById('g0');
		if (!originalLayer) {
			originalLayer = layer.cloneNode(true);
			layer.parentNode.appendChild(originalLayer);
			originalLayer.id = 'g0'
		}
		else {
			var last = originalLayer.childNodes.length;
			var nodeArr = [];
			for ( var index = 0, length = layer.childNodes.length; index < length; index++ ) {
				nodeArr.push(layer.childNodes[index]);
			}
			for ( var index = 0, length = nodeArr.length; index < length; index++ ) {
				nodeArr[index].setAttribute('id', 'g0' + 'h' + (last + index));
				originalLayer.appendChild(nodeArr[index]);
			}
		}
		layer.parentNode.removeChild(layer);
		showLayer(0);
		isDirty = true;
	}
}

function group() {
	isDirty = true;
	if (0 === selectionQueue.length) return;
	// Add new ContentGroup = row, layer, tree.
	var hotspotsDiv = document.getElementById('hotspotsDiv');
	var rs = $myDataTable1.getRecordSet();
	var records = rs.getRecords();
	var lastId = 1;
	for (var i = 0, l = records.length; i < l; i++) {
		var rId = parseInt(records[i].getData('id'));
		if (rId >= lastId) lastId = rId + 1;
	}
	// var lastId = 1 + parseInt(records[records.length - 1].getData('id'));
	var layerDiv = document.createElement('div');
	layerDiv.id = 'g' + lastId;
	layerDiv.style.position = 'absolute';
	layerDiv.style.visibility = 'hidden';
	hotspotsDiv.appendChild(layerDiv);

	var queue = document.getElementById('queue');
	if ( selectionQueue.length > 0 ) {
		queue.innerHTML = queue.innerHTML +
			"<br>" + selectionQueue.length + " items added<br>";
	}
	var myTitle = 'Ad';
	var layersToReorder = {};
	// Get all the hotspots that were selected in the queue
	for (var i = 0; i < selectionQueue.length; i++) {
		// console.log('selectionQueue[i]: ' + selectionQueue[i]);
		// Get selected hotspots
		var g = document.getElementById(selectionQueue[i]);
		var t = document.getElementById( "t" + selectionQueue[i].substring(1) );
		// Unhighlight them
		g.className = 'unselected';
		if (t) t.className = 'unselected';

		// Store the old layer
		var oldLayer = g.id.substring(1, g.id.indexOf('h'));
		
		// Get the title from the first selected hotspot
		if (i === 0) {
			var img = g.firstChild;
			var title = img.getAttribute('title');
			if (title) {
				myTitle = shortenTitle(title);
			}
		}

		// console.log('oldLayer: ' + oldLayer);
		layersToReorder[ oldLayer ] = oldLayer;
		
		// Rename hotspot id
		g.id = layerDiv.id + "h" + layerDiv.childNodes.length;
		// console.log('g.id: ' + g.id);

		// Add to hotspot layer
		layerDiv.appendChild(g);
	}
	var type = 'article';
	if (myTitle === 'Ad') type = 'ad';
	// Adding row to table
	$myDataTable1.addRow(
	 {
	  type: type,
	  titleString: myTitle,
	  id: lastId,
	  adRef: '',
	  url: '',
	  // email: '',
	  phone: '',
	  sms: ''
	 }
	);
	
	// Reset the queue
	selectionQueue = [];
	
	for (var l in layersToReorder) {
		// console.log("reorderLayer:" + l);
		reorderLayer(l);
		var n = $tree.getNodeByProperty('layer', l);
		$tree.removeChildren(n);
		// n.expand();
	}
	
	// Adding node to tree:
	var root = $tree.getRoot();
	// var node = new YAHOO.widget.TextNode({ label: myTitle, layer: lastId }, root);
	// node.labelStyle = 'icon-article';

	var icon = null;
	if (type === 'ad') icon = '<img src="images/type_ad.png" />';
	else if (type === 'article') icon = '<img src="images/type_article.png" />';
	else if (type === 'misc') icon = '<img src="images/type_misc.png" />';
	var node = new YAHOO.widget.HTMLNode(
		{ layer: lastId,
			html:'<div id="t' + lastId + '" ondblclick="showAll(' + lastId + ');" onclick="showLayer(' + lastId + ');" class="unselected" style="cursor: pointer;">' +
			icon + " " + myTitle + '</div>'},
		root, false, true);
	node.layer = lastId;
	node.setDynamicLoad(fnLoadData);
	$tree.draw();
	
	if (type === 'ad') {
		$advertisersData.show();
	}
}

var keyListener1 = new YAHOO.util.KeyListener(document,
	{ ctrl: true, keys: 71 }, // YAHOO.util.KeyListener.KEY.ENTER },
	{ fn: function (eType, fireArgs) {
			YAHOO.util.Event.stopEvent(fireArgs[1]);
			group();
		},
		scope: this,
		correctScope: true } );
keyListener1.enable();

$eType = null;
$fireArgs = null;
$e = null;
var keyListener2 = new YAHOO.util.KeyListener(document,
	{ keys: YAHOO.util.KeyListener.KEY.ENTER },
	// { keys: 28 },
	{ fn: function (eType, fireArgs) {
			var e = YAHOO.util.Event.getTarget(fireArgs[1]);
			$e = e;
			$eType = eType;
			$fireArgs = fireArgs;
			YAHOO.util.Event.stopEvent(fireArgs[1]);
			if (e.nodeName !== "INPUT") group();
		},
		scope: this,
		correctScope: true } );
keyListener2.enable();

var keyDuplicate = new YAHOO.util.KeyListener(document,
	{ ctrl: true, keys: 67 },
	{ fn: function (eType, fireArgs) {
			YAHOO.util.Event.stopEvent(fireArgs[1]);
			duplicateHotspot();
		},
		scope: this,
		correctScope: true } );
keyDuplicate.enable();

function duplicateHotspot() {
	if ( selectionQueue.length === 1 ) {
		// console.log(selectionQueue[0]);
		var element = document.getElementById(selectionQueue[0]);
	}
}

DDSend = function(id, sGroup, config) {
	Event = YAHOO.util.Event;
	
	if (id) {
		// bind this drag drop object to the
		// drag source object
		this.init(id, sGroup, config);
		this.initFrame();
		if (config.isTarget === false) this.isTarget = false;
	}
	// this.config = config;
	var s = this.getDragEl().style;
	s.borderColor = "transparent";
	s.backgroundColor = "#f6f5e5";
	s.opacity = 0.76;
	s.filter = "alpha(opacity=76)";
	// s.border = '2px dotted red';
	
	// Event.on( this.getEl(), 'click',
	Event.on( this.getEl(), 'mousedown',
		function() { 
			highlightHotspot( this.getEl().id );
		}, this, true );

	Event.on( this.getEl(), 'dragover',
		function() {
			var element = this.getEl();
			if ( element.className !== 'selected' ) {
				highlightHotspot( element.id );
			}
		}, this, true );

	Event.on( this.getEl(), 'dblclick',
		function() {
			var element = this.getEl();
			if ( "figure" === element.getAttribute("_type") ) {
				var resize = new YAHOO.util.Resize( element, {
					handles: 'all',
					knobHandles: false,
					proxy: false,
					draggable: true } );
			}
			else {
				var resize = new YAHOO.util.Resize( element, {
					handles: 'all',
					knobHandles: false,
					proxy: false,
					draggable: true } );
				resize.subscribe("resize", function(e) {
					element.setAttribute("_moved", "true");
				} );
				resize.subscribe("dragEvent", function(e) {
					element.setAttribute("_moved", "true");
				} );
				if (element.getAttribute("_moved") === "true") {
					// *****
					var left = parseInt(element.style.left);
					var top = parseInt(element.style.top);
					var x1 = left / pageImageWidth;
					var y1 = top / pageImageHeight;
					var x2 = (left + parseInt(element.style.width)) / pageImageWidth;
					var y2 = (top + parseInt(element.style.height)) / pageImageHeight;

					var uri = "/iseepublish/authoring/layout.do?docID=" + document_name + "/" + section_name + "&pageIndex=" + $page + "&action=getContent&area=" + x1 + "," + y1 + "," + x2 + "," + y2;
					YAHOO.util.Connect.setDefaultPostHeader(false);
					YAHOO.util.Connect.initHeader('Content-Type', 'text/xml; charset=utf-8', true);
					var callback = {
						success: function(o) {
							// console.dir(o);
							if ( o.responseXML.firstChild && o.responseXML.firstChild.firstChild ) {
								var text = o.responseXML.firstChild.firstChild.nodeValue;
								// console.log(text);
								var img = element.firstChild;
								img.setAttribute('title', text);
								img.setAttribute('alt', text);
								// console.log(element.id);
								preview();
							}
						}
					}
					YAHOO.util.Connect.asyncRequest( 'GET', uri , callback, null );
				}
			}
		}, this, true );
}

// Extend proxy so we don't move the whole object around
DDSend.prototype = new YAHOO.util.DDProxy();
DDSend.prototype.onDragDrop = function(e, id) {
	// this is called when the source object dropped on a valid target
	window.status = "DragDrop: " + this.id + " was dropped on " + id;
	// send a request to the server to handle the drag drop
	var el = YAHOO.util.DDM.getElement(id);
	el.style.border = "1px dotted blue";
}
DDSend.prototype.startDrag = function(x, y) {
	// called when source object first selected for dragging
	// draw a red border around the drag object we create
	var dragEl = this.getDragEl();
	var clickEl = this.getEl();

	dragEl.innerHTML = clickEl.innerHTML;
	dragEl.className = clickEl.className;
	dragEl.style.color = clickEl.style.color;
	dragEl.style.border = "1px dotted red";
}
DDSend.prototype.onDragEnter = function(e, id) {
	var el;
	// This is called anytime we drag over a potential valid target
	if ("string" == typeof id) {
		el = YAHOO.util.DDM.getElement(id);
	} else {
		el = YAHOO.util.DDM.getBestMatch(id).getEl();
	}
	// Highlight the target in red
	el.style.border = "1px dotted red";
}
DDSend.prototype.onDragOut = function(e, id) {
	var el;
	// This is called anytime we drag out a potential valid target
	if ("string" == typeof id) {
		el = YAHOO.util.DDM.getElement(id);
	} else {
		el = YAHOO.util.DDM.getBestMatch(id).getEl();
	}
	// Remove the highlight
	el.style.border = "1px dotted blue";
}
DDSend.prototype.endDrag = function(e) {
	// Override the default behavior
}

var oldT = null;
function showLayer(layer) {
	var queue = document.getElementById('queue');
	queue.innerHTML = '';
	hideLayers();
	var layerDiv = document.getElementById('g' + layer);
	if (layerDiv) layerDiv.style.visibility = 'visible';
	var t = document.getElementById('t' + layer);
	if (t) t.style.border = "1px dotted blue";
	if (oldT && t !== oldT) oldT.style.border = "";
	oldT = t;
}

fnLoadData = function (node, fnCallback) {
	// alert(node.layer);
	// var hotspotsDiv = document.getElementById('hotspotsDiv');
	// var layer = hotspotsDiv.childNodes[node.layer].childNodes;
	var layerId = 'g' + node.layer; 
	var layerDiv = document.getElementById(layerId);
	// If doesn't exist then return
	if (!layerDiv) {
		fnCallback();
		return;
	}
	var layer = layerDiv.childNodes;
	var icon = '';
	for (var i = 0; i < layer.length; i++) {
		var id = 't' + layer[i].id.substring(1);
		var type = layer[i].getAttribute('_type');
		if (type === 'text') icon = '<img src="images/hotspot_text.png" />';
		else if (type === 'figure') icon = '<img src="images/hotspot_image.png" />';
		var subNode = new YAHOO.widget.HTMLNode(
			{ layer: id,
				html:'<div alt="' + id + '" title="' + id +
				'" onclick="highlightHotspot(\'' + id +
				'\')" id="' + id + '" class="unselected" style="cursor: pointer; border: 1px dotted blue;">' +
				icon + " " + type + " " + (i+1) + '</div>'},
			node);
	}
	fnCallback();
}

function hideLayers() {
	var hotspotsDiv = document.getElementById('hotspotsDiv');
	// Make all hotspots invisible
	for (var i = 0; i < hotspotsDiv.childNodes.length; i++) {
		if ( 'visible' === hotspotsDiv.childNodes[i].style.visibility ) {
			hotspotsDiv.childNodes[i].style.visibility = 'hidden';
		}
	}
}

function treeInit() {
	
	var tree = new YAHOO.widget.TreeView("treeDiv");
	if ($debug) $tree = tree;
	var root = tree.getRoot();
	var rs = $myDataTable1.getRecordSet();
	var records = rs.getRecords();
	for (var i = 0; i < records.length; i++) {
		// var layer = rs.getRecordIndex(records[i]);
		var layer = records[i].getData('id');
		var title = records[i].getData('titleString');
		var type = records[i].getData('type');
		
		/*
		var node = new YAHOO.widget.TextNode( { label: title, layer: layer }, root);
		if (type === 'ad') node.labelStyle = 'icon-ad';
		else if (type === 'article') node.labelStyle = 'icon-article';
		else if (type === 'misc') node.labelStyle = 'icon-misc';
		*/

		var icon = null;
		if (type === 'ad') icon = '<img src="images/type_ad.png" />';
		else if (type === 'article') icon = '<img src="images/type_article.png" />';
		else if (type === 'misc') icon = '<img src="images/type_misc.png" />';
		var node = new YAHOO.widget.HTMLNode(
			{ layer: layer,
				// class="unselected" 
				html:'<div id="t' + layer + '" ondblclick="showAll(' + layer + ');" onclick="showLayer(' + layer + ');" style="cursor: pointer;">' +
				icon + " " + title + '</div>'},
			root, false, true);
		node.layer = layer;
		node.setDynamicLoad(fnLoadData);
	}

	tree.subscribe("labelClick", function (node) {
		if (node.depth === 0) {
			// alert(node.index-1);
			showLayer(node.layer);
		}
	} );
	tree.draw();
}

var initialLoad = true;
var pageImageWidth = 0;
var pageImageHeight = 0;
function getLayout() {
	isDirty = false;
	var queue = document.getElementById('queue');
	queue.innerHTML = '';
	selectionQueue = [];
	var hotspotsDiv = document.getElementById('hotspotsDiv');	
	hotspotsDiv.innerHTML = '';
	// To-do: Zooming:
	// http://pe1850a.iseemedia.com:8088/iseepublish/docs/USAToday_test-usatoday/thumbnail?page=1&wid=400&rgn=0,0,0.5,0.5
	// var imgGroup = new YAHOO.util.ImageLoader.group('imgDiv', null, 30);
	// imgGroup.foldConditional = true;
	// imgGroup.registerSrcImage('img', '/iseepublish/docs/USAToday_test-usatoday/thumbnail?page=' + $page + '&wid=800&qlt=80');
	
	// var img = document.getElementById('img');
	var img = document.getElementById('img');
	img.src = '/iseepublish/docs/' + document_name + "/" + section_name + '/thumbnail?page=' + $page + '&wid=800&qlt=80';
	img.onload = function () {
		// if (initialLoad) {
		if (true) {
			initialLoad = false;
			pageImageWidth = img.width;
			pageImageHeight = img.height;
			loadData();
			// alert('in onload');
			img.onload = null;
		}
	}
	// if (!initialLoad) loadData();
	// For debugging
	img.x_onmousedown = function (e) {
		e = e || window.event;
		/* var str = 'clientX: ' + e.clientX + ' clientY: ' + e.clientY + '\n' +
		'layerX: ' + e.layerX +	' layerY: ' + e.layerY + '\n' +
		'offsetX: ' + e.offsetX + ' offsetY: ' + e.offsetY + '\n' +
		'pageX: ' + e.pageX + ' pageY: ' + e.pageY + '\n' +
		'screenX: ' + e.screenX + ' screenY: ' + e.screenY + '\n' +
		'x: ' + e.x + ' y: ' + e.y;
		alert(str); */
		
	}

	img.style.cursor = 'crosshair';
	
	/*
	// For stopping the cursor changing when dragging the image.
	img.style.visibility = 'hidden';
	img.parentNode.cursor = 'crosshair';
	img.parentNode.style.backgroundRepeat = 'no-repeat';
	img.parentNode.style.backgroundImage = 'url(/iseepublish/docs/' + document_name + "/" + section_name + '/thumbnail?page=' + $page + '&wid=800&qlt=80)';
	// img.parentNode.style.backgroundColor = 'url('+ img.src +')';
	*/
	
	var p = document.getElementById("page");
	if ($page_max > 1) {
		p.innerHTML = "<b>Page " + ($page + 1) + " of " + $page_max + "</b>";
	}
	else p.innerHTML = "<b>Page " + ($page + 1) + "</b>";
}

function loadData() {
	// $myDataSource1 = new DataSource("/iseepublish/authoring/layout.do?docID=USAToday_test-usatoday&pageIndex=" + $page + "&action=getLayout&temp=");
	$myDataSource1 = new CustomDataSource("/iseepublish/authoring/layout.do?docID=" + document_name + "/" + section_name + "&pageIndex=" + $page + "&disableCache=" + Math.floor(Math.random()*9999) + "&action=getLayout&temp=" );
	$myDataSource1.connMethodPost = false;
	$myDataSource1.responseType = DataSource.TYPE_XML;
	
	// For special processing of XML data
	$myDataSource1.doBeforeCallback = function (oRequest, oFullResponse, oParsedResponse) {
	    $oRequest = oRequest;
		$oFullResponse = oFullResponse;
		$oParsedResponse = oParsedResponse;
		return oParsedResponse;
	}

	$myDataSource1.responseSchema = {
		resultNode: "contentGroup",
		fields: [ {key:"id"},
			{key:"type"},
			{key:"titleString"},
			{key:"adRef"},
			{key:"url"},
			// {key:"email"},
			{key:"phone"},
			{key:"sms"} ]
	};
	var customFormatter = function (el, oRecord, oColumn, oData) {
		el.innerHTML = '<input size="40" value="' + oData + '" /> ';
	}
	var customFormatterSmall = function (el, oRecord, oColumn, oData) {
		el.innerHTML = '<input size="10" value="' + oData + '" /> ';
	}
	$myDataTable1 = new DataTable("dataDiv",
		[ // {key:"titleString", label:"Title", formatter:customFormatter},
            {key:"titleString", label:"Title", editor:"textbox"},
			// {key:"type", label:"Type", formatter:"dropdown", dropdownOptions:["ad","article","misc"]},
			{ key:"type", label:"Type", editor:"radio", editorOptions:{ radioOptions:["ad","article"],disableBtns:false } },
			{key:"adRef", label:"adRef", editor:"textbox"},
			{key:"id", label:"id", editor:"textbox"},
			{key:"url", label:"Web", editor:"textbox"},
			// {key:"email", label:"Email", editor:"textbox"},
			{key:"phone", label:"Phone", editor:"textbox"},
			{key:"sms", label:"SMS", editor:"textbox"} ],
		$myDataSource1,
		{selectionMode:"single"} );

		// TODO:  Select tree and layer
		$myDataTable1.subscribe("rowClickEvent", $myDataTable1.onEventSelectRow );

		$myDataTable1.subscribe("initEvent", function() {
			treeInit();
		} );
		
        $myDataTable1.subscribe("cellClickEvent", function(oArgs) {
			$myDataTable1.onEventShowCellEditor(oArgs);
			YAHOO.util.Event.stopEvent(oArgs)
			} );
}

function getPageCount() {
	$myDataSource2 = new DataSource("/iseepublish/authoring/layout.do?docID=" + document_name + "/" + section_name + "&action=getPageCount&temp=");
	$myDataSource2.connMethodPost = false;
	$myDataSource2.responseType = DataSource.TYPE_XML;
	$myDataSource2.responseSchema = {
		resultNode: "pages",
		fields: ["count"]
	};
	
	$myDataTable2 = new DataTable("getLayoutDiv", [ {key:"count"} ], $myDataSource2);
	
	$myDataTable2.subscribe("initEvent", function() { 
		var rs = $myDataTable2.getRecordSet();
		var record = rs.getRecord(0);
		var count = record.getData('count');
		if (count > 0) $page_max = count;
		var p = document.getElementById("page");
		p.innerHTML = "<b>Page " + ($page + 1) + " of " + count + "</b>";
	} );
}

function submit() {
	function xmlEncode(str){
			var retVal = "";
			if (str != null){
				   retVal = str.toString();
				   retVal = retVal.replace(/\&/g, "&amp;");
				   retVal = retVal.replace(/\"/g, "&quot;");
				   retVal = retVal.replace(/\'/g, "&apos;");
				   retVal = retVal.replace(/\</g, "&lt;");
				   retVal = retVal.replace(/\>/g, "&gt;");
			}
			return retVal;
	}
	var xmlDoc = '<?xml version="1.0" encoding="UTF-8"?>\r\n<iseePageLayout>\r\n' +
		'<page section="' + section_name + '">\r\n';

	var articles = '<articles>\r\n';
	var ads = '<ads>\r\n';
	var unassignedContent = '<unassignedContent>\r\n';

	var rs = $myDataTable1.getRecordSet();
	for (var i = 0; i < rs.getLength(); i++ ) {
		var t = '';	
		var record = rs.getRecord(i);
		var type = record.getData('type');
		var adRef = record.getData('adRef');
		if (!adRef) adRef = '';

		if (type == 'article') {
			t += '<contentGroup id="' + record.getData('id') + '" type="' + record.getData('type') +
				'" titleString="' + xmlEncode(record.getData('titleString')) + '" adRef="' + adRef + '">\r\n';
		}
		else if (type == 'ad') {
			t += '<contentGroup id="' + record.getData('id') + '" type="' + record.getData('type') +
				'" titleString="' + xmlEncode(record.getData('titleString')) + '">\r\n';
		}
		// Iterate through hotspots
		var areas = document.getElementById('g' + record.getData('id'));
		for (var ai = 0; ai < areas.childNodes.length; ai++) {
			var node = areas.childNodes[ai].style;
			var tag = areas.childNodes[ai].getAttribute("_type");
			var left = parseInt(node.left);
			var top = parseInt(node.top);
			var x1 = parseInt(node.left) / pageImageWidth;
			var y1 = parseInt(node.top) / pageImageHeight;
			var x2 = (left + parseInt(node.width)) / pageImageWidth;
			var y2 = (top + parseInt(node.height)) / pageImageHeight;
			// console.log(tag + " " + x1 + "," + y1 + "," + x2 + "," + y2);
			if ( 'text' == tag ) {
				var alt = areas.childNodes[ai].firstChild.getAttribute('alt');
				if (alt) t += '<text area="'+ x1 + ',' + y1 + ',' + x2 + ',' + y2 + '" newParagraph="true"><content>' + xmlEncode(alt) + '</content></text>\r\n';
				else t += '<text area="'+ x1 + ',' + y1 + ',' + x2 + ',' + y2 + '" newParagraph="true"></text>\r\n';
			}
			else if ( 'figure' == tag ) {
				t += '<figure area="'+ x1 + ',' + y1 + ',' + x2 + ',' + y2 +
					'" captionText="' + xmlEncode(record.getData('titleString')) + '"></figure>\r\n';
			}
		}
		if (type == 'article' || type == 'ad') {
			t += '<url>' + xmlEncode(record.getData('url')) + '</url>'
			// + '<email>' + xmlEncode(record.getData('email')) + '</email>'
			+ '<phone>' + xmlEncode(record.getData('phone')) + '</phone>'
			+ '<sms>' + xmlEncode(record.getData('sms')) + '</sms>\r\n';
		}
		if (type == 'article') articles += t + '</contentGroup>\r\n';
		else if (type == 'ad') ads += t + '</contentGroup>\r\n';
		else if (type == 'misc') unassignedContent += t + '</unassignedContent>\r\n';
	}
	// xmlDoc += t;
	articles += '</articles>\r\n'
	ads += '</ads>\r\n'
	xmlDoc += articles + ads + unassignedContent + '</page>\r\n</iseePageLayout>';
	var uri = "/iseepublish/authoring/update.do?docID=" + document_name + "/" + section_name + "&pageIndex=" + $page;
	YAHOO.util.Connect.setDefaultPostHeader(false);
	YAHOO.util.Connect.initHeader('Content-Type', 'text/xml; charset=utf-8', true);
	YAHOO.util.Connect.asyncRequest( 'POST', uri , null, xmlDoc );
	isDirty = false;
}
// YAHOO.util.Event.on(window, "load", init);
YAHOO.util.Event.onDOMReady( function () { init("<s:text name="%{docid}"/>"); } );
</script>
</body>
</html>
