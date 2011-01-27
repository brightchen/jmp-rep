var iseepublish = function() {
	Ext.BLANK_IMAGE_URL = 'images/s.gif';
	var SERVER = "../"; // "http://localhost:8080/iseepublish/";
	var DOCUMENT = "businessweek";
	var pageIndex = 0;

	// Private fields
	// TODO convert some to non-static
	var view;
	var ds;
	var rsm;
	var layout;
	var pageImageWidth = 0;
	var pageImageHeight = 0;
	var treeRoot;
	var lastSelected;
	var uniqueId = 0;
	var pageTotal = 0;
	var proxy;
	var reader;
	var record;
	var grid = null;
	var cm;
	var imgBody;
	var comboSection;
	var pageCombo;
	var tree;
	var ss;

	// Private methods
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

function xmlDecode(str){
        var retVal = "";
        if (str){
             retVal = str.toString();
             retVal = retVal.replace(/\&amp\;/g, "&");
             retVal = retVal.replace(/\&quot\;/g, '"');
             retVal = retVal.replace(/\&apos\;/g, "'");
             retVal = retVal.replace(/\&lt\;/g, "<");
             retVal = retVal.replace(/\&gt\;/g, ">");
       }
       return retVal;
}

	/* function createToolbar(north)
	{
			// Start of Menu
			var menu = new Ext.menu.Menu( {id: 'menu'} );
			var tb = new Ext.Toolbar(north.getEl());

			tb.add( { handler: function() { iseepublish.submit(); }, icon: 'images/submit.gif', cls: 'x-btn-icon', tooltip: "Save ( Ctrl-S )" } );
			tb.addSeparator();
			pageCombo = new Ext.form.Field({
				value: '' + (1+pageIndex) + ' of ' + pageTotal,
				width: 50
			});
			tb.add( { handler: iseepublish.previousPage, icon: 'images/page-prev-disabled.gif', cls: 'x-btn-icon', tooltip: "Previous Page ( Ctrl-[ )" } );
			tb.addField(pageCombo);
			tb.add( { handler: iseepublish.nextPage, icon: 'images/page-next-disabled.gif', cls: 'x-btn-icon', tooltip: "Next Page ( Ctrl-] )" } );
			tb.addSeparator();
			tb.add(
				{ handler: iseepublish.addRecord, icon: 'images/article-add.gif', cls: 'x-btn-icon', tooltip: "Add Article" },
				{ handler: iseepublish.removeRecord, icon: 'images/article-remove.gif', cls: 'x-btn-icon', tooltip: "Remove Article" }
			);
			tb.add( {
				enableToggle: true,
				// toggleHandler: onItemToggle,
				icon: "images/draw.gif",
				cls: 'x-btn-icon',
				tooltip: "Draw Regions ( Ctrl-D )",
				pressed: false
			} );
			tb.addSeparator();
			section = [
				["Main"],
				["Business"],
				["Entertainment"],
				["Life"],
				["Sports"],
				["World"]
			];
			var store = new Ext.data.SimpleStore({
				fields: ['section'],
				data : section
			});
			comboSection = new Ext.form.ComboBox({
				store: store,
				displayField:'section',
				typeAhead: true,
				// mode: 'local',
				triggerAction: 'all',
				emptyText:'Section...',
				selectOnFocus:true,
				width:100
			});
			tb.addField(comboSection);
	    		function onItemToggle(item, pressed) {
				// placeholder
	    		}
			return comboSection;
		} */
	
	function populateTree() {
		// Clean the tree
		while(treeRoot.firstChild) {
			treeRoot.removeChild(treeRoot.firstChild);
		}

		for (var i = 0; i < ds.getCount(); i++) {
			var record = ds.getAt(i);
			var titleString = record.get('titleString');
			var recordID = 't' + record.get('id');
			// Node can't be 0 zero because that is reserved for the root of tree
			// recordID++;
			
			var node = new Ext.tree.TreeNode( {ds:'', id: recordID, text: titleString, cls:'article-node', allowDrag:false /* true */, allowDrop:true} );

			var areas = record.get('areas');
			if (areas && areas.length) {
				for (var x = 0; x < areas.length; x++) {
					node.appendChild(new Ext.tree.TreeNode( {leaf: true, text: '' + (x+1) + ': ' + areas[x][0]} ));
				}
			}

			node.on('click', function(node) {
				// selectRowById(parseInt(node.id));
				selectRowById(node.id.substr(1));
			} );
			node.on('textchange', function(node, text) {
				// changeRowById(parseInt(node.id), text);
				changeRowById(node.id.substr(1), text);
			} );
			treeRoot.appendChild(node);
		}
	};

	function selectRowById(id) {
		for (var i = 0; i < ds.getCount(); i++) {
			var record = ds.getAt(i);
			var gid = record.get('id');
			if (id == gid) {
				rsm.selectRow(i);
				return;
			}
		}
	}

	function changeRowById(id, text) {
		for (var i = 0; i < ds.getCount(); i++) {
			var record = ds.getAt(i);
			var gid = record.get('id');
			if (id == gid) {
				record.set('titleString', text);
				return;
			}
		}
	}

	function addTreeNode(id, text) {
		var node = new Ext.tree.TreeNode( {id: '' + id, text: text + ' ' + id, cls:'article-node', allowDrag:true, allowDrop:false} );
		node.on('click', function(node) {
			rsm.selectRow(parseInt(node.id));
		} );
		node.on('textchange', function(node, text) {
			// var recordChange = ds.getAt(node.id);
			// if (recordChange) recordChange.set('titleString', text);
		} );
		treeRoot.appendChild(node);
	};

	function getTitles(dataStore) {
		var titles = "";
		for (var i = 0; i < dataStore.getTotalCount(); i++) {
			var dataRecord = dataStore.getAt(i);
			titles += dataRecord.get("@titleString") + ", ";
		}
		return titles;
	};

	function changeSelection(rsm, selected) {

		var record = ds.getAt(selected);
		var node = treeRoot.item(selected);
		node.select();

		initHotspots(imgBody);
	};

	function focusHotspot(id) {	
		var hs = document.getElementById(id);
		if (hs != null) {
			// hs.style.backgroundColor = "yellow";
			// hs.style.opacity = "0.50";
			// hs.style.filter = "alpha(opacity=50)";
			hs.style.borderColor = "red";
		}
	}

	function blurHotspot(id) {	
		var hs = document.getElementById(id);
		if (hs != null) {
			// hs.style.backgroundColor = "";
			// hs.style.opacity = "";
			// hs.style.filter = "";
			hs.style.borderColor = "blue";
		}
	}

	function updateAll()
	{
		initHotspots(imgBody);
		populateTree();
	}

	function updateDataStore()
	{
			// proxy = new Ext.data.HttpProxy( {url: SERVER + 'authoring/layout.do?disableCache=' + Math.floor(Math.random()*9999) + '&docID=' + DOCUMENT + '&pageIndex=' + pageIndex + '&action=getLayout'} );
			proxy = new Ext.data.MemoryProxy();
			// reader = new Ext.data.XmlReader2( {record: ['unassignedContent', 'contentGroup'] }, record );
			reader = new Ext.data.ArrayReader();
			// reader = new Ext.data.XmlReader2( {record: ['unassignedContent', 'contentGroup'] }, record );
			// *** TODO
			// reader = new XmlReader( {record: ['unassignedContent', 'contentGroup'] }, record );
			ds = new Ext.data.Store( {
				proxy: proxy,
				reader: reader
				// sortInfo: {field: 'titleString'}
			} );
			var layoutXML = '';
			var layoutString = '';
			try {
				layoutXML = XML.load(SERVER + 'authoring/layout.do?disableCache=' + Math.floor(Math.random()*9999) + '&docID=' + DOCUMENT + '&pageIndex=' + pageIndex + '&action=getLayout');
				layoutString = XML.serialize(layoutXML);
			}
			catch (ex) {
        		alert('Login:  Session timed-out. Please log back in.');				
				location = '../';
				return;
			}
			// alert(layoutString);
			var ph_xml = document.getElementById("placeholder_xml");
			ph_xml.innerHTML = "<h3>Debugging:</h3>";
			ph_xml.innerText += layoutString;
			// ph_xml.innerHTML += "<br>" + xml2json.parser(layoutString, "", "html");
			var pageObject = xml2json.parser(layoutString);
			if (!pageObject.iseepagelayout) {
        		alert('Login:  Session timed-out. Please log back in.');				
				location = '../';
				return;
			}
			var page1 = pageObject.iseepagelayout.page;

			if (page1.articles.contentgroup && !page1.articles.contentgroup.length) page1.articles.contentgroup = [page1.articles.contentgroup];
			if (page1.ads.contentgroup && !page1.ads.contentgroup.length) page1.ads.contentgroup = [page1.ads.contentgroup];
			if (page1.unassignedcontent.text && !page1.unassignedcontent.text.length) page1.unassignedcontent.text = [page1.unassignedcontent.text];
			if (page1.unassignedcontent.figure && !page1.unassignedcontent.figure.length) page1.unassignedcontent.figure = [page1.unassignedcontent.figure];

			var sectionString = '';
			if(page1.section) {
				sectionString = '' + page1.section;
				comboSection.setValue(page1.section);
			}
			ph_xml.innerHTML += "<b>section:</b>" + sectionString;

			// TODO

			ph_xml.innerHTML += "<br><b>unassignedContent:</b>";
			if (page1.unassignedcontent) {
				var areas = [];
				if(page1.unassignedcontent.figure) for (var i = 0; i < page1.unassignedcontent.figure.length; i++) {
					// Unassigned figure
					var str = page1.unassignedcontent.figure[i].area;
					ph_xml.innerHTML += '<br>figure: ' + str;
					// TODO use better IDs
					var id = areas.length;
					var tag = 'figure';
					var point = str.split(',');
					var x1 = parseFloat(point[0]);
					var y1 = parseFloat(point[1]);
					var x2 = parseFloat(point[2]);
					var y2 = parseFloat(point[3]);
					areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
				}
				if(page1.unassignedcontent.text) for (var i = 0; i < page1.unassignedcontent.text.length; i++) {
					// Unassigned text
					var str = page1.unassignedcontent.text[i].area;
					ph_xml.innerHTML += '<br>text: ' + str;
					var id = areas.length;
					var tag = 'text';
					var point = str.split(',');
					var x1 = parseFloat(point[0]);
					var y1 = parseFloat(point[1]);
					var x2 = parseFloat(point[2]);
					var y2 = parseFloat(point[3]);
					var content = page1.unassignedcontent.text[i].content;
					ph_xml.innerHTML += '<br>text value: ' + content;
					// alert(text.length); **** TODO ****
					areas[areas.length] = [ id, id, tag, x1, y1, x2, y2, content ];
				}
				var areasRecordDef = Ext.data.Record.create([
					{name: 'id', mapping: 1},
					{name: 'tag', mapping: 2},
					{name: 'x1', mapping: 3},
					{name: 'y1', mapping: 4},
					{name: 'x2', mapping: 5},
					{name: 'y2', mapping: 6},
					{name: 'content', mapping: 7}
				]);
				var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
				var areasStore = new Ext.data.Store( {
						proxy: new Ext.data.MemoryProxy(areas),
						reader: areasReader
				} );
				areasStore.load();
				// alert("areas.length: " + areas.length); // ***
				var p = new record( { 'id': uniqueId++, "titleString": 'Misc', "type": 'misc', "url": '', "email": '', "phone": '', "sms": '', "adRef": '', 'areas': areasStore } );
				ds.add(p);
			}

			if (page1.ads.contentgroup) ph_xml.innerHTML += "<br><b>Number of ads:</b>" + page1.ads.contentgroup.length;
			if (page1.ads.contentgroup) {
				for (var i = 0; i < page1.ads.contentgroup.length; i++) {
					var areas = [];
					ph_xml.innerHTML += "<br><b>Ad " + i + ":</b>";
					if (page1.ads.contentgroup[i].text && !page1.ads.contentgroup[i].text.length) page1.ads.contentgroup[i].text = [page1.ads.contentgroup[i].text];
					if (page1.ads.contentgroup[i].figure && !page1.ads.contentgroup[i].figure.length) page1.ads.contentgroup[i].figure = [page1.ads.contentgroup[i].figure];
					if (page1.ads.contentgroup[i].text) {
						for (var ii = 0; ii < page1.ads.contentgroup[i].text.length; ii++) {
							// Ads text
							var str = page1.ads.contentgroup[i].text[ii].area;
							ph_xml.innerHTML += '<br>text: ' + str;
							var id = areas.length;
							var tag = 'text';
							var point = str.split(',');
							var x1 = parseFloat(point[0]);
							var y1 = parseFloat(point[1]);
							var x2 = parseFloat(point[2]);
							var y2 = parseFloat(point[3]);
							areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
						}
					}
					if (page1.ads.contentgroup[i].figure) {
						for (var ii = 0; ii < page1.ads.contentgroup[i].figure.length; ii++) {
							// Ads figure
							var str = page1.ads.contentgroup[i].figure[ii].area;
							ph_xml.innerHTML += '<br>figure: ' + str;
							var id = areas.length;
							var tag = 'figure';
							var point = str.split(',');
							var x1 = parseFloat(point[0]);
							var y1 = parseFloat(point[1]);
							var x2 = parseFloat(point[2]);
							var y2 = parseFloat(point[3]);
							areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
						}
					}
					var areasRecordDef = Ext.data.Record.create([
						{name: 'id', mapping: 1},
						{name: 'tag', mapping: 2},
						{name: 'x1', mapping: 3},
						{name: 'y1', mapping: 4},
						{name: 'x2', mapping: 5},
						{name: 'y2', mapping: 6}
					]);
					var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
					var areasStore = new Ext.data.Store( {
						proxy: new Ext.data.MemoryProxy(areas),
						reader: areasReader
					} );
					areasStore.load();
					var str = page1.ads.contentgroup[i];
					var p = new record( { 'id': uniqueId++, "titleString": str.titlestring, "type": str.type, "url": (typeof(str.url)=='string')?str.url:'', "email": (typeof(str.email)=='string')?str.email:'', "phone": (typeof(str.phone)=='string'||typeof(str.sms)=='number')?str.phone:'', "sms": (typeof(str.sms)=='string'||typeof(str.sms)=='number')?str.sms:'', "adRef": (typeof(str.adref)=='number')?str.adref:'', 'areas': areasStore } );
					ds.add(p);
				}
			}

			if (page1.articles.contentgroup) {
				for (var i = 0; i < page1.articles.contentgroup.length; i++) {
					var areas = [];
					ph_xml.innerHTML += "<br><b>Article " + i + ":</b>";
					if (page1.articles.contentgroup[i].text && !page1.articles.contentgroup[i].text.length) page1.articles.contentgroup[i].text = [page1.articles.contentgroup[i].text];
					if (page1.articles.contentgroup[i].figure && !page1.articles.contentgroup[i].figure.length) page1.articles.contentgroup[i].figure = [page1.articles.contentgroup[i].figure];
					if (page1.articles.contentgroup[i].text) {
						for (var ii = 0; ii < page1.articles.contentgroup[i].text.length; ii++) {
							// Articles text
							var str = page1.articles.contentgroup[i].text[ii].area;
							// alert(str);
							ph_xml.innerHTML += '<br>text: ' + str;
							var id = areas.length;
							var tag = 'text';
							var point = str.split(',');
							var x1 = parseFloat(point[0]);
							var y1 = parseFloat(point[1]);
							var x2 = parseFloat(point[2]);
							var y2 = parseFloat(point[3]);
							areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
						}
					}
					if (page1.articles.contentgroup[i].figure) {
						for (var ii = 0; ii < page1.articles.contentgroup[i].figure.length; ii++) {
							// Articles figure
							var str = page1.articles.contentgroup[i].figure[ii].area;
							ph_xml.innerHTML += '<br>figure: ' + str;
							var id = areas.length;
							var tag = 'figure';
							var point = str.split(',');
							var x1 = parseFloat(point[0]);
							var y1 = parseFloat(point[1]);
							var x2 = parseFloat(point[2]);
							var y2 = parseFloat(point[3]);
							areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
						}
					}
					var areasRecordDef = Ext.data.Record.create([
						{name: 'id', mapping: 1},
						{name: 'tag', mapping: 2},
						{name: 'x1', mapping: 3},
						{name: 'y1', mapping: 4},
						{name: 'x2', mapping: 5},
						{name: 'y2', mapping: 6}
					]);
					var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
					var areasStore = new Ext.data.Store( {
						proxy: new Ext.data.MemoryProxy(areas),
						reader: areasReader
					} );
					areasStore.load();
					var str = page1.articles.contentgroup[i];
					var p = new record( { 'id': uniqueId++, "titleString": str.titlestring, "type": str.type, "url": (typeof(str.url)=='string')?str.url:'', "email": (typeof(str.email)=='string')?str.email:'', "phone": (typeof(str.phone)=='string'||typeof(str.sms)=='number')?str.phone:'', "sms": (typeof(str.sms)=='string'||typeof(str.sms)=='number')?str.sms:'', "adRef": (typeof(str.adref)=='number')?str.adref:'', 'areas': areasStore } );
					ds.add(p);
				}
			}
			
			/* ds.on('load', function() {
				var count = ds.getCount();
				for (var i = 0; i < count; i++) {
					var record = ds.getAt(i);
					var id = record.get('id');
					if (!id) record.set('id', '' + uniqueId++);
				}
				updateAll();
				ds.commitChanges();
			} ) */
			ds.on('add', updateAll);
			ds.on('remove', updateAll);
			ds.on('datachanged', updateAll);
			ds.on('update', updateAll);
			ds.commitChanges();
			// updateAll();
			populateTree();
			// ds.load();
			if (grid != null) grid.reconfigure(ds, cm);
	};

	function formatBoolean(value) {
		return value ? 'Yes' : 'No';  
	};
    
	function formatDate(value) {
		return value ? value.dateFormat('M d, Y') : '';
	};

	function initHotspots(container) {

		var record = rsm.getSelected();
		if (!record) return;

		var ss = record.get('areas');
		// alert(ss.getCount()); // ***
		if (ss && ss.getCount() > 0) {
			// Test first record and see if 'left' is defined.
			var record = ss.getAt(0);
			var left = record.get('left');
			if (!left) {
				// Initialize the hotspot region values
				for (var i = 0; i < ss.getCount(); i++)
				{
					var record = ss.getAt(i);
					var x1 = parseFloat(record.get('x1'));
					var y1 = parseFloat(record.get('y1'));
					var x2 = parseFloat(record.get('x2'));
					var y2 = parseFloat(record.get('y2'));
					record.set('left', Math.floor(x1 * pageImageWidth));
					record.set('top', Math.floor(y1 * pageImageHeight));
					record.set('width', Math.floor((x2 - x1) * pageImageWidth));
					record.set('height', Math.floor((y2 - y1) * pageImageHeight));
				}
			}
		}
		else {
			// Clear all the hotspots
			var dh = Ext.DomHelper;
			var t = '';
			dh.overwrite(container, {tag: "div", html: t});
		}
// *****
		var tpl = new Ext.Template( '<div class="hotspotContainer" id="hc{id}" style="position:absolute; left:{left}px; top:{top}px; width:{width}px; height:{height}px; margin:0px"><div class="thumb-wrap" style="position:relative; overflow:hidden; width:{width}px; height:{height}px; margin:0px">' +
			'<div id="{id}" class="ttthumb"><img class="ttthumb-img" src="images/{tag}.gif"><font color="blue">{id}</font></div>' +
			'</div></div>' );
		// tpl.compile();

		// Initialize the view which will create and manage the hotspots
		view = new Ext.View(container, tpl, {selectedClass: 'x-view-selected', multiSelect: true, store: ss} );
		var dragZone = new ImageDragZone(view, {containerScroll: true, ddGroup: 'organizerDD'});
	}

	function initLayout(div) {
		layout = new Ext.BorderLayout(div, {
			north: {
				initialSize:30, split:false, titlebar: false, animate: true
			},
			west: {
				initialSize:120, maxSize: 200, split:true, titlebar: false, animate: true
			},
			center: {
				initialSize: 600, minWidth: 600, titlebar: false, split:true
			},
			east: {
				initialSize:0, maxSize: 200, split:true, titlebar: false, animate: true
			},
			south: {
				initialSize:200, maxSize: 200, split:true, titlebar: false, animate: true
			}
		});
	};
	function updateImage() {
		var img = document.getElementById("PAGE_IMAGE");
		img.src = SERVER + 'docs/' + DOCUMENT + '/thumbnail?page=' + pageIndex + '&wid=800&qlt=80';
	}
	

	function onBeforeNodeDrop(e) {
		// var t = '';
		// for (i in e.target) t += i + "\n";
		if (e.source.dragData.multi) {
			// var t = e.source.dragData.nodes[0].firstChild.firstChild.id;
			var t = '';
			if ( e.source.dragData.nodes[0] && e.source.dragData.nodes[0].firstChild && e.source.dragData.nodes[0].firstChild.className == "ttthumb" )
				t += e.source.dragData.nodes[0].firstChild.id;
			for (var i = 1; i < e.source.dragData.nodes.length; i++) {
				if ( e.source.dragData.nodes[i] && e.source.dragData.nodes[i].firstChild && e.source.dragData.nodes[i].firstChild.className == "ttthumb" ) {
					t += ',' + e.source.dragData.nodes[i].firstChild.id;
				}
			}
			window.status = 'multi: ' + e.source.dragData.multi + ' ids: ' + t;
			// return;
			var hotspots = t.split(',');
			var treeNodeId = e.target.id.substr(1);
			for (var i = 0; i < hotspots.length; i++) {
				var targetRecord = ds.getAt(parseInt(treeNodeId));
				var targetAreasDS = targetRecord.get('areas');
			
				var sourceRecord = rsm.getSelected();
				var id = sourceRecord.get('id');
				if (id == e.target.id) return;
				var sourceAreasDS = sourceRecord.get('areas');
				var hotspotRecord = sourceAreasDS.getAt(parseInt(hotspots[i]));
			
				if (hotspotRecord) targetAreasDS.add(hotspotRecord);
			}
			view.clearSelections(true);
		}
		/* else {
			var hotspotId = e.source.dragData.ddel.id;
			var treeNodeId = e.target.id.substr(1);
			window.status = 'Hotspot: ' + hotspotId + " Article/Ad: " + treeNodeId;
			
			var targetRecord = ds.getAt(parseInt(treeNodeId));
			var targetAreasDS = targetRecord.get('areas');
			
			var sourceRecord = rsm.getSelected();
			var id = sourceRecord.get('id');
			if (id == e.target.id) return;
			var sourceAreasDS = sourceRecord.get('areas');
			var hotspotRecord = sourceAreasDS.getAt(parseInt(hotspotId));
			
			if (hotspotRecord) targetAreasDS.add(hotspotRecord);
		} */
		return true;
	}

	function onNodeDragOver(e) {
       	// return true/false based on whatever rules you want to establish. 
       	// (false disallows the drop)
		return true;
	}

// This function copies the node & all its children (recursively)
function copyDropNode(node){
	//forces creation of a new id by removing the original.
	var atts = node.attributes;
	atts.id = undefined;

	var newNode = new Ext.tree.TreeNode(Ext.apply({}, atts));
	newNode.text = node.text;
	for(var i=0; i < node.childNodes.length; i++) {
		n = node.childNodes[i];
		newNode.appendChild(copyDropNode(n));
	}
	return newNode;
}

	return {
		// Public methods
		// Remove some in final release
		groupHotspots : function() {
			var hotspots = view.getSelectedNodes();
			if (hotspots.length > 0)
			{
				var hotspotsString = '';
				var recordId = iseepublish.addRecord();
				var targetRecord = null; // ds.getAt(recordId);
				for (var i = 0; i < ds.getCount(); i++) {
					var record = ds.getAt(i);
					var gid = record.get('id');
					if (recordId == gid) {
						targetRecord = ds.getAt(i);
					}
				}
				var targetAreasDS = targetRecord.get('areas');

				var sourceRecord = rsm.getSelected();
				var sourceAreasDS = sourceRecord.get('areas');
				var id = sourceRecord.get('id');
				for (var i = 0; i < hotspots.length; i++) {
					// nodes += '' + hotspots[i] + '\n';
					// var hotspot = null;
					// if ( hotspots[i].firstChild )
					// 	hotspot = hotspots[i].firstChild.firstChild.id;
					if (hotspots[i].id)
					{
						var hotspot = hotspots[i].id.substr(2);
						// var hotspot = hotspots[i].firstChild.firstChild.id;
						hotspotsString += hotspot + ' ';
						var hotspotRecord = sourceAreasDS.getAt(parseInt(hotspot));
						// TODO Create record
						if (hotspotRecord) {
							targetAreasDS.add(hotspotRecord);
							// **** TODO Select "content" and put into title ****
							if ( i==0 && hotspotRecord.get('content') ) {
								var temp = hotspotRecord.get('content');

								temp = temp.substr(0, 50);
								temp = xmlDecode(temp);
								targetRecord.set( 'titleString', temp );

								// alert( hotspotRecord.get('content') );
							}
							targetAreasDS.commitChanges();
							// sourceAreasDS.remove(hotspotRecord);
							// sourceAreasDS.commitChanges();
							// *****
						}
					}
				}
			}
			window.status = "Hotspots added: " + hotspotsString;
			// *** TODO Put text in the preview window.

			return true;
		},
		getSS : function() {
			return ss;
		},
		update : function() {
			updateAll();
		},
		submit : function() {
			var section = comboSection.getRawValue();
			var xmlDoc = '<?xml version="1.0" encoding="UTF-8"?>\r\n<iseePageLayout>\r\n<page';
			// var xmlDoc = '<iseePageLayout>\r\n<page'
			if (section && section != "")xmlDoc += ' section="' + section + '"';
			xmlDoc += '>\r\n';
			
			var articles = '<articles>\r\n';
			var ads = '<ads>\r\n';
			var unassignedContent = '<unassignedContent>\r\n';

			for(var i = 0; i < ds.getCount(); i++) {
				var t = '';	
				var record = ds.getAt(i);

				var type = record.get('type');
				var adRef = record.get('adRef');
				if (!adRef) adRef = '';
				if (type == 'article') t += '<contentGroup id="' + record.get('id') + '" type="' + record.get('type') + '" titleString="' + xmlEncode(record.get('titleString')) + '" adRef="' + adRef + '">\r\n';
				else if (type == 'ad') t += '<contentGroup id="' + record.get('id') + '" type="' + record.get('type') + '" titleString="' + xmlEncode(record.get('titleString')) + '">\r\n';



				var areas = record.get('areas');
				for(var ii = 0; ii < areas.getCount(); ii++) {
					var areaRecord = areas.getAt(ii);
					var tag = areaRecord.get('tag');
					if ( 'text' == tag ) {
						// **** DOES NOT SAVE <CONTENT> **** UNCOMMENT TO SAVE <CONTENT>
						if ( areaRecord.get('content') ) t += '<text area="'+ areaRecord.get('x1') + ',' + areaRecord.get('y1') + ',' + areaRecord.get('x2') + ',' + areaRecord.get('y2') + '" newParagraph="true"><content>' + areaRecord.get('content') + '</content></text>\r\n';
						else t += '<text area="'+ areaRecord.get('x1') + ',' + areaRecord.get('y1') + ',' + areaRecord.get('x2') + ',' + areaRecord.get('y2') + '" newParagraph="true"></text>\r\n';
					}
					if ( 'figure' == tag ) t += '<figure area="'+ areaRecord.get('x1') + ',' + areaRecord.get('y1') + ',' + areaRecord.get('x2') + ',' + areaRecord.get('y2') + '" captionText="' + xmlEncode(record.get('titleString')) + '"></figure>\r\n';
				}
				if (type == 'article' || type == 'ad') {
					t += '<url>' + xmlEncode(record.get('url')) + '</url>'
					+ '<email>' + xmlEncode(record.get('email')) + '</email>'
					+ '<phone>' + xmlEncode(record.get('phone')) + '</phone>'
					+ '<sms>' + xmlEncode(record.get('sms')) + '</sms>\r\n';
				}

				if (type == 'article') articles += t + '</contentGroup>\r\n';
				else if (type == 'ad') ads += t + '</contentGroup>\r\n';
				else if (type == 'misc') unassignedContent += t + '</unassignedContent>\r\n';
			}
			articles += '</articles>\r\n'
			ads += '</ads>\r\n'
			xmlDoc += articles + ads + unassignedContent + '</page>\r\n</iseePageLayout>';

			ds.commitChanges();

			// var output = document.getElementById('output');
			// output.innerText = xmlDoc;

			var request = HTTP.newRequest();
			request.open("POST", SERVER + "authoring/update.do?docID=" + DOCUMENT + "&pageIndex=" + pageIndex, false);

			// request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			request.setRequestHeader("Content-Type", "text/xml; charset=utf-8");

			// &&&& DEBUGGING &&&&
			// var ph_xml = document.getElementById("placeholder_xml")
			// ph_xml.innerHTML = xmlDoc;
			// alert( xmlDoc );



			request.send(xmlDoc);
		},
		addRecord : function() {
			var temp = 0; // uniqueId++;
			for (var i = 0; i < ds.getCount(); i++) {
				var iRecord = ds.getAt(i);
				if (iRecord) {
					var gid = parseInt(iRecord.get('id'));
					if (gid && gid > temp) temp = gid;
				}
			}
			temp++; 
			temp = '' + temp;

			var areas = [];
			var areasRecordDef = Ext.data.Record.create([
				{name: 'id', mapping: 1},
				{name: 'tag', mapping: 2},
				{name: 'x1', mapping: 3},
				{name: 'y1', mapping: 4},
				{name: 'x2', mapping: 5},
				{name: 'y2', mapping: 6}
				// {name: 'left', mapping: 7},
				// {name: 'top', mapping: 8},
				// {name: 'width', mapping: 9},
				// {name: 'height', mapping: 10}
			]);
			var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
			var areasStore = new Ext.data.Store( {
					proxy: new Ext.data.MemoryProxy(areas),
					reader: areasReader
			} );

			var p = new record( {
				// "titleString": 'New Article',
				"titleString": '-',
				"type": 'article',
				"url": '',
				"email": '',
				"phone": '',
				"sms": '',
				"adRef": '',
				"areas": areasStore,
				"id": temp
			} );
			ds.add(p);
			return temp;
			// addTreeNode(temp, 'New Article');
		},
		removeRecord : function() {
			var record = rsm.getSelected();
			if (record != null)
			{
				var temp = record.get('id');
				if (temp != 0) ds.remove(record);
			}
		},
		getDataSource : function() {
			return ds;
		},
		previousPage : function() {
			if ( pageIndex != 0 )
			{
				var dh = Ext.DomHelper;
				dh.overwrite('imgBody', {tag: "div", html: ''});

				pageIndex--;
				pageCombo.setValue('' + (1+pageIndex) + ' of ' + pageTotal);
				uniqueId = 0;
				updateDataStore();
				updateImage();
			}
		},
		nextPage : function() {
			if ( pageIndex+1 < pageTotal )
			{
				var dh = Ext.DomHelper;
				dh.overwrite('imgBody', {tag: "div", html: ''});
				
				pageIndex++;
				pageCombo.setValue('' + (1+pageIndex) + ' of ' + pageTotal);
				uniqueId = 0;
				updateDataStore();
				updateImage();
			}
		},
		getRowSelectionModel : function() {
			return rsm;
		},
		getUniqueId : function() {
			return uniqueId++;
		},
		getDocument : function() {
		},
		setDocument : function(documentString) {
			DOCUMENT = documentString;
		},
		getPage : function() {
		},
		setPage : function(pageNumber) {
		},
		getPageCount : function() {
		},
		init : function() {
			Ext.QuickTips.init();
			// var params = window.location.href;
			var params = Ext.urlDecode(window.location.href, true);
			if (params.doc != null) DOCUMENT = params.doc;
			if (params.page != null) pageIndex = parseInt(params.page);

			initLayout("iseepublish_container");
            var documentKeyMap = new Ext.KeyMap(document /* layout.getEl() */, [
				{ key: 's', ctrl:true, scope: this, stopEvent: true, fn : function() { iseepublish.submit(); } },

				{ key: 'g', ctrl:true, scope: this, stopEvent: true, fn : function() { iseepublish.groupHotspots(); } },
				{ key: 113, scope: this, stopEvent: true, fn : function() { iseepublish.groupHotspots(); } },

				{ key: Ext.EventObject.PAGEUP, ctrl: true, scope: iseepublish, stopEvent: true, fn : function() {iseepublish.previousPage();} },

				{ key: Ext.EventObject.PAGEDOWN, ctrl: true, scope: iseepublish, stopEvent: true, fn : function() {iseepublish.nextPage();} }
			] );
			documentKeyMap.stopEvent = true;

            /* var documentKeyMap2 = new Ext.KeyMap(layout.getEl(), [
				{ key: Ext.EventObject.ENTER, shift:true, fn : function() {iseepublish.submit();} },
				{ key: Ext.EventObject.PAGEUP, shift: true, fn : function() {iseepublish.previousPage();} },
				{ key: Ext.EventObject.PAGEDOWN, shift: true, fn : function() {iseepublish.nextPage();} }
			] ); */
			// *** TODO Group elements ***
            /* var documentKeyMap3 = new Ext.KeyMap('imgBody', [
				{ key: 'g', ctrl:true, fn : function() {alert('g');} },
			] ); */

			// Start of articles pane
			var articles = layout.getEl().createChild( {tag:'div', id:'articles'} );

			tree = new Ext.tree.TreePanel(articles, {
				animate:true, 
				enableDD:true,
				containerScroll: true,
				ddGroup: 'organizerDD',
				rootVisible:false
			} );

			var droptarget = new Ext.dd.DropTarget(tree.getEl());
			droptarget.notifyDrop = function(dd, e, data) {
				// console.log("dropped " + dd.id + " on dragtarget " + data);
	        		return true;
   			}

			treeRoot = new Ext.tree.TreeNode( {
				text: 'Articles', 
				allowDrag:false,
				allowDrop:false
			});
			tree.setRootNode(treeRoot);

			tree.on('beforenodedrop', onBeforeNodeDrop );
			tree.on('nodedragover', onNodeDragOver);

			tree.render();
			treeRoot.expand();

			var ge = new Ext.tree.TreeEditor(tree, {
				allowBlank:false,
				blankText:'A name is required',
				selectOnFocus:true
			});
			// End of articles pane
			
			
			layout.beginUpdate();
			var west = layout.add("west", new Ext.ContentPanel(articles, { fitToFrame:true, autoScroll:true, autoCreate:true } ));

			var east = layout.add('east', new Ext.ContentPanel('east', {
				title:'Page Info', 
				fitToFrame:true,
				autoScroll:true,
				autoCreate:true
			}));

			var north = layout.add('north', new Ext.ContentPanel('north', {
				title:'toolbar', 
				fitToFrame:false,
				autoScroll:false,
				autoCreate:true
			}));

			var south = layout.add('south', new Ext.ContentPanel('south', {
				title:'Data', 
				fitToFrame:true,
				autoScroll:true,
				autoCreate:true
			} ));
		
			// Start of images pane
			var images = layout.add('center', new Ext.ContentPanel('images', {
				title:'Page', 
				fitToFrame:true,
				autoScroll:true,
				autoCreate:true
			}));
			layout.endUpdate();
			layout.layout();
			// layout.on('regionresized', function() { alert("blah"); layout.layout(); } )
			
			var imgBodyContainer = images.getEl();
			imgBody = imgBodyContainer.createChild( {tag:'div', id:'imgBody'} );

			// Set up datastore
			record = Ext.data.Record.create([
				 {name: 'titleString', mapping: '@titleString', type: 'string', defaultValue: 'Misc'},
				 {name: 'type', mapping: '@type', type: 'string'},
				 {name: 'url', type: 'string'},
				 {name: 'email', type: 'string'},
				 {name: 'phone', type: 'string'},
				 {name: 'sms', type: 'string'},
				 {name: 'adRef', mapping: '@adRef', type: 'string'},
				 {name: 'id', mapping: '@id', type: 'string'},
				 {name: 'textArea', mapping: 'text@area', type: 'string'},
				 {name: 'figureArea', mapping: 'figure@area', type: 'string'},
				 {name: 'areas', type: 'auto'}
			]);
			// OLD LOCATION updateDataStore();
			
			// Add elements to the image panel
			var dh = Ext.DomHelper;
			var img = '<img border="0" id="PAGE_IMAGE" src="' + SERVER + 'docs/' + DOCUMENT + '/thumbnail?page=' + pageIndex + '&wid=800&qlt=80' + '">';
			dh.append(imgBodyContainer, {tag: "div", html: img});
			pageImage = document.getElementById('PAGE_IMAGE');
			pageImage.onload = function()
			{
				pageImageWidth = pageImage.width;
				pageImageHeight = pageImage.height;
				// initHotspots(imgBody);
			};

			// Get the number of pages
			
			var pageXML = '';
			var pageString = '';
			try {
				pageXML = XML.load(SERVER + "authoring/layout.do?docID=" + DOCUMENT + "&action=getPageCount");
				pageString = XML.serialize(pageXML);
			}
			catch(ex) {
        		alert('Login:  Session timed-out. Please log back in.');				
				location = '../';
				return;
			}
			var pageCountObject = xml2json.parser(pageString);
			pageTotal = pageCountObject.pages.count;
			// pageTotal = 1;
			// comboSection = createToolbar(north);
			
			// Start of Menu
			var menu = new Ext.menu.Menu( {id: 'menu'} );
			var tb = new Ext.Toolbar(north.getEl());

			tb.add( { handler: function() { iseepublish.submit(); }, icon: 'images/submit.gif', cls: 'x-btn-icon', tooltip: "Save (Ctrl+S)" } );
			tb.addSeparator();
			pageCombo = new Ext.form.Field({
				value: '' + (1+pageIndex) + ' of ' + pageTotal,
				width: 50
			});

			function gotoPage(page) {
				page = parseInt(page.getValue());
				if ( page <= pageTotal && page > 0)
				{
					// view.clearSelections(true);
					var dh = Ext.DomHelper;
					dh.overwrite('imgBody', {tag: "div", html: ''});

					pageIndex = page - 1;
					pageCombo.setValue('' + (1+pageIndex) + ' of ' + pageTotal);
					uniqueId = 0;
					updateDataStore();
					updateImage();
				}
				else pageCombo.setValue('' + (1+pageIndex) + ' of ' + pageTotal);
			};
			pageCombo.on('change', gotoPage);
			pageCombo.on('specialkey', function(passedObject, e) { if (e.getKey() == 13) gotoPage(this); } );

			tb.add( { handler: iseepublish.previousPage, icon: 'images/page-prev-disabled.gif', cls: 'x-btn-icon', tooltip: "Previous Page (Ctrl+Page Up)" } );
			tb.addField(pageCombo);
			tb.add( { handler: iseepublish.nextPage, icon: 'images/page-next-disabled.gif', cls: 'x-btn-icon', tooltip: "Next Page (Ctrl+Page Down)" } );
			tb.addSeparator();
			tb.add(
				{ handler: iseepublish.addRecord, icon: 'images/article-add.gif', cls: 'x-btn-icon', tooltip: "Add Article (Ctrl+G or F2)" },
				{ handler: iseepublish.removeRecord, icon: 'images/article-remove.gif', cls: 'x-btn-icon', tooltip: "Remove Article" }
			);
			tb.add( {
				enableToggle: true,
				// toggleHandler: onItemToggle,
				icon: "images/draw.gif",
				cls: 'x-btn-icon',
				tooltip: "Draw Regions",
				pressed: false
			} );
			tb.addSeparator();
			section = [
				["Main"],
				["Business"],
				["Entertainment"],
				["Life"],
				["Sports"],
				["World"]
			];
			var store = new Ext.data.SimpleStore({
				fields: ['section'],
				data : section
			});
			comboSection = new Ext.form.ComboBox( {
				transform:'contentSection',
				// store: store,
				// scope: iseepublish,
				displayField:'section',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				emptyText:'Section...',
				selectOnFocus:true,
				width:150,
				lazyRender:true
			} );
			tb.addField(comboSection);
			// tb.add(comboSection);
    		// function onItemToggle(item, pressed) {
				// placeholder
	    	// }
			tb.add('&nbsp;&nbsp;&nbsp;');
			tb.add('&nbsp;<a href="../Logout.do">Log off</a>&nbsp;');
			tb.add('&nbsp;<a href="../ListDocuments.do">List all documents</a>&nbsp;');
			tb.add('&nbsp;<a href="../Welcome.do">Main page</a>&nbsp;');
		
			tb.addFill();
			tb.add('<a href="http://www.iseemedia.com/"><img border="0" src="images/logo.gif" /></a>&nbsp;');

			// Start of grid
			cm = new Ext.grid.ColumnModel( [
				{
					header: "Title",
					dataIndex: 'titleString',
					width: 250,
					editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: false
					}))
				},
				{
					header: "Type",
					dataIndex: 'type',
					width: 75,
					// editor: new Ext.grid.GridEditor(new Ext.form.TextField({
					//	allowBlank: false
					editor: new Ext.grid.GridEditor(new Ext.form.ComboBox( {
						typeAhead: false,
						triggerAction: 'all',
						transform:'contentType',
						lazyRender:true
					}))
				},
				{
					 header: "adRef",
					 dataIndex: 'adRef',
					 width: 50,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				},
				{
					 header: "id",
					 dataIndex: 'id',
					 width: 50,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				},
				{
					 header: "Web",
					 dataIndex: 'url',
					 width: 200,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				},
				{
					 header: "Email",
					 dataIndex: 'email',
					 width: 150,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				},
				{
					 header: "Phone",
					 dataIndex: 'phone',
					 width: 100,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				},
				{
					 header: "SMS",
					 dataIndex: 'sms',
					 width: 100,
					 editor: new Ext.grid.GridEditor(new Ext.form.TextField({
							 allowBlank: true
					 }))
				}
			] );			
			cm.defaultSortable = false;
			
			rsm = new Ext.grid.RowSelectionModel( {singleSelect: true} );
			var selected = 0;
			rsm.on('rowselect', changeSelection, rsm, selected);
			updateDataStore();

			grid = new Ext.grid.EditorGrid("grid", {
				ds: ds,
				cm: cm,
				selModel: rsm,
				enableColLock:false
			});
			Ext.BorderLayout.create( { center: { panels: [new Ext.GridPanel(grid)] } }, south.getEl() );
			grid.render();
			// ds.load();

			// var tpl = new Ext.Template('<div class="drsElementAlt drsMoveHandleAlt" id="hotspot{uniqueId}" style="left:{left}px; top:{top}px; width:{width}px; height:{height}px"></div>');
			// var view = new Ext.View(container, tpl, {store: ds} );
	
		}
	};
}();

// Ext.EventManager.onDocumentReady(iseepublish.init, iseepublish, true);

ImageDragZone = function(view, config){
	this.view = view;
	ImageDragZone.superclass.constructor.call(this, view.getEl(), config);
};

Ext.extend(ImageDragZone, Ext.dd.DragZone, {
	// We don't want to register our image elements, so let's 
	// override the default registry lookup to fetch the image 
	// from the event instead
	getDragData : function(e) {
		e = Ext.EventObject.setEvent(e);
		var target = e.getTarget('.thumb-wrap');
		if(target) {
			var view = this.view;
			if(!view.isSelected(target)) {
				view.select(target, e.ctrlKey);
			}
			var selNodes = view.getSelectedNodes();
			var dragData = {
				nodes: selNodes
			};
			// alert(dragData.nodes.length);
			/* if(selNodes.length == 1) {
				dragData.ddel = target.firstChild; // the div element
				dragData.single = true;
			} else 
			{ */
				var div = document.createElement('div'); // create the multi element drag "ghost"
				div.className = 'multi-proxy';
				for(var i = 0, len = selNodes.length; i < len; i++) {
					// div.appendChild(selNodes[i].firstChild);
					if (i == 0 && selNodes[i].firstChild && selNodes[i].firstChild.className == 'ttthumb')
					{
						// ****
						div.appendChild(selNodes[i].firstChild.cloneNode(true));
						window.status = 'firstChild.className: ' + selNodes[i].firstChild.className + ' firstChild.id: ' + selNodes[i].firstChild.id;
					}
					else if ( selNodes[i].firstChild && selNodes[i].firstChild.className == 'ttthumb' ) 
					{
						div.appendChild(selNodes[i].firstChild.cloneNode(true));
					}
					// if (selNodes[i].firstChild.id != undefined)
					// 	div.appendChild(selNodes[i].firstChild);
					/* div.appendChild(selNodes[i].firstChild.firstChild.cloneNode(true));
					if((i+1) % 3 == 0) {
						div.appendChild(document.createElement('br'));
					} */
				// }
				dragData.ddel = div;
				// dragData.ddel = selNodes[0].firstChild.firstChild;
				dragData.multi = true;
			}
			return dragData;
		}
		return false;
	},
	x_getTreeNode : function(data1, targetNode) {
		window.status = 'getTreeNode';
		var treeNodes = [];
		var nodeData = this.dragData.nodes;
		for(var i = 0, len = nodeData.length; i < len; i++) {
			var data = nodeData[i];
			var ss = iseepublish.getSS();
			var record = ss.getById( data.id );
			window.status = 'data.id: ' + data.id + ' id: ' + record.get('id');
			ss.remove(record);

			treeNodes.push(new Ext.tree.TreeNode({
				text: data.id,
				icon: data.url,
				data: data,
				leaf:true,
				cls: 'article-node',
				qtip: data.qtip
			}));
		}
		return treeNodes;
	},
	x_afterRepair:function(){
		for(var i = 0, len = this.dragData.nodes.length; i < len; i++){
			Ext.fly(this.dragData.nodes[i]).frame('#8db2e3', 1);
		}
		this.dragging = false;    
	},
	x_getRepairXY : function(e){
		if(!this.dragData.multi){
			var xy = Ext.Element.fly(this.dragData.ddel).getXY();
			xy[0]+=3;xy[1]+=3;
			return xy;
		}
		return false;
	}
});

String.prototype.ellipse = function(maxLength){
	if(this.length > maxLength){
		return this.substr(0, maxLength-3) + '...';
	}
	return this;
};

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
dragresize.ondragmove = function(isResize) { };
dragresize.ondragend = function(isResize) { };
dragresize.ondragblur = function() { };

// Finally, you must apply() your DragResize object to a DOM node; all children of this
// node will then be made draggable. Here, I'm applying to the entire document.
dragresize.apply(document);


Ext.data.XmlReader2 = function(meta, recordType){
    Ext.data.XmlReader2.superclass.constructor.call(this, meta, recordType);
};
Ext.extend(Ext.data.XmlReader2, Ext.data.DataReader, {
    /**
     * This method is only used by a DataProxy which has retrieved data from a remote server.
	 * @param {Object} response The XHR object which contains the parsed XML document.  The response is expected
	 * to contain a method called 'responseXML' that returns an XML document object.
     * @return {Object} records A data block which is used by an {@link Ext.data.Store} as
     * a cache of Ext.data.Records.
     */
    read : function(response){
        var doc = response.responseXML;
        if(!doc) {
            throw {message: "XmlReader.read: XML Document not available"};
        }
        return this.readRecords(doc);
    },
    /**
     * Create a data block containing Ext.data.Records from an XML document.
	 * @param {Object} doc A parsed XML document.
     * @return {Object} records A data block which is used by an {@link Ext.data.Store} as
     * a cache of Ext.data.Records.
     */
    readRecords : function(doc){
        /**
         * After any data loads/reads, the raw XML Document is available for further custom processing.
         * @type XMLDocument
         */
        this.xmlData = doc;
        var root = doc.documentElement || doc;
    	var q = Ext.DomQuery;
    	var recordType = this.recordType, fields = recordType.prototype.fields;
    	var sid = this.meta.id;
    	var totalRecords = 0, success = true;
    	if(this.meta.totalRecords) {
    	    totalRecords = q.selectNumber(this.meta.totalRecords, root, 0);
    	}
        
        if(this.meta.success){
            var sv = q.selectValue(this.meta.success, root, true);
            success = sv !== false && sv !== 'false';
    	}
    	var records = [];

for(var x = 0; x < this.meta.record.length; x++)
{
    	var ns = q.select(this.meta.record[x], root);
        for(var i = 0, len = ns.length; i < len; i++) {
	        var n = ns[i];
	        var values = {};
	        var id = sid ? q.selectValue(sid, n) : undefined;

	
		// if (this.meta.record == "contentGroup")
			// areaAttributes = q.select('>area', n);
		var areaAttributes = q.select('>[area]', n);
		var areas = [];
		for (var ii = 0; ii < areaAttributes.length; ii++)
		{
			var id = ii;
			var tag = areaAttributes[ii].tagName;
			var str = '' + areaAttributes[ii].attributes[0].value;
			var point = str.split(',');
			var x1 = parseFloat(point[0]);
			var y1 = parseFloat(point[1]);
			var x2 = parseFloat(point[2]);
			var y2 = parseFloat(point[3]);
			areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
		}

		var areasRecordDef = Ext.data.Record.create([
			{name: 'id', mapping: 1},
			{name: 'tag', mapping: 2},
			{name: 'x1', mapping: 3},
			{name: 'y1', mapping: 4},
			{name: 'x2', mapping: 5},
			{name: 'y2', mapping: 6}
		]);
		var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
		var areasStore = new Ext.data.Store( {
				proxy: new Ext.data.MemoryProxy(areas),
				reader: areasReader
		} );
		areasStore.load();

	        for(var j = 0, jlen = fields.length; j < jlen; j++){
	            var f = fields.items[j];

                    var v = '';
		    if (f.name == 'areas')
		    {
				v = areasStore;
		    }
            else
			{
				v = q.selectValue(f.mapping || f.name, n, f.defaultValue);
				if(v==undefined && f.name=='titleString') v = 'Misc';
			} 
			v = f.convert(v);
	            values[f.name] = v;
	        }
	        var record = new recordType(values, id);
	        record.node = n;
	        records[records.length] = record;
	    }
}
	    return {
	        success : success,
	        records : records,
	        totalRecords : totalRecords || records.length
	    };
    }
});

XmlReader = function(meta, recordType){
    XmlReader.superclass.constructor.call(this, meta, recordType);
};
Ext.extend(XmlReader, Ext.data.DataReader, {
    /**
     * This method is only used by a DataProxy which has retrieved data from a remote server.
	 * @param {Object} response The XHR object which contains the parsed XML document.  The response is expected
	 * to contain a method called 'responseXML' that returns an XML document object.
     * @return {Object} records A data block which is used by an {@link Ext.data.Store} as
     * a cache of Ext.data.Records.
     */
    read : function(response) {
        var doc = response.responseXML;
        if(!doc) {
            throw {message: "XmlReader.read: XML Document not available"};
        }
        return this.readRecords(doc);
    },
    /**
     * Create a data block containing Ext.data.Records from an XML document.
	 * @param {Object} doc A parsed XML document.
     * @return {Object} records A data block which is used by an {@link Ext.data.Store} as
     * a cache of Ext.data.Records.
     */
    readRecords : function(doc) {
        /**
         * After any data loads/reads, the raw XML Document is available for further custom processing.
         * @type XMLDocument
         */
        this.xmlData = doc;
		
		// *** TODO
		var pageObject = xml2json.parser( doc.toString() );
/*
        var root = doc.documentElement || doc;
    	var q = Ext.DomQuery;
    	var recordType = this.recordType, fields = recordType.prototype.fields;
    	var sid = this.meta.id;
    	var totalRecords = 0, success = true;
    	if(this.meta.totalRecords) {
    	    totalRecords = q.selectNumber(this.meta.totalRecords, root, 0);
    	}
        
        if(this.meta.success){
            var sv = q.selectValue(this.meta.success, root, true);
            success = sv !== false && sv !== 'false';
    	}
    	var records = [];

for(var x = 0; x < this.meta.record.length; x++)
{
		var ns = q.select(this.meta.record[x], root);
        for(var i = 0, len = ns.length; i < len; i++) {
	        var n = ns[i];
	        var values = {};
	        var id = sid ? q.selectValue(sid, n) : undefined;

		var areaAttributes = q.select('>[area]', n);
		var areas = [];
		for (var ii = 0; ii < areaAttributes.length; ii++)
		{
			var id = ii;
			var tag = areaAttributes[ii].tagName;
			var str = '' + areaAttributes[ii].attributes[0].value;
			var point = str.split(',');
			var x1 = parseFloat(point[0]);
			var y1 = parseFloat(point[1]);
			var x2 = parseFloat(point[2]);
			var y2 = parseFloat(point[3]);
			areas[areas.length] = [ id, id, tag, x1, y1, x2, y2 ];
		}

		var areasRecordDef = Ext.data.Record.create([
			{name: 'id', mapping: 1},
			{name: 'tag', mapping: 2},
			{name: 'x1', mapping: 3},
			{name: 'y1', mapping: 4},
			{name: 'x2', mapping: 5},
			{name: 'y2', mapping: 6}
		]);
		var areasReader = new Ext.data.ArrayReader( {id:0}, areasRecordDef);
		var areasStore = new Ext.data.Store( {
			proxy: new Ext.data.MemoryProxy(areas),
			reader: areasReader
		} );
		areasStore.load();

	        for(var j = 0, jlen = fields.length; j < jlen; j++){
	            var f = fields.items[j];

                    var v = '';
		    if (f.name == 'areas')
		    {
				v = areasStore;
		    }
            else
			{
				v = q.selectValue(f.mapping || f.name, n, f.defaultValue);
				if(v==undefined && f.name=='titleString') v = 'Misc';
			} 
			v = f.convert(v);
	            values[f.name] = v;
	        }
	        var record = new recordType(values, id);
	        record.node = n;
	        records[records.length] = record;
	    }
}
*/	        

	    return {
			// success: success, records: records, totalRecords: records.length
	        success : false,
	        records : null,
	        totalRecords : 0
	    };
    }
} );